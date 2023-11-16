package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.TeamObjectDetector;
//https://github.com/GaugeCaywood/PowerPlay-2022-2023/blob/main/SampleMecannumDrive.java
@TeleOp(name="Concept: TFLite test", group ="Autonomous")

public class TfLiteTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        telemetry.addData(">", "Press Start");
        telemetry.update();

        waitForStart();
    if (opModeIsActive()) {
        TeamObjectDetector teamObjectDetector = new TeamObjectDetector("", new String[]{"blue", "read", "-red-blue"},"blue", telemetry, hardwareMap);
        teamObjectDetector.initModel("20231115-all-ftc-team-prop.tflite");

        int whereIsProp = teamObjectDetector.whereIsTeamObject();
        telemetry.addData(">", "PropLocation:", whereIsProp);
        telemetry.update();

    }
    }
}
