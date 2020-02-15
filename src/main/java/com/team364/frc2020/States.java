package com.team364.frc2020;

public class States {
    /**
     * States for the color sensor
     */

     public static enum ColorStates {
         RED, GREEN, BLUE, YELLO, NONE
     }

    /**
     * Target states of turret:
     * Gyro for basic tracking while moving, 
     * Vision for accurate tracking to shoot
     */
    public static enum TargetStates {
        GYRO, VISION, NO_TRACK
    }
    public static enum ConfigStates {
        TARGET, SWERVE, MATCH
    }


    /**
     * This is essentially intended to be used for tracking whether or not vision is
     * being used to navigate but auto drive mode will be included here too
     */
    public static enum DriveStates {
        OPEN_LOOP, VISION, AUTO
    }

    public static enum ShooterStates {
        SHOOTING, FERRY, RAMP_UP, RAMP_DOWN;
    }

    public static ColorStates colorState = ColorStates.NONE;
    public static ConfigStates configState = ConfigStates.MATCH;
    public static TargetStates targetState = TargetStates.NO_TRACK;
    public static DriveStates driveState = DriveStates.OPEN_LOOP;
    public static ShooterStates shooterState = ShooterStates.FERRY;

}
