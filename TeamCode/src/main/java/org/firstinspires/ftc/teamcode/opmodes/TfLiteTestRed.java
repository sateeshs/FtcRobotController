package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.components.TeamObjectDetector;

@TeleOp(name="Concept: TFLite test Red", group ="Autonomous")
public class TfLiteTestRed extends LinearOpMode {

    private char path;
    private static final double DEG_THRESHOLD = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        telemetry.addData(">", "Press Start");
        telemetry.update();

        waitForStart();
        if (opModeIsActive()) {
            TeamObjectDetector teamObjectDetector = new TeamObjectDetector("", new String[]{"blue", "read", "-red-blue"}, "read", telemetry, hardwareMap);
            teamObjectDetector.initModel("20231115-all-ftc-team-prop.tflite");
            teamObjectDetector.updateTelemetry(true, true, true, true, true);
            telemetry.addLine("Current Threshold: " + teamObjectDetector.getConfidenceThreshold());
            telemetry.update();
            sleep(250);
//
//        int whereIsProp = teamObjectDetector.whereIsTeamObject();
//        telemetry.addData(">", "PropLocation:", whereIsProp);

            if (teamObjectDetector.getNumRecognitions() == 0) {
                telemetry.addData("Object Detected - ", "No object was detected with a confidence above %f", teamObjectDetector.getConfidenceThreshold());
                telemetry.addData("Path Chosen - ", "Estimated angle = NULL deg, ready to follow 'r' path");
                telemetry.update();

                path = 'r';
            } else if (teamObjectDetector.getHighestConfidenceRecognition().estimateAngleToObject(AngleUnit.DEGREES) < DEG_THRESHOLD) {
                telemetry.addData("Object Detected - ", "A(n) %s was found with %f confidence", teamObjectDetector.getHighestConfidenceRecognition().getLabel(), teamObjectDetector.getHighestConfidenceRecognition().getConfidence());
                path = 'l';
                telemetry.addData("Path Chosen - ", "Estimated angle = %f deg, ready to follow %c path", teamObjectDetector.getHighestConfidenceRecognition().estimateAngleToObject(AngleUnit.DEGREES), path);
                telemetry.update();

            } else {
                path = 'c';
                telemetry.addData("Path Chosen - ", "Estimated angle = %f deg, ready to follow %c path", teamObjectDetector.getHighestConfidenceRecognition().estimateAngleToObject(AngleUnit.DEGREES), path);
                telemetry.update();
            }
            telemetry.update();

        }
    }
}