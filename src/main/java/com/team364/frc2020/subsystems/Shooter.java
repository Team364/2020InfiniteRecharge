package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.RobotContainer.THE_SWITCH;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private int shootingLoops;
    private int ferryLoops;
    private int rampUpLoops;
    private int rampDownLoops;
    public double motorPower;
    public TalonFX mFlyWheelMotor;
    public TalonFX mShooterSlave;
    private double ShooterInput;

    public Shooter() {
        mFlyWheelMotor = new TalonFX(12);
        mShooterSlave = new TalonFX(13);
        mShooterSlave.follow(mFlyWheelMotor);
        mShooterSlave.setInverted(false);
        ShooterInput = 0;
    }
  
    public void setFlyWheelVel(double velocity) {
        ShooterInput = velocity;
        mFlyWheelMotor.set(ControlMode.Velocity, velocity);
    }
    public double getFlyWheelVel() {
        return mFlyWheelMotor.getSelectedSensorVelocity();
    }

    @Override
    public void periodic() {
        ShooterStates();
        pidShooterStates();
    }
    private void stateChange(double[] pidControl) {
        mFlyWheelMotor.config_kP(0, pidControl[0]);
        mFlyWheelMotor.config_kI(0, pidControl[1]);
        mFlyWheelMotor.config_kD(0, pidControl[2]);
    }
    private void pidShooterStates(){
        switch(shooterState) {
            case SHOOTING:
                ferryLoops = 0;
                rampUpLoops = 0;
                rampDownLoops = 0;
                stateChange(SHOOTING_PID);
                setFlyWheelVel();
                shootingLoops = 1;
            break;
            case FERRY:
                shootingLoops = 0;
                rampUpLoops = 0;
                rampDownLoops = 0;
                stateChange(FERRY_PID);
                setFlyWheelVel();
                ferryLoops = 1;
            break;
            case RAMP_UP:
                shootingLoops = 0;
                ferryLoops = 0;
                rampDownLoops = 0;
                stateChange(RAMP_UP_PID);
                setFlyWheelVel();
                rampUpLoops = 1;
            break;
            case RAMP_DOWN:
                shootingLoops = 0;
                ferryLoops = 0;
                rampUpLoops = 0;
                stateChange(RAMP_DOWN_PID);
                setFlyWheelVel();
                rampDownLoops = 1;
            break;
        }
    }

    public void stateChange(double[] pidControl) {
        if(shootingLoops == 0 || ferryLoops == 0 || rampUpLoops == 0 || rampDownLoops == 0) {
            mFlyWheelMotor.config_kP(0, pidControl[0]);
            mFlyWheelMotor.config_kI(0, pidControl[1]);
            mFlyWheelMotor.config_kD(0, pidControl[2]);
        }
    }

    private void ShooterStates(){
     if (THE_SWITCH) {
        if (getFlyWheelVel() >= ShooterInput && shooterState != ShooterStates.SHOOTING) shooterState = ShooterStates.SHOOTING;
       if(shooterState == ShooterStates.FERRY ) shooterState = ShooterStates.RAMP_UP;
      } else if (!THE_SWITCH ) {
        if(shooterState == ShooterStates.SHOOTING) shooterState = ShooterStates.RAMP_DOWN;
        if(shooterState == ShooterStates.RAMP_DOWN && getFlyWheelVel() <= FERRYSPEED) shooterState = ShooterStates.FERRY;
      }
    }
} 

