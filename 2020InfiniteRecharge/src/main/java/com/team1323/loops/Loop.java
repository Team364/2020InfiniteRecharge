package com.team1323.loops;

import java.util.List;

/**
 * Interface for loops, which are routine that run periodically in the robot
 * code (such as periodic gyroscope calibration, etc.)
 */
public abstract class Loop {

    public abstract void onStart(double timestamp);

    public abstract void onLoop(double timestamp);

    public abstract void onStop(double timestamp);
}
