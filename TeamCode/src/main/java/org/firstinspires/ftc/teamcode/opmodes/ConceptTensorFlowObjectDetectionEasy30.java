/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection, using
 * the easiest way.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@TeleOp(name = "Concept: TensorFlow Object Detection 30", group = "Concept")
public class ConceptTensorFlowObjectDetectionEasy30 extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;
    private TFObjectDetector tfod2;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
try {


        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive())
            {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
                telemetry.update();

            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();
} catch (Exception ex){
    telemetry.addData("ex", ex.getMessage());
    telemetry.update();

}
    }   // end runOpMode()


    /**
     * Initialize the TensorFlow Object Detection engine.
     */
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfodParameters.minResultConfidence = 0.6f;
//        tfodParameters.isModelTensorFlow2 = false;
//        tfodParameters.inputSize = 300;
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        if (labels != null) {
//            tfod.loadModelFromFile(TFOD_MODEL_FILE, labels);
//        }
//    }
    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        try {

            //det.setup();
            // Create the TensorFlow processor the easy way.

            // tfod = TfodProcessor.easyCreateWithDefaults();

//FTC-KrW3
            tfod = new TfodProcessor.Builder() //20231115-all-ftc-team-prop.tflite //20231115-ftc-team-prop.tflite
                    .setModelAssetName("20231115-all-30-ftc-team-prop.tflite") //"20231110-ftc-team-prop.tflite"//20231107-best_float32.tflite//CenterStage.tflite
                    .setModelLabels (new String[]{"blue", "red","-red-blue"})
//                    .setIsModelTensorFlow2(true)
//                    .setIsModelQuantized(true)
//                    .setModelInputSize(640)
                    //.setModelAspectRatio(16.0 / 9.0)
                    .build();
            // Create the vision portal the easy way.
            VisionPortal.Builder builder = new VisionPortal.Builder();
            if (USE_WEBCAM) {
                builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
            } else {
                builder.setCamera(BuiltinCameraDirection.BACK);
            }
            builder.addProcessor(tfod);
//// Choose a camera resolution. Not all cameras support all resolutions.
//            builder.setCameraResolution(new Size(640, 360));
//
//// Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
//            builder.enableLiveView(true);
//
//// Set the stream format; MJPEG uses less bandwidth than default YUY2.
//            builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
//
//// Choose whether or not LiveView stops if no processors are enabled.
//// If set "true", monitor shows solid orange screen if no processors enabled.
//// If set "false", monitor shows camera view without annotations.
//            builder.setAutoStopLiveView(false);


            visionPortal = builder.build();
//            if (USE_WEBCAM) {
//                visionPortal = VisionPortal.easyCreateWithDefaults(
//                    hardwareMap.get(WebcamName.class, "Webcam 2"), tfod);
//            } else {
//                visionPortal = VisionPortal.easyCreateWithDefaults(
//                    BuiltinCameraDirection.BACK, tfod);
//            }

            telemetry.addData("Initialized sucessfully", "");
        }catch (Exception ex){
            telemetry.addData("exception in initTfod", ex.getMessage());
            throw  ex;
        }
    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {
    try{
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop
    }catch (Exception ex){
        telemetry.addData("exception in telemetryTfod", ex);
        throw ex;
    }
    }   // end method telemetryTfod()

}   // end class
