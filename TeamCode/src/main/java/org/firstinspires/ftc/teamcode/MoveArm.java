package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp (name = "Gamepad Control Arm", group = "ftcGamePad")
public class MoveArm extends LinearOpMode {


    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    Servo   servoLeft, servoRight;
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;

    @Override
    public void runOpMode() throws InterruptedException {
// Connect to servo (Assume Robot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        servoLeft = hardwareMap.get(Servo.class, "left_hand");
        servoRight = hardwareMap.get(Servo.class, "right_hand");

        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();

double tgtPower = 0;

        // Scan servo till stop pressed.
        while(opModeIsActive()){
            tgtPower =  - this.gamepad1.left_stick_y;
//            servoRight.setPosition(1);
//            servoLeft.setPosition(0);
            //check to see if we need to move the servo
//            if(gamepad1.x) {
//                // Move to0 degrees.
//                servoRight.setPosition(1);
//                servoLeft.setPosition(0);
//            } else
                if (gamepad1.x){
                servoLeft.setPosition(0);
                servoRight.setPosition(1);
//                position += INCREMENT ;
//                if (position >= MAX_POS ) {
//                    position = MAX_POS;
//                    rampUp = !rampUp;   // Switch ramp direction
//                }
                    //servoRight.setPosition(position);
//                servoRight.setPosition(position);
//                servoLeft.setPosition(0.5);
            }else if(gamepad1.b)
                {
//                    position -= INCREMENT ;
//
//                    if (position >= MAX_POS ) {
//                        position = MAX_POS;
//                        rampUp = !rampUp;   // Switch ramp direction
//                    }
//                    // working as expected
//                    servoRight.setPosition(0.6);
//                    servoLeft.setPosition(0.4);
                    //Second test
                    servoRight.setPosition(1.50);
                    servoLeft.setPosition(-.50);
                }
//            else if (gamepad1.x){
//                servoRight.setPosition(1);
//            }
            telemetry.addData("Left Servo Position", "%5.2f", servoLeft.getPosition());
            telemetry.addData("Right Servo Position", "%5.2f", servoRight.getPosition());

            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.addData("position", position );
            telemetry.update();



//            // slew the servo, according to the rampUp (direction) variable.
//            if (rampUp) {
//                // Keep stepping up until we hit the max value.
//                position += INCREMENT ;
//                if (position >= MAX_POS ) {
//                    position = MAX_POS;
//                    rampUp = !rampUp;   // Switch ramp direction
//                }
//            }
//            else {
//                // Keep stepping down until we hit the min value.
//                position -= INCREMENT ;
//                if (position <= MIN_POS ) {
//                    position = MIN_POS;
//                    rampUp = !rampUp;  // Switch ramp direction
//                }
//            }
//
//            // Display the current value
//            telemetry.addData("Servo Position", "%5.2f", position);
//            telemetry.addData(">", "Press Stop to end test." );
//            telemetry.update();
//
//            // Set the servo to the new position and pause;
//            servoLeft.setPosition(position);
//            servoRight.setPosition(position);

            sleep(CYCLE_MS);
            idle();
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
