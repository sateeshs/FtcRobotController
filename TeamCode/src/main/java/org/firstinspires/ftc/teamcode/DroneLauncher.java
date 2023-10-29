package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Drone Launcher", group = "ftcGamePad")
public class DroneLauncher extends LinearOpMode {
    Servo droneServo;
    @Override
    public void runOpMode() throws InterruptedException {
        droneServo = hardwareMap.get(Servo.class, "drone_launcher");
        waitForStart();
        droneServo.setPosition(0.75);

// Scan servo till stop pressed.
        while(opModeIsActive()){
            telemetry.addData("Position-1", droneServo.getPosition());

            if(gamepad1.x) {
                droneServo.setPosition(0);
            }else if(gamepad1.b){
                droneServo.setPosition(0.75);

            }
            telemetry.addData("Position-2", droneServo.getPosition());

            telemetry.addData(">", "Done");
            telemetry.update();
        }
    }
}
