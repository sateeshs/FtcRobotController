package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp(name = "Utility: Vision April Tag", group = "Utility")

public class AprilTagVision extends LinearOpMode {

    final int RESOLUTION_WIDTH = 640;
    final int RESOLUTION_HEIGHT = 480;
    @Override
    public void runOpMode()  {
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                //.setLensIntrinsics( 502.186,  502.186, 286.502,  316.433) // Calibration points
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()

                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(RESOLUTION_WIDTH, RESOLUTION_HEIGHT))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(tagProcessor)
                //.enableCamaraMonitoring(true)
                .build();

        waitForStart();
        while (!isStopRequested() && opModeIsActive())
        {

            //visionPortal.resumeLiveView();`
            tagProcessor.getFreshDetections();
            if(tagProcessor.getDetections().size() >0)
            {
                AprilTagDetection tag = tagProcessor.getDetections().get(0);

                telemetry.addData("April x:", tag.ftcPose.x);
                telemetry.addData("April y:", tag.ftcPose.y);
                telemetry.addData("April z:", tag.ftcPose.z);
                telemetry.addData("April roll:", tag.ftcPose.roll);
                telemetry.addData("April pitch:", tag.ftcPose.pitch);
                telemetry.addData("April yaw:", tag.ftcPose.yaw);
            }else{
                telemetry.addLine("April tag not detected");
            }
            telemetry.update();
        }
    }
}
