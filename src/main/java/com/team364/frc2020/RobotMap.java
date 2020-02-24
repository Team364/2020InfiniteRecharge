package com.team364.frc2020;

import com.ctre.phoenix.sensors.CANCoderConfiguration;

public class RobotMap {
    public static final String TARGETJSON = "/home/lvuser/TargetValues.json";
    public static final String SWERVEJSON = "/home/lvuser/SwerveOffsets.json";

    public static final double TARGETHEIGHTDIFFERENCE = 0;
    public static final double LIMELIGHTANGLE = 0;

    public static final double SWERVEMAXSPEED = 22500;
    public static final double SWERVEMAX_ANGLEVELOCITY = 26000;
    public static final double SWERVEMAX_ANGLEACCELERATION = 15000;

    public static final double HEIGHTDEADBAND = 1.0;
    // Hardware
    public static final int primaryPCM = 0;

    //hardware
    public static final int TRACKWIDTH = 21;
    public static final int WHEELBASE = 26;
    public static final int WHEELDIAMETER = 3;

    public static final int FLDRIVE = 1;    
    public static final int FLANGLE = 2;
    public static final int FRDRIVE = 3;
    public static final int FRANGLE = 4;
    public static final int BLDRIVE = 5;
    public static final int BLANGLE = 6;
    public static final int BRDRIVE = 7;
    public static final int BRANGLE = 8;

    //CANCoder Swerve Angle
    public static final int FLCAN = 1;
    public static final int FRCAN = 2;
    public static final int BLCAN = 3;
    public static final int BRCAN = 4;


    public static final int PIGEON = 13;
    
    //DRIVE INVERT
    /**Front Left */
    public static double MOD1OFFSET = 275.361;
    /**Front Right */
    public static double MOD2OFFSET = -13.447;
    /**Back Left */
    public static double MOD3OFFSET = -27.773;
    /**Back Right */
    public static double MOD4OFFSET = 63.897;

    //CANCoder Config
    /**Front Left */
    public static CANCoderConfiguration CAN1CONFIG = new CANCoderConfiguration();
    /**Front Right */
    public static CANCoderConfiguration CAN2CONFIG = new CANCoderConfiguration();
    /**Back Left */
    public static CANCoderConfiguration CAN3CONFIG = new CANCoderConfiguration();
    /**Back Right */
    public static CANCoderConfiguration CAN4CONFIG = new CANCoderConfiguration();

    //DRIVE INVERT
    /**Front Left */
    public static final boolean MOD1DRIVEINVERT = false;
    /**Front Right */
    public static final boolean MOD2DRIVEINVERT = true;
    /**Back Left */
    public static final boolean MOD3DRIVEINVERT = false;
    /**Back Right */
    public static final boolean MOD4DRIVEINVERT = true;
    
    //ANGLE INVERT
    /**Front Left */
    public static final boolean MOD1ANGLEINVERT = false;
    /**Front Right */
    public static final boolean MOD2ANGLEINVERT = false;
    /**Back Left */
    public static final boolean MOD3ANGLEINVERT = false;
    /**Back Right */
    public static final boolean MOD4ANGLEINVERT = false;
      
    //ANGLE PHASE
    /**Front Left */
    public static final boolean MOD1ANGLEPHASE = true;
    /**Front Right */
    public static final boolean MOD2ANGLEPHASE = true;
    /**Back Left */
    public static final boolean MOD3ANGLEPHASE = true;
    /**Back Right */
    public static final boolean MOD4ANGLEPHASE = true;
    


    //constants
    public static final double STICKDEADBAND = 0.2;
    public static final double ENCODERTICKS = 4096.0;
    public static final double OFFSETTOSTRAIGHT = 180;
    //public static final double ANGLE_TICKS_PER_RADIAN = ENCODERTICKS / (2.0 * Math.PI);

    // PID constants
    public static final int PIDLoopIdx = 0;
    public static final int TimeoutMs = 20;
    public static final int SLOTIDX = 0;

    
    public static final int SWERVETIMEOUT = 20;

    public static final double ANGLEP = 2.5;//20
    public static final double ANGLEI = 0.0;//0.001
    public static final double ANGLED = 200;//130 //200

    public static final int ANGLECONTINUOUSCURRENTLIMIT = 25;
    public static final int ANGLEPEAKCURRENT = 30;
    public static final double ANGLEPEAKCURRENTDURATION = 0.1;
    public static final boolean ANGLEENABLECURRENTLIMIT = true;


    public static final int DRIVECONTINUOUSCURRENTLIMIT = 35;
    public static final int DRIVEPEAKCURRENT = 40;
    public static final double DRIVEPEAKCURRENTDURATION = 0.1;
    public static final boolean DRIVEENABLECURRENTLIMIT = true;

    public static final int TURRET = 11;

    //PID constants for shooter
    public static final double SHOOTING_P = 0;
    public static final double SHOOTING_I = 0;
    public static final double SHOOTING_D = 0;
    public static final double[] SHOOTING_PID = { SHOOTING_P, SHOOTING_I, SHOOTING_D };

    public static final double FERRY_P = 0;
    public static final double FERRY_I = 0;
    public static final double FERRY_D = 0;
    public static final double[] FERRY_PID = { FERRY_P, FERRY_I, FERRY_D };

    public static final double RAMP_UP_P = 0;
    public static final double RAMP_UP_I = 0;
    public static final double RAMP_UP_D = 0;
    public static final double[] RAMP_UP_PID = { RAMP_UP_P, RAMP_UP_I, RAMP_UP_D };

    public static final double RAMP_DOWN_P = 0;
    public static final double RAMP_DOWN_I = 0;
    public static final double RAMP_DOWN_D = 0;
    public static final double[] RAMP_DOWN_PID = { RAMP_DOWN_P, RAMP_DOWN_I, RAMP_DOWN_D };

    //constants for shooter
    public static final double SHOOTERSPEED = 0;
    public static final double FERRYSPEED = 0;
    
}
