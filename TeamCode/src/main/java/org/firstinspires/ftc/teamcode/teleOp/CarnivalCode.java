package org.firstinspires.ftc.teamcode.teleOp;

        import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorEx;
        import com.qualcomm.robotcore.hardware.Gamepad;
        import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CarniCode")
public class CarnivalCode extends OpMode {


    RobotHardware hardware;
    //MAKE SURE TO CHANGE THESE. THESE ARE YOUR DRIVE MODES THAT YOU NEED FOR THE CHECKPOINT
    public static final double FAST_MODE = .9;

    public static final double MEDIUM_MODE = 0.7;
    public static final double SLOW_MODE = .45;
    double currentMode;
    ElapsedTime buttonTime = null;

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

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
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);
        drive();
        intake();
        launch();
        //lift();
        telemetry();
    }

    public void telemetry() {
        //this is the class you should put stuff in if you want to print to the phone.

    }

    public void drive() {
        double Forward = -gamepad1.left_stick_y; // This is reversed
        double Strafe = gamepad1.left_stick_x; // Counteract imperfect strafing
        double Turn = gamepad1.right_stick_x;

        double leftFrontPower;
        double leftRearPower;
        double rightFrontPower;
        double rightRearPower;

        leftFrontPower = Forward + Strafe + Turn;
        leftRearPower = Forward - Strafe + Turn;
        rightFrontPower = Forward - Strafe - Turn;
        rightRearPower = Forward + Strafe - Turn;

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

        //if (gamepad1.left_bumper) {
            currentMode = SLOW_MODE;

        //} else if (gamepad1.right_bumper) {
            currentMode = FAST_MODE;


            currentMode = MEDIUM_MODE;

        hardware.frontLeft.setPower(leftFrontPower * currentMode);
        hardware.rearLeft.setPower(leftRearPower * currentMode);
        hardware.frontRight.setPower(rightFrontPower * currentMode);
        hardware.rearRight.setPower(rightRearPower * currentMode);
    }

    public void intake() {
        if (currentGamepad1.left_trigger>0) {
            hardware.intakeMotor.setPower(0.8);
            telemetry.addData("the", "on");

       // } else if (gamepad2.circle) {
       //     hardware.intakeMotor.setPower(-0.8);
        }
        else if(previousGamepad1.cross) {
            hardware.intakeMotor.setPower(-0.8);
            ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
            time.reset();
            while (time.time() < 25){}
            hardware.leftLaunch.setPower(0);
            hardware.rightLaunch.setPower(0);
            hardware.intakeMotor.setPower(0);
        }
        else {
            hardware.intakeMotor.setPower(0);
        }
    }

    public void launch() {
        if (gamepad1.right_trigger> 0) {
            hardware.leftLaunch.setPower(-0.7);
            hardware.rightLaunch.setPower(0.7);
            telemetry.addData("pew", "pew");
        }
//        else if (gamepad2.right_trigger > 0)
//        {
//            hardware.leftLaunch.setPower(-0.5);
//            hardware.rightLaunch.setPower(0.5);
//            telemetry.addData("dome", "pew");
//        }

        else if (previousGamepad1.circle) {
            hardware.leftLaunch.setPower(-0.7);
            hardware.rightLaunch.setPower(0.7);
            hardware.intakeMotor.setPower(0.8);
            ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
            time.reset();
            while (time.time() < 50){}
            hardware.leftLaunch.setPower(0);
            hardware.rightLaunch.setPower(0);
            hardware.intakeMotor.setPower(0);
        }
        else {
            hardware.rightLaunch.setPower(0);
            hardware.leftLaunch.setPower(0);
        }
    }

//    public void lift() {
//        //climber code will go here
//        if (gamepad2.dpad_down) {
//            hardware.climbMotor.setPower(-0.5);
//            telemetry.addData("Motor", "weeeee");
//        }
//        else if(gamepad2.dpad_up){
//            hardware.climbMotor.setPower(0.5);
//        }
//        else {
//            hardware.climbMotor.setPower(0);
//        }
//
//    }
}

