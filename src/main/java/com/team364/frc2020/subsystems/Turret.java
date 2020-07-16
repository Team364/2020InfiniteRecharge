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
import com.team364.frc2020.States.TurretStates;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

import com.team364.frc2020.Robot;
import static com.team364.frc2020.Robot.*;
import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.Conversions.*;

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
       
    private Vision s_Vision;
    private Swerve s_Swerve;
    private char letterSide = 'L';
    private double minSoft;
    private double maxSoft;
    private double target;
    

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

        minSoft = toTurretDegrees(TURRETMINSOFT);
        maxSoft = toTurretDegrees(TURRETMAXSOFT);

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
        /*if(dStation.isEnabled()){
            switch(turretState){
                case NOT_CALIBRATED:
                    limelightOff();
                    if(resetTurret()){
                        turretState = TurretStates.NO_TRACK;
                    }
                break;
                case GYRO:
                    limelightOn();
                    //TODO: could cause error because s_Vision is never initialized, only declared
                    if(s_Vision.hasTarget()){
                        turretState = TurretStates.VISION;
                    }
                    double switchTarget = (letterSide == 'L') ? LEFTTURRETRANGE : RIGHTTURRETRANGE;
                    target = to360Boundaries(52 + switchTarget + to180Boundaries(s_Swerve.getYaw()));
                    if(withinDeadband(getDegreePosition() - target, 8)){
                        letterSide = (letterSide == 'L') ? 'R' : 'L';
                    }
                break;
                case VISION:
                    target = to360Boundaries(getDegreePosition() + s_Vision.limeX());
                    if(!s_Vision.hasTarget()){
                        turretState = TurretStates.GYRO;
                    }
                    if((target <= 350 && getDegreePosition() < 45 && target > 180) || (target > 0 && getDegreePosition() > 285 && target < 180)){
                        turretState = TurretStates.FLIPPING;
                    }
                break;
                case FLIPPING:
                    if(s_Vision.hasTarget()){
                        turretState = TurretStates.VISION;
                    }
                break;
                case NO_TRACK:
                    limelightOff();
                    setPosition(toTurretCounts(52));
                break;
            }
            if(target < minSoft && target > 350){
                target = minSoft;
            }else if(target <= 350 && getDegreePosition() < 45 && target > 180){
                target = maxSoft;
            }
            setPosition(toTurretCounts(target));
            boolean inRange;
            if(Math.abs(getDegreePosition() - target) < 30){
                inRange = true;
            }else{
                inRange = false;
            }
            SmartDashboard.putBoolean("Turret range", inRange);
            Robot.TurretReady.setBoolean(inRange);        
            SmartDashboard.putNumber("Turret error", Math.abs(getDegreePosition() - target));


        }
        else{
            limelightOff();
        }*/
    }

    public void limelightOff(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    }
    public void limelightOn(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
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


    public boolean resetTurret(){
        if(turretFx.getSupplyCurrent() < 1.5){
            turretFx.set(ControlMode.PercentOutput, -0.1);
            return false;
        } else{
            turretFx.set(ControlMode.PercentOutput, 0);
            turretFx.setSelectedSensorPosition(0);
            turretFx.configForwardSoftLimitEnable(true);
            turretFx.configReverseSoftLimitEnable(true);
            return true;
        }
    }
}
