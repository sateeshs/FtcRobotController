package org.firstinspires.ftc.teamcode.opmodes;

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
@TeleOp (name = "Gamepad Control on Mechanum", group = "ftc")
public class MecanumTeleOp extends LinearOpMode {

    public DcMotor  leftArm     = null;
    //public Servo leftClaw    = null;
    //public Servo    rightClaw   = null;
    private Servo left_hand;
    public Servo droneServo;
    private Servo right_hand;
    //double clawOffset = 0;

    //public static final double MID_SERVO   =  0.35 ;
    //public static final double CLAW_SPEED  = 0.02 ;        // sets rate to move servo
    public static final double ARM_UP_POWER    =  -0.75 ;   // Run arm motor up at 100% power
    public static final double ARM_DOWN_POWER  = 0.75 ;   // Run arm motor down at -30% power


    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        left_hand = hardwareMap.get(Servo.class, "left_hand");
        right_hand = hardwareMap.get(Servo.class, "right_hand");
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        left_hand.setDirection(Servo.Direction.FORWARD);
        right_hand.setDirection(Servo.Direction.REVERSE);
        right_hand.setPosition(0.1);
        left_hand.setPosition(0.1);

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        droneServo = hardwareMap.get(Servo.class, "drone_launcher");
        droneServo.setPosition(0.4);
        leftArm    = hardwareMap.get(DcMotor.class, "left_arm");


        telemetry.addData(">", "Robot Ready.  Press Play.");

        waitForStart();
        //droneServo.setPosition(0.4);
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            telemetry.addData("Position-1", droneServo.getPosition());

            if(gamepad1.x) {
                droneServo.setPosition(0);
                sleep(1000);
                droneServo.setPosition(0.4);
                //else if(gamepad2.b){
                //droneServo.setPosition(0.75);

            }
            telemetry.addData("Position-2", droneServo.getPosition());

            telemetry.addData(">", "Done");
            telemetry.update();

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double left;
            double right;

            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;


            if (gamepad2.dpad_down) {
                right_hand.setPosition(0);
                left_hand.setPosition(0);
                sleep(1000);
            } else if (gamepad2.dpad_left) {
                right_hand.setPosition(0.25);
                left_hand.setPosition(.5);
                sleep(1000);
            }
            else if (gamepad2.dpad_right) {
                right_hand.setPosition(1);
                left_hand.setPosition(1);
                sleep(1000);
            }


            if (gamepad2.y)
                leftArm.setPower(ARM_UP_POWER);
            else if (gamepad2.a)
                leftArm.setPower(ARM_DOWN_POWER);

            else
                leftArm.setPower(-0.005);


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
