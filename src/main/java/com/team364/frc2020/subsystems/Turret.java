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
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

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

    public SupplyCurrentLimitConfiguration turretSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

    public Turret() {
        register();
        controlled = false;
        turretFx = new TalonFX(TURRET);

        // Configure turret Motor
        turretFx.configFactoryDefault();
        turretFx.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 20);
        turretFx.selectProfileSlot(SLOTIDX, PIDLoopIdx);
        // turretFx.setSensorPhase(invertSensorPhase);
        turretFx.config_kP(0, 0.1);
        turretFx.config_kI(0, 0);
        turretFx.config_kD(0, 0);
        turretFx.setNeutralMode(NeutralMode.Coast);
        turretFx.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 10);

        // Setup Current Limiting
        turretFx.configSupplyCurrentLimit(turretSupplyLimit, 20);
        turretFx.setInverted(true);
        turretFx.configReverseSoftLimitThreshold(TURRETMINSOFT);
        turretFx.configForwardSoftLimitThreshold(TURRETMAXSOFT);
    }

    public void setPosition(double pos){
        double additiveSoft = 1000;
        if(pos < TURRETMINSOFT + additiveSoft){
            turretFx.set(ControlMode.Position, TURRETMINSOFT + additiveSoft);
        } else if(pos > TURRETMAXSOFT - additiveSoft){
            turretFx.set(ControlMode.Position, TURRETMAXSOFT - additiveSoft);
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
