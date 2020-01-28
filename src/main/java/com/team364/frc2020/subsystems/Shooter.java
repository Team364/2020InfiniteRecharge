package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;

public class Shooter extends SubsystemBase {
    public double motorPower;
    public TalonFX mFlyWheelMotor;
    public Shooter() {
        mFlyWheelMotor = new TalonFX(2);
    }

    public void setFlyWheelVel() {

        mFlyWheelMotor.set(ControlMode.Velocity, motorPower);
    }
    public double getFlyWheelVel() {
        return mFlyWheelMotor.getSelectedSensorVelocity();
    }

    @Override
    public void periodic() {
        switch(shooterState) {
            case SHOOTING:
                stateChange(SHOOTING_PID);
                setFlyWheelVel();
            break;
            case FERRY:
                stateChange(FERRY_PID);
                setFlyWheelVel();
            break;
            case RAMP_UP:
                stateChange(RAMP_UP_PID);
                setFlyWheelVel();
            break;
            case RAMP_DOWN:
                stateChange(RAMP_DOWN_PID);
                setFlyWheelVel();
            break;
        }
    }
    public void stateChange(double[] pidControl) {
        mFlyWheelMotor.config_kP(0, pidControl[0]);
        mFlyWheelMotor.config_kI(0, pidControl[1]);
        mFlyWheelMotor.config_kD(0, pidControl[2]);
    }
} 