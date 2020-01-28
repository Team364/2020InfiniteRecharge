package com.team364.frc2020;

public class RobotMap {

    // Hardware
    public static final int primaryPCM = 0;

    //hardware
    public static final int TRACKWIDTH = 21;
    public static final int WHEELBASE = 26;
    public static final int WHEELDIAMETER = 3;
    
    public static final int FLANGLE = 16;//FL 2-> BR 8/////////////16
    public static final int FLDRIVE = 36;//FL 1-> BR 7/////////////36
    public static final int FRANGLE = 32;//FR 6-> BL 12////////////32
    public static final int FRDRIVE = 12;//FR 5-> BL 3/////////////12
    public static final int BRANGLE = 33;//BR 8-> FL 2
    public static final int BRDRIVE = 15;//BR 7-> FL 1
    public static final int BLANGLE = 37;//was 4 //BL 12-> FR
    public static final int BLDRIVE = 39;//BL 3-> FR

    public static final int PIGEON = 13;

    //Offsets 
    /**Front Left */
    public static final int MOD0OFFSET = 82;
    /**Front Right */
    public static final int MOD1OFFSET = 53;
    /**Back Left */
    public static final int MOD2OFFSET = 195;
    /**Back Right */
    public static final int MOD3OFFSET = 124;

    //DRIVE INVERT
    /**Front Left */
    public static final boolean MOD0DRIVEINVERT = true;
    /**Front Right */
    public static final boolean MOD1DRIVEINVERT = false;
    /**Back Left */
    public static final boolean MOD2DRIVEINVERT = false;
    /**Back Right */
    public static final boolean MOD3DRIVEINVERT = false;


    //constants
    public static final double STICKDEADBAND = 0.2;
    public static final double ENCODERTICKS = 1024.0;
    public static final double OFFSETTOSTRAIGHT = 180;

    //constants for shooter
    public static final double SHOOTERSPEED = 0;
    public static final double FERRYSPEED = 0;
    //public static final double ANGLE_TICKS_PER_RADIAN = ENCODERTICKS / (2.0 * Math.PI);

    // PID constants
    public static final int SlotIdx = 0;
    public static final int PIDLoopIdx = 0;
    public static final int TimeoutMs = 20;


    public static final int SLOTIDX = 0;
    public static final int SWERVETIMEOUT = 20;

    public static final double ANGLEP = 7.5;//20
    public static final double ANGLEI = 0.0;//0.001
    public static final double ANGLED = 100;//130 //200

    public static final int ANGLECONTINUOUSCURRENTLIMIT = 30;
    public static final int ANGLEPEAKCURRENT = 30;
    public static final int ANGLEPEAKCURRENTDURATION = 100;
    public static final boolean ANGLEENABLECURRENTLIMIT = true;
    public static final double kSwerveRotationMaxSpeed = 1250.0 * 0.8;

    public static final int DRIVECONTINUOUSCURRENTLIMIT = 30;
    public static final int DRIVEPEAKCURRENT = 30;
    public static final int DRIVEPEAKCURRENTDURATION = 100;
    public static final boolean DRIVEENABLECURRENTLIMIT = true;

    public static final int TURRET = 1;


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
}
