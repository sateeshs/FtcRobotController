package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "FromLastYearSample", group= "Autonomous")
public class FromLastYearSample extends LinearOpMode {

    private DcMotor motorBackLeft;
    private DcMotor motorFrontLeft;
    private Servo right_hand;
    private DcMotor left_arm;
    private DcMotor motorBackRight;
    private DcMotor motorFrontRight;
    private Servo left_hand;

    int FrontRightPos;
    int BackLeftPos;
    int BackRightPos;
    int FrontLeftPos;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        right_hand = hardwareMap.get(Servo.class, "right_hand");
        left_arm = hardwareMap.get(DcMotor.class, "left_arm");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        left_hand = hardwareMap.get(Servo.class, "left_hand");

        // Put initialization blocks here.
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        right_hand.setPosition(-4);
        right_hand.setPosition(4);
        BackLeftPos = 0;
        BackRightPos = 0;
        FrontLeftPos = 0;
        FrontRightPos = 0;
        waitForStart();
        drive(-1200, -1200, -1200, -1200, 0.4);
        drive(-1620, 1620, -1620, 1620, 0.3);
        sleep(100);
        movethe_arm(-200, 0.4);
    }

    /**
     * Describe this function...
     */
    private void movethe_arm(int arm_target, double arm_speed) {
        left_arm.setTargetPosition(arm_target);
        left_arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_arm.setPower(arm_speed);
        while (opModeIsActive() && left_arm.isBusy()) {
            idle();
        }
    }

    /**
     * Describe this function...
     */
    private void drive(int BackLeftTarget, int BackRightTarget, int FrontRightTarget, int FrontLeftTarget, double speed) {
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightPos += FrontRightTarget;
        BackLeftPos += BackLeftTarget;
        BackRightPos += BackRightTarget;
        FrontLeftPos += FrontLeftTarget;
        motorFrontRight.setTargetPosition(FrontRightTarget);
        motorBackRight.setTargetPosition(BackRightTarget);
        motorFrontLeft.setTargetPosition(FrontLeftTarget);
        motorBackLeft.setTargetPosition(BackLeftTarget);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setPower(speed);
        motorFrontRight.setPower(speed);
        motorBackRight.setPower(speed);
        motorBackLeft.setPower(speed);
        while (opModeIsActive() && motorFrontLeft.isBusy() && motorBackLeft.isBusy() && motorFrontRight.isBusy() && motorBackRight.isBusy()) {
            idle();
        }
    }
}