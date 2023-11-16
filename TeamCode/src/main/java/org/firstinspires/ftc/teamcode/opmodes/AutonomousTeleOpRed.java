package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.TeamObjectDetector;
@TeleOp(name = "Autonomous Red", group = "Autonomous")
public class AutonomousTeleOpRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        telemetry.addData("Red >", "Press Start");
        telemetry.update();

        waitForStart();
        if(isStopRequested()) return;

        if (opModeIsActive()) {
            TeamObjectDetector teamObjectDetector = new TeamObjectDetector("", new String[]{"blue", "read", "-red-blue"},"read", telemetry, hardwareMap);
            teamObjectDetector.initModel("20231115-all-ftc-team-prop.tflite");

            int whereIsProp = teamObjectDetector.whereIsTeamObject();
            telemetry.addData(">", "PropLocation:", whereIsProp);
            telemetry.update();

        }
    }
}
