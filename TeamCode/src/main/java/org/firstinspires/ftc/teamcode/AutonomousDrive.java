package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TeleOp(name = "Autonomous white pixel", group = "autonomous")

public class AutonomousDrive extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private  static  final int Total_Time = 30; // total autonomous time is 30 seconds

    // Hand constants
    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftFrontDrive   = null;  //  Used to control the left front drive wheel
    private DcMotor rightFrontDrive  = null;  //  Used to control the right front drive wheel
    private DcMotor leftBackDrive    = null;  //  Used to control the left back drive wheel
    private DcMotor rightBackDrive   = null;  //  Used to control the right back drive wheel

    // For holding pixels
    Servo servoLeft, servoRight;
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;


    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        leftFrontDrive = hardwareMap.dcMotor.get("motorFrontLeft");
        leftBackDrive = hardwareMap.dcMotor.get("motorBackLeft");
        rightFrontDrive = hardwareMap.dcMotor.get("motorFrontRight");
        rightBackDrive = hardwareMap.dcMotor.get("motorBackRight");

        servoLeft = hardwareMap.get(Servo.class, "left_hand");
        servoRight = hardwareMap.get(Servo.class, "right_hand");

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        runtime.reset();
        if (opModeIsActive()) {
            while (runtime.seconds() < Total_Time) {

                Map<String, Double> objectPosition =  telemetryTfod();

                // Stop streaming
                visionPortal.stopStreaming();
                // Push telemetry to the Driver Station.
                telemetry.update();
                // TODO: derive L,R,C
                Position position = derivePixelPositionOnSpike(objectPosition);
                if(position == Position.C) {
                    // Move forward
                    moveRobot(0,12,0);
                    // Place pixel at center
                    moveRobot(0,1,0);

                }else if(position == Position.L) {
                    // Move forward
                    moveRobot(0,12,0);
                    // Place pixel at left
                    moveRobot(0,1,0);

                }else if(position == Position.R) {
                    // Move forward
                    moveRobot(0,12,0);
                    // Place pixel at right
                    moveRobot(0,1,0);

                }
                // Goto board
                moveRobot(0,15,0);

                //Drop Pixel
                releasePixelFromArm();
                //Com back and park
                moveRobot(0,15,0);
break;
                //pauseOpMode(runtime, Total_Time);

//                // Save CPU resources; can resume streaming when needed.
//                if (gamepad1.dpad_down) {
//                    visionPortal.stopStreaming();
//                } else if (gamepad1.dpad_up) {
//                    visionPortal.resumeStreaming();
//                }
//
//                // Share the CPU.
//                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();
    }

    private void initTfod() {

        // Create the TensorFlow processor the easy way.
        tfod = TfodProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, tfod);
        }

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private Map<String, Double> telemetryTfod() {
        Map<String, Double> dictionary = new HashMap<String, Double>();
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
            dictionary.put("x", x);
            dictionary.put("y", x);
            return  dictionary;
        }   // end for() loop
        return  dictionary;
    }
    /**
     * Move robot according to desired axes motions
     * <p>
     * Positive X is forward
     * <p>
     * Positive Y is strafe left
     * <p>
     * Positive Yaw is counter-clockwise
     */
    private void moveRobot(double x, double y, double yaw) {
        // Calculate wheel powers.
        double leftFrontPower    =  x -y -yaw;
        double rightFrontPower   =  x +y +yaw;
        double leftBackPower     =  x +y -yaw;
        double rightBackPower    =  x -y +yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send powers to the wheels.
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }

    /**
     *
     * */
    private Position derivePixelPositionOnSpike(Map<String, Double> objectPosition ) {
        // TODO: derive L,R,C
        return Position.L;
    }

    private void releasePixelFromArm() {
        //check to see if we need to move the servo
//        if(gamepad1.x) {
//            // Move to0 degrees.
//            servoRight.setPosition(1);
//            servoLeft.setPosition(0);
//        } else if (gamepad1.x || gamepad1.b){
            position += INCREMENT ;
            if (position >= MAX_POS ) {
                position = MAX_POS;
                //rampUp = !rampUp;   // Switch ramp direction
            }
            servoLeft.setPosition(0.5);
            servoRight.setPosition(position);
    }

    public void pauseOpMode(ElapsedTime et, double waitTime){
        double startTime = et.milliseconds();
        while (opModeIsActive() && et.milliseconds() < startTime + waitTime){}
    }
}


enum Position {
    L,R,C
}