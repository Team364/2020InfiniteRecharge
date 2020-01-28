package com.team364.frc2020;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.CAN;

public class RobotMap {

    // Hardware
    public static final int primaryPCM = 0;
    public static final PigeonIMU pigeon = new PigeonIMU(new TalonSRX(10));


    //hardware
    public static final int TRACKWIDTH = 21;
    public static final int WHEELBASE = 26;
    public static final int WHEELDIAMETER = 3;

    public static final int FLDRIVE = 1;    
    public static final int FLANGLE = 2;
    public static final int FRDRIVE = 3;
    public static final int FRANGLE = 4;
    public static final int BRDRIVE = 5;
    public static final int BRANGLE = 6;
    public static final int BLDRIVE = 7;
    public static final int BLANGLE = 8;

    //CANCoder Swerve Angle
    public static final int FLCAN = 1;
    public static final int FRCAN = 2;
    public static final int BLCAN = 3;
    public static final int BRCAN = 4;


    public static final int PIGEON = 13;
    
    //DRIVE INVERT
    /**Front Left */
    public static final double MOD1OFFSET = 0;
    /**Front Right */
    public static final double MOD2OFFSET = 0;
    /**Back Left */
    public static final double MOD3OFFSET = 0;
    /**Back Right */
    public static final double MOD4OFFSET = 0;

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
    public static final boolean MOD1DRIVEINVERT = true;
    /**Front Right */
    public static final boolean MOD2DRIVEINVERT = false;
    /**Back Left */
    public static final boolean MOD3DRIVEINVERT = false;
    /**Back Right */
    public static final boolean MOD4DRIVEINVERT = false;


    //constants
    public static final double STICKDEADBAND = 0.2;
    public static final double ENCODERTICKS = 1024.0;
    public static final double OFFSETTOSTRAIGHT = 180;
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

    //constants for shooter
    public static final double SHOOTERSPEED = 0;
    public static final double FERRYSPEED = 0;
    
}
