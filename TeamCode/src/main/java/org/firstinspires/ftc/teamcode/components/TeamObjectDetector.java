package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class TeamObjectDetector extends TensorFlowDetector {
    private static final double BLUE_ANGLE_THRESHOLD = 0;
    private static final double RED_ANGLE_THRESHOLD = 0;
    private static final int SLEEP_TIME = 20;
    private static final int SEC_TO_FIND_OBJECT = 1;

    private String teamColor;
    public TeamObjectDetector(String modelName, String[] labels, String teamColor, Telemetry telemetry, HardwareMap hardwaremap) {
        super(telemetry, hardwaremap);
        this.teamColor = teamColor;
        this.setModelName(modelName);
        this.setLabels(labels);
    }

    public int whereIsTeamObject() {
        if (teamColor.equals("red")){
            if (getNumRecognitions() == 0){
                int timesToTry = (SEC_TO_FIND_OBJECT * 1000) / SLEEP_TIME;
                while (getNumRecognitions() == 0){
                    updateRecognitions();
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            Recognition teamObject = getHighestConfidenceRecognition("red");
            if (getNumRecognitions() == 0) {
                telemetry.addData("Position 3", "[3] No objects were found, current confidence threshold = %f", getConfidenceThreshold());
                return 3;
            }
            if (teamObject.estimateAngleToObject(AngleUnit.DEGREES) > RED_ANGLE_THRESHOLD) {
                telemetry.addData("Position 1", "[1], current angle = %f degrees", teamObject.estimateAngleToObject(AngleUnit.DEGREES));
                return 1;
            } else {
                telemetry.addData("Position 2", "[2], current angle = %f degrees", teamObject.estimateAngleToObject(AngleUnit.DEGREES));
                return 2;
            }
        } else if (teamColor.equals("blue")){
            if (getNumRecognitions() == 0){
                int timesToTry = (SEC_TO_FIND_OBJECT * 1000) / SLEEP_TIME;
                while (getNumRecognitions() == 0){
                    updateRecognitions();
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            Recognition teamObject = getHighestConfidenceRecognition("blue_prop");
            if (getNumRecognitions() == 0) {
                telemetry.addData("Position 3", "[3] No objects were found, current confidence threshold = %f", getConfidenceThreshold());
                return 3;
            }
            if (teamObject.estimateAngleToObject(AngleUnit.DEGREES) < BLUE_ANGLE_THRESHOLD) {
                telemetry.addData("Position 1", "[1], current angle = %f degrees", teamObject.estimateAngleToObject(AngleUnit.DEGREES));
                return 1;
            } else {
                telemetry.addData("Position 2", "[2], current angle = %f degrees", teamObject.estimateAngleToObject(AngleUnit.DEGREES));
                return 2;
            }
        }
        return -1;
    }
}