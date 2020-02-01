/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static com.team364.frc2020.States.*;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.RobotContainer.*;
import java.io.FileReader;
import java.io.Reader;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Turret implements Subsystem {
    public HashMap<Double, String> visionHeights = new HashMap();
    public double[] closestTarget = new double[2];

    public FileReader targetFile;
    public TalonFX turretFx;

    public SupplyCurrentLimitConfiguration turretSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

    public Turret(){
        register();
        turretFx = new TalonFX(TURRET);
        try{
            targetFile = new FileReader("C:/Users/Fusion364/Documents/GitHub/2020InifiteRecharge/Targets.JSON");
        } catch(Exception e){}

        // Configure turret Motor
        turretFx.configFactoryDefault();
        turretFx.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 20);
        turretFx.selectProfileSlot(0, 0);
        // turretFx.setSensorPhase(invertSensorPhase);
        turretFx.config_kF(0, 0);
        turretFx.config_kP(0, 3);
        turretFx.config_kI(0, 0);
        turretFx.config_kD(0, 0);
        turretFx.setNeutralMode(NeutralMode.Brake);
        turretFx.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 10);

        // Setup Current Limiting
        turretFx.configSupplyCurrentLimit(turretSupplyLimit, 20);

        //fill in other heights
        visionHeights.put(1.0, "height1");
        
    }
    

    public double visionTrack(){
        double height = 0;
        return height;
    }

    public void setTarget(){
        findClosestTargets(visionTrack());
        TARGET = calculateTarget();
    }

    public HashMap<String, Double> calculateTarget(){
        HashMap<String, Double> finalTarget = new HashMap<>();
        //finalTarget.put("Shooter", );
        //finalTarget.put("Hood", );
        return finalTarget;
    }/*
    public double linearInterpolation(){
        double[] JSONplots = {
            
            targetFile.
            targetFile.get(visionHeights.get(closestTarget[0])),
            targetFile.get(visionHeights.get(closestTarget[0]))
        }
        return 
    }*/

    public void findClosestTargets(double height){
        visionHeights.forEach((target, name) -> {
            double holder = Math.abs(height - target);
            if(holder < closestTarget[0] || holder < closestTarget[1] ){
                if(Math.abs(holder - closestTarget[0]) < Math.abs(holder - closestTarget[1])){
                    closestTarget[1] = height;
                }
                else{
                    closestTarget[0] = height;
                }
            }
        });
    }

    @Override
    public void periodic() {
        if(THE_SWITCH){
            //setTarget();
        }
  }




}
