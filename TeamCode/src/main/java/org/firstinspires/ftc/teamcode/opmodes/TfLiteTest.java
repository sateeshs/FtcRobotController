package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.TeamObjectDetector;

@TeleOp(name="Concept: TFLite test", group ="Autonomous")

public class TfLiteTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        telemetry.addData(">", "Press Start");
        telemetry.update();

        waitForStart();
    if (opModeIsActive()) {
        TeamObjectDetector teamObjectDetector = new TeamObjectDetector("", new String[]{"blue-prop", "read-prop"}, telemetry, hardwareMap);
        int whereIsProp = teamObjectDetector.whereTeamObject();
        telemetry.addData(">", "objectLocation:", whereIsProp);

    }
    }
}
