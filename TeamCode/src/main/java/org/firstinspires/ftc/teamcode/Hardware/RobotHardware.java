package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class RobotHardware {
    public DcMotorEx frontLeft = null;
    public DcMotorEx rearLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx rearRight = null;
    public DcMotorEx leftLaunch = null;
    public DcMotorEx rightLaunch = null;
    public DcMotorEx intakeMotor = null;
    public DcMotorEx climbMotor = null;
    public DcMotorEx[] motors;

    public BNO055IMU imu;

    public RobotHardware() {}

    public void init(HardwareMap hardwareMap){
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        initializeIntakeMotors(hardwareMap);
        initializeOutTakeMotors(hardwareMap);
        initializeClimbMotors(hardwareMap);
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
    intakeMotor = hardwareMap.get(DcMotorEx.class, RobotIDS.INTAKE_MOTOR);

    for(DcMotorEx motor : motors ) {
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
    //set up IMU
    imu = hardwareMap.get(BNO055IMU.class, RobotIDS.IMU);
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
    parameters.mode = BNO055IMU.SensorMode.IMU;
    parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
    parameters.loggingEnabled = false;
    imu.initialize(parameters);
}

public void initializeOutTakeMotors(HardwareMap hardwareMap){
    leftLaunch = hardwareMap.get(DcMotorEx.class, RobotIDS.FLYWHEEL_MOTOR1);
    rightLaunch = hardwareMap.get(DcMotorEx.class, RobotIDS.FLYWHEEL_MOTOR2);

    for(DcMotorEx motor : motors ) {
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

public void initializeClimbMotors(HardwareMap hardwareMap) {
    climbMotor = hardwareMap.get(DcMotorEx.class, RobotIDS.CLIMB_MOTOR);

    for(DcMotorEx motor : motors ) {
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

public void initializeServos(HardwareMap hardwareMap){
    //this is where all of your servos will go eventually
    
}
}