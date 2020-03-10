/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotMap.TURRET;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static com.team364.frc2020.Robot.*;
import static com.team364.frc2020.RobotMap.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Turret implements Subsystem {
    public TalonFX turretFx;
    public boolean controlled;
       
    

    public Turret() {
        register();
        controlled = false;

        turretFx = new TalonFX(TURRET);       

        // Configure turret Motor
        turretFx.configFactoryDefault();
        TalonFXConfiguration turretFxConfiguration = new TalonFXConfiguration();
        
        // Configure Turret PID
        turretFxConfiguration.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
        turretFxConfiguration.slot0.kP = TURRETKP;
        turretFxConfiguration.slot0.kI = TURRETKI;
        turretFxConfiguration.slot0.kD = TURRETKD;

        // Configure Turret Min and Max Limits
        turretFxConfiguration.reverseSoftLimitThreshold = TURRETMINSOFT;
        turretFxConfiguration.forwardSoftLimitThreshold = TURRETMAXSOFT;
        
        // Setup Current Limiting
        SupplyCurrentLimitConfiguration turretSupplyLimit = new SupplyCurrentLimitConfiguration(TURRETENABLECURRENTLIMIT, TURRETCONTINUOUSCURRENTLIMIT, TURRETPEAKCURRENT, TURRETPEAKCURRENTDURATION);
        turretFxConfiguration.supplyCurrLimit = turretSupplyLimit;
        

        //Write Settings to Turret Motor
        turretFx.configAllSettings(turretFxConfiguration);
        turretFx.setNeutralMode(TURRETNEUTRALMODE);
        turretFx.setInverted(TURRETINVERT);
        turretFx.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);

    }

    public void setPosition(double pos){
        if(pos < TURRETMINSOFT + ADDITIVESOFT){
            turretFx.set(ControlMode.Position, TURRETMINSOFT + ADDITIVESOFT);
        } else if(pos > TURRETMAXSOFT - ADDITIVESOFT){
            turretFx.set(ControlMode.Position, TURRETMAXSOFT - ADDITIVESOFT);
        }else{
            turretFx.set(ControlMode.Position, pos);
        }
    }

    public double getPosition(){
        return turretFx.getSelectedSensorPosition();
    }

    @Override
    public void periodic() {
        if(!THE_TURRET_ZERO && dStation.isEnabled()){
            resetTurret();
        }
        if(!controlled){
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
            if(THE_TURRET_ZERO && dStation.isOperatorControl()){
                setPosition(toTurretCounts(52));
            }
        }
    }

    public double getDegreePosition(){
        return toTurretDegrees(getPosition());
    }

    public double toTurretDegrees(double counts){
        return counts * (1.0 / 50.560) * (360.0 / 2048.0);
    }
    public double toTurretCounts(double degrees){
        return degrees * (50.560) * (2048.0 / 360.0);
    }


    public void resetTurret(){
        if(turretFx.getSupplyCurrent() < 1.5){
            turretFx.set(ControlMode.PercentOutput, -0.1);
        } else{
            turretFx.set(ControlMode.PercentOutput, 0);
            turretFx.setSelectedSensorPosition(0);
            turretFx.configForwardSoftLimitEnable(true);
            turretFx.configReverseSoftLimitEnable(true);
            THE_TURRET_ZERO = true;
        }
    }
}
