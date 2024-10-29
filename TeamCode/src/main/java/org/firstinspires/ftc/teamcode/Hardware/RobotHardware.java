package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class RobotHardware {
    public DcMotorEx frontLeft = null;
    public DcMotorEx rearLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx rearRight = null;
    public DcMotorEx leftLaunch = null;
    public DcMotorEx rightLaunch = null;
    public DcMotorEx intakeMotor = null;


    public DcMotorEx[] motors;

    public RobotHardware() {}

    public void init(HardwareMap hardwareMap){
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        initializeIntakeMotors(hardwareMap);
        initializeOutTakeMotors(hardwareMap);
        initializeServos(hardwareMap);
    }

public void initializeDriveMotors(HardwareMap hardwareMap){
        //getting the motor ids from the hardwaremap you will create
    frontLeft = hardwareMap.get(DcMotorEx.class, RobotIDS.LEFT_FRONT_MOTOR);
    frontRight = hardwareMap.get(DcMotorEx.class, RobotIDS.RIGHT_FRONT_MOTOR);
    rearLeft = hardwareMap.get(DcMotorEx.class, RobotIDS.LEFT_REAR_MOTOR);
    rearRight = hardwareMap.get(DcMotorEx.class, RobotIDS.RIGHT_REAR_MOTOR);


    //this is the list of the motors you will use for the drivetrain
    motors = new DcMotorEx[]{frontLeft, frontRight, rearLeft, rearRight}; 

    //setting the directions specific to the mecanum drive train. If you have a different drive train come ask.
    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
    rearRight.setDirection(DcMotorSimple.Direction.FORWARD);


    for(DcMotorEx motor : motors ){
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

public void initializeIntakeMotors(HardwareMap hardwareMap){
    frontLeft = hardwareMap.get(DcMotorEx.class, RobotIDS.INTAKE_MOTOR);

    motors = new DcMotorEx[]{intakeMotor};

    intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

    for(DcMotorEx motor : motors ){
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

public void initializeOutTakeMotors(HardwareMap hardwareMap){
    leftLaunch = hardwareMap.get(DcMotorEx.class, RobotIDS.FLYWHEEL_MOTOR1);
    rightLaunch = hardwareMap.get(DcMotorEx.class, RobotIDS.FLYWHEEL_MOTOR2);

    motors = new DcMotorEx[]{leftLaunch, rightLaunch};

    leftLaunch.setDirection(DcMotorSimple.Direction.FORWARD);
    rightLaunch.setDirection(DcMotorSimple.Direction.REVERSE);

    for(DcMotorEx motor : motors ){
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

public void initializeServos(HardwareMap hardwareMap){
    //this is where all of your servos will go eventually
}
}