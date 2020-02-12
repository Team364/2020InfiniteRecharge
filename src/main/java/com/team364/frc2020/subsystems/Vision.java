/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Configuration.*;

import java.lang.annotation.Target;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Vision implements Subsystem {
    public double[] closestTarget = new double[2];
    public int cycles;
    
    public Vision() {
        register();
        closestTarget[0] = 0;
        closestTarget[1] = 0;
        cycles = 0;
    }

    public void findClosestTargets(double height, int whichSystem) {
        TargetJson.getMap().keySet().forEach((key) -> {
            // difference between actual and the iteration
            double mathHold = Math.abs(height - key);
            // closest logic
            if (mathHold < Math.abs(closestTarget[0] - key) || mathHold < Math.abs(closestTarget[1] - key)) {
                if (Math.abs(mathHold - closestTarget[0]) < Math.abs(mathHold - closestTarget[1])) {
                    closestTarget[1] = key;
                } else {
                    closestTarget[0] = key;
                }
            }
        });
    }

    public double linearInterpolate(double first, double second, int whichSystem, double actual) {
        double holdOne = TargetJson.getMap().get(first).get(whichSystem);
        double holdTwo = TargetJson.getMap().get(second).get(whichSystem);
        double slope = (holdOne - holdTwo) / (first - second);
        double intercept = holdOne - (slope * holdOne);
        return (actual * slope) + intercept;
    }

    public double targetLogic(int whichSystem){
        findClosestTargets(limeY(), whichSystem);
        return linearInterpolate(closestTarget[0], closestTarget[1], whichSystem, limeY());
    }

    public double limeX() {
        return 1.0;
    }

    public double limeY() {
        return 1.0;
    }
    @Override
    public void periodic(){
        
    }


}