package com.team364.frc2020;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;

public class RobotMap {
    //JSON Configuration Files
    public static final String TARGETJSON = "/home/lvuser/TargetValues.json";
    public static final String SWERVEJSON = "/home/lvuser/SwerveOffsets.json";

    /*****NON MOTOR CAN ID'S *******/
    public static final int primaryPCM = 0;


    /*********ALL MOTORS CAN ID'S*********/ 

    public static final int FLDRIVE = 9;    
    public static final int FLANGLE = 2;
    public static final int FRDRIVE = 3;
    public static final int FRANGLE = 4;
    public static final int BLDRIVE = 5;
    public static final int BLANGLE = 6;
    public static final int BRDRIVE = 7;
    public static final int BRANGLE = 8;
    public static final int FLOORROLLERS = 9;
    public static final int SIDEROLLERS = 10;    
    public static final int TURRET = 11;
    public static final int SHOOTER = 12;
    public static final int SHOOTERSLAVE = 13;
    public static final int INTAKE = 14;
    public static final int HANG = 15;

    

    /***************** SWERVE ****************/

    //Drivetrain constants
    public static final int TRACKWIDTH = 21;
    public static final int WHEELBASE = 26;
    public static final int WHEELDIAMETER = 3;
    public static final double kMaxSpeedMetersPerSecond = 4.572;
    public static final double kMaxSwerveDriveVelocity = 22700.0;
    
    //Distance between front and back wheels on robot
    public static final SwerveDriveKinematics SWERVEKINEMATICS =
    new SwerveDriveKinematics(
    new Translation2d(WHEELBASE * 0.0254 / 2, TRACKWIDTH * 0.0254 / 2),
    new Translation2d(WHEELBASE * 0.0254 / 2, -TRACKWIDTH * 0.0254 / 2),
    new Translation2d(-WHEELBASE * 0.0254 / 2, TRACKWIDTH * 0.0254 / 2),
    new Translation2d(-WHEELBASE * 0.0254 / 2, -TRACKWIDTH * 0.0254 / 2));


    //Swerve CANCoder ID's
    public static final int FLCAN = 1;
    public static final int FRCAN = 2;
    public static final int BLCAN = 3;
    public static final int BRCAN = 4;

    //Pigeon ID
    public static final int PIGEON = 10; //Pigeon Connect to TalonSRX
    
    //MODULE ANGLE OFFSETS
    public static double MOD1OFFSET = 0; //Front Left
    public static double MOD2OFFSET = 0; //Front Right
    public static double MOD3OFFSET = 0; //Back Left
    public static double MOD4OFFSET = 0; //Back Right

    //DRIVE MOTOR INVERT
    public static final boolean MOD1DRIVEINVERT = true; //Front Left
    public static final boolean MOD2DRIVEINVERT = true; //Front Right
    public static final boolean MOD3DRIVEINVERT = true; //Back Left
    public static final boolean MOD4DRIVEINVERT = false; //Back Right
    
    //ANGLE MOTOR INVERT
    public static final boolean MOD1ANGLEINVERT = false; //Front Left
    public static final boolean MOD2ANGLEINVERT = false; //Front Right
    public static final boolean MOD3ANGLEINVERT = false; //Back Left
    public static final boolean MOD4ANGLEINVERT = false; //Back Right
      
    //ANGLE CANCODER PHASE
    public static final boolean MOD1ANGLEPHASE = true; //Front Left
    public static final boolean MOD2ANGLEPHASE = true; //Front Right
    public static final boolean MOD3ANGLEPHASE = true; //Back Left
    public static final boolean MOD4ANGLEPHASE = true; //Back Right
    
    //SWERVE CONSTANTS
    public static final double STICKDEADBAND = 0.2;
    public static final double ENCODERTICKS = 4096.0; // CANCoder Encoder Ticks
    public static final double OFFSETTOSTRAIGHT = 180;    

    //SWERVE PID CONSTANTS
    public static final int PIDLoopIdx = 0;
    public static final int SLOTIDX = 0;   
    public static final int SWERVETIMEOUT = 20;

    public static final double ANGLEP = 2;//20
    public static final double ANGLEI = 0.0;//0.001
    public static final double ANGLED = 200;//130 //200

    //SWERVE MOTORS CURRENT LIMITING
    public static final int ANGLECONTINUOUSCURRENTLIMIT = 25;
    public static final int ANGLEPEAKCURRENT = 30;
    public static final double ANGLEPEAKCURRENTDURATION = 0.1;
    public static final boolean ANGLEENABLECURRENTLIMIT = true;

    public static final int DRIVECONTINUOUSCURRENTLIMIT = 35;
    public static final int DRIVEPEAKCURRENT = 60;
    public static final double DRIVEPEAKCURRENTDURATION = 0.1;
    public static final boolean DRIVEENABLECURRENTLIMIT = true;

    //SWERVE PROFILING 
    public static final double SWERVEMAXSPEED = 22500;
    public static final double SWERVEMAX_ANGLEVELOCITY = 26000;
    public static final double SWERVEMAX_ANGLEACCELERATION = 15000;



    /***************** TURRET ****************/ 

    //Turret Hard and Soft Limits
    public static final int TURRETMINHARD = 0;
    public static final int TURRETMAXHARD = 94680;
    public static final int TURRETMINSOFT = TURRETMINHARD + 2000;
    public static final int TURRETMAXSOFT = TURRETMAXHARD - 2000;
    public static final int ADDITIVESOFT = 1000;
    
    //Turret Scanning Range
    public static final double LEFTTURRETRANGE = -10;
    public static final double RIGHTTURRETRANGE = 30;

    //TURRET PID CONSTANTS
    public static final double TURRETKP = 0.1;
    public static final double TURRETKI = 0.0;
    public static final double TURRETKD = 0.0;

    //TURRET Motor Consants
    public static final boolean TURRETINVERT = true;
    public static final NeutralMode TURRETNEUTRALMODE = NeutralMode.Coast;

    //Turret Current Limiting
    public static final int TURRETCONTINUOUSCURRENTLIMIT = 25;
    public static final int TURRETPEAKCURRENT = 30;
    public static final double TURRETPEAKCURRENTDURATION = 0.1;
    public static final boolean TURRETENABLECURRENTLIMIT = true;




    /***************** SHOOTER ****************/

    //SHOOTER PID Constants
    public static final double SHOOTERKP = 0.75;
    public static final double SHOOTERKI = 0.0;
    public static final double SHOOTERKD = 0.0;
    public static final double SHOOTERKF = 0.046976;

    //SHOOTER Motor Consants
    public static final boolean SHOOTERINVERT = true;
    public static final NeutralMode SHOOTERNEUTRALMODE = NeutralMode.Coast;

    //SHOOTER Current Limiting
    public static final int SHOOTERCONTINUOUSCURRENTLIMIT = 35;
    public static final int SHOOTERPEAKCURRENT = 60;
    public static final double SHOOTERPEAKCURRENTDURATION = 0.1;
    public static final boolean SHOOTERENABLECURRENTLIMIT = true;
    


    /***************** HOOD ****************/

    //HOOD Servo PWM Ports
    public static final int HOOD = 0;
    public static final int HOODSLAVE = 1;

    //HOOD Encoder Analog Input
    public static final int HOODENCODER = 0;
    
    //constants for hood
    public static final int SERVOHOOD1 = 0;
    public static final int SERVOHOODSLAVE = 0;

    //constats for WoF
    public static final int MOTORCOLORNONE = 0;
    public static final double MOTORCOLORRED = 0.1;
    public static final double MOTORCOLORGREEN = 10;
    public static final double MOTORCOLORBLUE = -1;
    public static final double MOTORCOLORYELLO = -0.5;

    //constants for Climber
    public static final int CLIMBER = 24;

    //HOOD Encoder Analog Offset
    public static final double HOODOFFSET = 295.5;

    //HOOD PID CONSTANTS
    public static final double HOODKP = 0.01;
    public static final double HOODKI = 0.0;
    public static final double HOODKD = 0.0;

    public static final double HOODTOLERANCE = 5.0; //Allowable tolerance for PID Controller


    /***************** HOPPER ****************/

    //HOPPER Infrared Dio Sensor
    public static final int HOPPERIR = 0;

    //HOPPER Current Limiting
    public static final int HOPPERCONTINUOUSCURRENTLIMIT = 35;
    public static final int HOPPERPEAKCURRENT = 60;
    public static final double HOPPERPEAKCURRENTDURATION = 0.1;
    public static final boolean HOPPERENABLECURRENTLIMIT = true;



    /***************** HANG ****************/

    //HANG Hard and Soft Limits
    public static final int HANGMINSOFT = 0;
    public static final int HANGMAXSOFT = 568873;

    //HANG Motor Consants
    public static final boolean HANGINVERT = true;
    public static final NeutralMode HANGNEUTRALMODE = NeutralMode.Brake;

    //HANG Current Limiting
    public static final int HANGCONTINUOUSCURRENTLIMIT = 35;
    public static final int HANGPEAKCURRENT = 60;
    public static final double HANGPEAKCURRENTDURATION = 0.1;
    public static final boolean HANGENABLECURRENTLIMIT = true;







    /***************** VISION ****************/

    public static final double TARGETHEIGHTDIFFERENCE = 62.0;//inches
    public static final double LIMELIGHTANGLE = 35.0;//degrees    

    public static final double SIMPLEVELOCITY = 4000;
    public static final double SIMPLEANGLE = 0;
}
