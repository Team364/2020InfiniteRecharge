/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Configuration.*;
import static com.team364.frc2020.RobotMap.*;

import java.util.ArrayList;
import java.util.List;

import com.team364.frc2020.Robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Vision implements Subsystem {
    public List<Double> closestTarget = new ArrayList<Double>();
    public int cycles;

    public Vision() {
        register();
        closestTarget.add(100000.0);
        closestTarget.add(100000.0);
        cycles = 0;
    }

    public void findClosestTargets(double distance, int whichSystem) {
        try{
            TargetJson.getMap().keySet().forEach((ObjectKey) -> {
                double key = Double.valueOf(ObjectKey.toString());
                // difference between actual and the iteration
                double mathHold = Math.abs(distance - key);
                // closest target logic
                if (mathHold < Math.abs(closestTarget.get(0) - distance) || mathHold < Math.abs(closestTarget.get(1) - distance)) {
                    if (Math.abs(mathHold - closestTarget.get(0)) < Math.abs(mathHold - closestTarget.get(1))) {
                        if(key != closestTarget.get(0)){
                            closestTarget.set(1, key);
                        }
                    } else {
                        if(key != closestTarget.get(1)){
                            closestTarget.set(0, key);
                        }
                    }
                }
            });
        }catch(Exception e){SmartDashboard.putString("error", e.toString());}
    }

    public double linearInterpolate(Double first, Double second, int whichSystem, double actual) {
        double holdOne = TargetJson.getMap().get(first.toString()).get(whichSystem);
        double holdTwo = TargetJson.getMap().get(second.toString()).get(whichSystem);
        double slope = (holdOne - holdTwo) / (first - second);
        SmartDashboard.putNumber("slope", slope);
        double intercept = holdOne - (slope * first);
        SmartDashboard.putNumber("intercept", intercept);
        return (actual * slope) + intercept;
    }

    public double targetLogic(int whichSystem){
        SmartDashboard.putNumber("distance", getDistance());
        if(true){
            findClosestTargets(20.0, whichSystem);
            double hold = linearInterpolate(closestTarget.get(0), closestTarget.get(1), whichSystem, 20.0);
            SmartDashboard.putNumber("output", hold);
            return hold;            
        }else{
            return whichSystem == 0 ? SIMPLEVELOCITY : SIMPLEANGLE;
        }
    }

    public double limeX() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }

    public double limeY() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }

    public boolean hasTarget(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 0 ? false : true;
    }

    public double getDistance(){
        return TARGETHEIGHTDIFFERENCE / Math.tan(Math.toRadians(limeY() + LIMELIGHTANGLE));
    }


    @Override
    public void periodic(){
        SmartDashboard.putNumber("distance", getDistance());
        Robot.Distance.setString(String.valueOf(getDistance()));
    }


}