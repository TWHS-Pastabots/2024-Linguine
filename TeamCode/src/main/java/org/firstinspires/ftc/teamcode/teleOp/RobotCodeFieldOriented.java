package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

@TeleOp (name = "FieldOriented")
public class RobotCodeFieldOriented extends OpMode {


    RobotHardware hardware;
    //MAKE SURE TO CHANGE THESE. THESE ARE YOUR DRIVE MODES THAT YOU NEED FOR THE CHECKPOINT
    public static final double FAST_MODE = .9;

    public static final double MEDIUM_MODE = 0.7;
    public static final double SLOW_MODE = .45;
    double currentMode;
    ElapsedTime buttonTime = null;

    public boolean fieldOriented;
    Orientation angles = new Orientation();
    double initYaw;
    double adjustedYaw;

    public void init() {
        hardware = new RobotHardware();
        hardware.init(hardwareMap);
        currentMode = MEDIUM_MODE;
        buttonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    public void start() {
        telemetry.addData("Status: ", "Started");
        telemetry.update();
    }

    public void loop() {
        drive();
        intake();
        launch();
        lift();
        telemetry();
    }

    public void telemetry() {
        //this is the class you should put stuff in if you want to print to the phone.

    }

    public void drive() {
        double y = -gamepad1.left_stick_y; // This is reversed
        double x = gamepad1.left_stick_x; // Counteract imperfect strafing
        double z = gamepad1.right_stick_x;

        double leftFrontPower;
        double leftRearPower;
        double rightFrontPower;
        double rightRearPower;

        if (gamepad1.share) {
            fieldOriented = true;
        } else if (gamepad1.options) {
            fieldOriented = false;
        }
        if(fieldOriented){
            angles = hardware.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            adjustedYaw = angles.firstAngle-initYaw;
            double zeroedYaw = -initYaw+angles.firstAngle;
            double theta = Math.atan2(y, x) * 180/Math.PI; // aka angle
            double realTheta;
            realTheta = (360 - zeroedYaw) + theta;
            double power = Math.hypot(x, y);
            double sin = Math.sin((realTheta * (Math.PI / 180)) - (Math.PI / 4));
            double cos = Math.cos((realTheta * (Math.PI / 180)) - (Math.PI / 4));
            double maxSinCos = Math.max(Math.abs(sin), Math.abs(cos));
            leftFrontPower = (power * cos / maxSinCos + z);
            rightFrontPower = (power * sin / maxSinCos - z);
            leftRearPower = (power * sin / maxSinCos + z);
            rightRearPower = (power * cos / maxSinCos - z);
        }
        else
        {
            leftFrontPower = y + x + z;
            leftRearPower = y - x + z;
            rightFrontPower = y - x - z;
            rightRearPower = y + x - z;
        }
        if (Math.abs(leftFrontPower) > 1 || Math.abs(leftRearPower) > 1 ||
                Math.abs(rightFrontPower) > 1 || Math.abs(rightRearPower) > 1) {
            // Find the largest power
            double max;
            max = Math.max(Math.abs(leftFrontPower), Math.abs(leftRearPower));
            max = Math.max(Math.abs(rightFrontPower), max);
            max = Math.max(Math.abs(rightRearPower), max);

            // Everything is Positive, do not worry about signs
            leftFrontPower /= max;
            leftRearPower /= max;
            rightFrontPower /= max;
            rightRearPower /= max;
        }

        if (gamepad1.dpad_left) {
            leftFrontPower = -1;
            rightRearPower = -1;
            leftRearPower = 1;
            rightFrontPower = 1;
        } else if (gamepad1.dpad_right) {
            leftFrontPower = 1;
            rightRearPower = 1;
            leftRearPower = -1;
            rightFrontPower = -1;
        } else if (gamepad1.dpad_up) {
            leftFrontPower = 1;
            rightRearPower = 1;
            leftRearPower = 1;
            rightFrontPower = 1;
        } else if (gamepad1.dpad_down) {
            leftFrontPower = -1;
            leftRearPower = -1;
            rightRearPower = -1;
            rightFrontPower = -1;
        }

        if (gamepad1.left_bumper) {
            currentMode = SLOW_MODE;

        } else if (gamepad1.right_bumper) {
            currentMode = FAST_MODE;

        } else {
            currentMode = MEDIUM_MODE;
        }

        hardware.frontLeft.setPower(leftFrontPower * currentMode);
        hardware.rearLeft.setPower(leftRearPower * currentMode);
        hardware.frontRight.setPower(rightFrontPower * currentMode);
        hardware.rearRight.setPower(rightRearPower * currentMode);
    }

    public void intake() {
        if (gamepad2.cross) {
            hardware.intakeMotor.setPower(0.8);
            telemetry.addData("the", "on");

        } else if (gamepad2.circle) {
            hardware.intakeMotor.setPower(-0.8);
        }
        else {
            hardware.intakeMotor.setPower(0);
        }
    }

    public void launch() {
        if (gamepad2.left_trigger > 0) {
            hardware.leftLaunch.setPower(-0.7);
            hardware.rightLaunch.setPower(0.7);
            telemetry.addData("pew", "pew");
        }
        else if (gamepad2.right_trigger > 0)
        {
            hardware.leftLaunch.setPower(-0.5);
            hardware.rightLaunch.setPower(0.5);
            telemetry.addData("dome", "pew");
        }

        else {
            hardware.rightLaunch.setPower(0);
            hardware.leftLaunch.setPower(0);
        }
    }

    public void lift() {
        //climber code will go here
        if (gamepad2.dpad_up) {
            hardware.climbMotor.setPower(-0.5);
            telemetry.addData("Motor", "weeeee");
        }
        else if(gamepad2.dpad_down){
            hardware.climbMotor.setPower(0.5);
        }
        else {
            hardware.climbMotor.setPower(0);
        }

    }
}

