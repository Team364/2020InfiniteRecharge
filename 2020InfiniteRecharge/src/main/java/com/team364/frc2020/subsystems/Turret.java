/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static com.team364.frc2020.States.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
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

    public SupplyCurrentLimitConfiguration turretSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

    public Turret() {
        register();
        turretFx = new TalonFX(TURRET);


        // Configure turret Motor
        turretFx.configFactoryDefault();
        turretFx.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 20);
        turretFx.selectProfileSlot(0, 20);
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

    public void gyroTarget() {
        //use gyro for tracking
    }

    public void visionTarget() {
        //use vision for tracking
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        Subsystem.super.periodic();
        if (targetState == TargetStates.GYRO) {
        gyroTarget();
      } else if(targetState == TargetStates.VISION) {
          visionTarget();
      }
  }




}
