package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.*;

/**
 * Example OpMode. Demonstrates use of gyro, color sensor, encoders, and telemetry.
 *
 */
@TeleOp (name = "Game pad Control on Mechanum", group = "ftcGamePad")
public class MecanumTeleOp extends LinearOpMode {

    public DcMotor  leftArm     = null;
    public Servo leftClaw    = null;
    public Servo    rightClaw   = null;

    double clawOffset = 0;

    public static final double MID_SERVO   =  0.5 ;
    public static final double CLAW_SPEED  = 0.02 ;        // sets rate to move servo
    public static final double ARM_UP_POWER    =  -0.5 ;   // Run arm motor up at 100% power
    public static final double ARM_DOWN_POWER  = 0.5 ;   // Run arm motor down at -30% power

    //Servo variables
    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        leftArm    = hardwareMap.get(DcMotor.class, "left_arm");

        // Define and initialize ALL installed servos.
        leftClaw  = hardwareMap.get(Servo.class, "left_hand");
        rightClaw = hardwareMap.get(Servo.class, "right_hand");
        double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double left;
            double right;

            // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;

            // Use game pad left & right Bumpers to open and close the claw
            if (gamepad2.right_bumper)
                clawOffset += CLAW_SPEED;
            else if (gamepad2.left_bumper)
                clawOffset -= CLAW_SPEED;

            // Move both servos to new position.  Assume servos are mirror image of each other.
            clawOffset = Range.clip(clawOffset, -0.8, 0.8); //Initially the value set to 0.5.Change the point to 0.2 and it doesn't close.So changed to 0.8
            leftClaw.setPosition(MID_SERVO + clawOffset);
            rightClaw.setPosition(MID_SERVO - clawOffset);

            // Use game pad buttons to move the arm up (Y) and down (A)
            if (gamepad2.y)
                leftArm.setPower(ARM_UP_POWER);
            else if (gamepad2.a)
                leftArm.setPower(ARM_DOWN_POWER);
            else
                leftArm.setPower(0.05);

//
            if (gamepad2.x){
                rightClaw.setPosition(0.75);
                leftClaw.setPosition(0.75);
                //servoRight.setPosition(position);
//                servoRight.setPosition(position);
//                servoLeft.setPosition(0.5);
            }else if(gamepad2.b)
            {


                rightClaw.setPosition(0.4);
                leftClaw.setPosition(0.4);
            }
            // Send telemetry message to signify robot running;
            telemetry.addData("claw",  "Offset = %.2f", clawOffset);
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / (2*denominator);
            double backLeftPower = (y - x + rx) / (2*denominator);
            double frontRightPower = (y - x - rx) / (2*denominator);
            double backRightPower = (y + x - rx) / (2*denominator);

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);
        }
    }
}

///Servo control sample code
//This added code will check to see if any of the colored buttons on the F310 game pad are pressed.
// If the Y button is pressed, it will move the servo to the 0-degree position.
// If either the X button or B button is pressed,
// it will move the servo to the 90-degree position.
// If the A button is pressed, it will move the servo to the 180-degree position.
// The op mode will also send telemetry data on the servo position to the Driver Station.
//
//            double tgtPower = 0;
//            while (opModeIsActive()) {
//                tgtPower = -this.game pad1.left_stick_y;
//                motorTest.setPower(tgtPower);
//                // check to see if we need to move the servo.
//                if(game pad1.y) {
//                    // move to 0 degrees.
//                    servoTest.setPosition(0);
//                } else if (game pad1.x || game pad1.b) {
//                    // move to 90 degrees.
//                    servoTest.setPosition(0.5);
//                } else if (game pad1.a) {
//                    // move to 180 degrees.
//                    servoTest.setPosition(1);
//                }
//                telemetry.addData("Servo Position", servoTest.getPosition());
//                telemetry.addData("Target Power", tgtPower);
//                telemetry.addData("Motor Power", motorTest.getPower());
//                telemetry.addData("Status", "Running");
//                telemetry.update();
//
//            }