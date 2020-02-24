package com.team364.frc2020;

import static com.team364.frc2020.RobotMap.*;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

public class Conversions {

    public static double toCounts(double units) {
        return units * ENCODERTICKS / 360.0;
    }
    public static double to180Boundaries(double degrees){
        if(degrees > 180){
            degrees -= 360;
        }else if(degrees < -180){
            degrees += 360;
        }
        return degrees;
    }

    public static double toDegrees(double units) {
        return units * (360 / ENCODERTICKS);
    }

    public static double modulate360(double units) {
        return units %= 360;
    }

    public static int modulateEncoder(int units) {
        return units %= ENCODERTICKS;
    }

    /**
     * @return encoder counts for the drive wheel to reach so that angle desired is
     *         acheived whilst in X-drive formation
     */
    public static double degreeToCounts(double units) {
        return units / 360 * WHEELBASE * ENCODERTICKS / WHEELDIAMETER;
    }

    public static double deadband(double input) {
        if (Math.abs(input) < STICKDEADBAND)
            return 0;
        return input;
    }

    // For the shooter
    /**
     * 
     * @param oldVel
     * @return converts input rpm to falcon velocity sensor value
     */
    public double toSensorVel(double oldVel) {
        double newVel = oldVel;
        // reduction gear ratio
        newVel *= 0.45;
        // to sensor value from rpm
        newVel *= 3.4133;
        return newVel;
    }

    /**
     * 
     * @param oldVel
     * @return converts falcon velocity sensor value to rpm
     */
    public double toRpmVel(double oldVel) {
        double newVel = oldVel;
        // to rpm from sensor value
        newVel *= .2929;
        // upduction gear ratio
        newVel *= 2.222;
        return newVel;
    }

    public static Trajectory toTrajectory() {
        String trajectoryJSON = "paths/firstPath.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            return trajectory;
        } catch (IOException e) {
            SmartDashboard.putString("conversion error", e.toString());
            return null;
        }
    }
    public static double toInchesPerSecond(double MetersPerSecond){
        return MetersPerSecond * 39.37;
    }
}