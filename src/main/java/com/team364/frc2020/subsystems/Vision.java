/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;
import static com.team364.frc2020.RobotMap.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.json.simple.JSONObject;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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
    public double[] closestTarget = new double[2];
    public static HashMap<String, Double> TARGET = new HashMap<String, Double>();
    public int cycles;
    private double lastHeight = 0;
    private ShuffleboardTab tab = Shuffleboard.getTab("Config");
    private Map<Double, Double[]> targetMap;
    
    public Vision() {
        register();
        closestTarget[0] = 0;
        closestTarget[1] = 0;
        cycles = 0;
    }

    public void findClosestTargets(double height, int whichSystem) {
        targetMap.keySet().forEach((key) -> {
            try {
                double value = targetMap.get(key)[whichSystem];
                double mathHold = Math.abs(height - value);
                // logic
                if (mathHold < Math.abs(closestTarget[0] - value) || mathHold < Math.abs(closestTarget[1] - value)) {
                    if (Math.abs(mathHold - closestTarget[0]) < Math.abs(mathHold - closestTarget[1])) {
                        closestTarget[1] = value;
                    } else {
                        closestTarget[0] = value;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<String, Double> calculateTarget() {
        HashMap<String, Double> finalTarget = new HashMap<>();
        double[] linearHolder = linearInterpolation();
        finalTarget.put("Shooter", linearHolder[0]);
        finalTarget.put("Hood", linearHolder[0]);
        return finalTarget;
    public double linearInterpolate(double first, double second, int whichSystem, double actual) {
        double holdOne = targetMap.get(first)[whichSystem];
        double holdTwo = targetMap.get(second)[whichSystem];
        double slope = (holdOne - holdTwo) / (first - second);
        double intercept = holdOne - (slope * holdOne);
        return (actual * slope) + intercept;
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