package com.team364.frc2020;

public class States {


    /**
     * Target states of turret:
     * Gyro for basic tracking while moving, 
     * Vision for accurate tracking to shoot
     */
    public static enum TargetStates {
        GYRO, VISION, NO_TRACK
    }

    public static enum ShooterStates {
        SHOOTING, FERRY, RAMP_UP, RAMP_DOWN;
    }

    /**
     * This is essentially intended to be used for tracking whether or not vision is
     * being used to navigate but auto drive mode will be included here too
     */
    public static enum DriveStates {
        OPEN_LOOP, VISION, AUTO
    }



    public static TargetStates targetState = TargetStates.NO_TRACK;
    public static DriveStates driveState = DriveStates.OPEN_LOOP;
    public static ShooterStates shooterState = ShooterStates.FERRY;

}
