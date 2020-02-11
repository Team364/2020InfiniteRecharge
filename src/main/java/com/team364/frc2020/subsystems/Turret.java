/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;
import static com.team364.frc2020.RobotMap.TURRET;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.*;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Turret implements Subsystem {
    public TalonFX turretFx;
    private CANCoderConfiguration turretCANConfig = new CANCoderConfiguration();
    private CANCoder turretCAN = new CANCoder(11);

    public SupplyCurrentLimitConfiguration turretSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

    public Turret() {
        register();
        turretFx = new TalonFX(TURRET);
        turretCANConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        turretCANConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;

        // Configure CANCoder
        turretCAN.configAllSettings(turretCANConfig, 30);

        // Configure turret Motor
        turretFx.configFactoryDefault();
        turretFx.configRemoteFeedbackFilter(turretCAN, 0, 20);
        turretFx.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, 0, 20);
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

    }

    public void setPosition(double pos){
        turretFx.set(ControlMode.Position, pos);
    }
    public double getPosition(){
        return turretFx.getSelectedSensorPosition();
    }

    @Override
    public void periodic() {

    }




}
