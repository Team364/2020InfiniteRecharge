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
    public static enum TurretStates {
        GYRO, VISION, FLIPPING, NO_TRACK, NOT_CALIBRATED
    }
    public static enum ConfigStates {
        TARGET, SWERVE, MATCH, INIT
    }


    /**
     * This is essentially intended to be used for tracking whether or not vision is
     * being used to navigate but auto drive mode will be included here too
     */
    public static enum DriveStates {
        OPEN_LOOP, VISION, AUTO
    }


    public static ColorStates colorState = ColorStates.NONE;
    public static ConfigStates configState = ConfigStates.INIT;
    public static TurretStates turretState = TurretStates.NOT_CALIBRATED;
    public static DriveStates driveState = DriveStates.OPEN_LOOP;

}
