package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.RobotContainer.THE_SWITCH;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private double ShooterInput;
    public TalonFX mFlyWheelMotor;
    public TalonFX mImmaSlave;
    public Shooter() {
        mFlyWheelMotor = new TalonFX(12);
        mImmaSlave = new TalonFX(1);

        mImmaSlave.follow(mFlyWheelMotor);
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
                stateChange(SHOOTING_PID);
            break;
            case FERRY:
                stateChange(FERRY_PID);
            break;
            case RAMP_UP:
                stateChange(RAMP_UP_PID);
            break;
            case RAMP_DOWN:
                stateChange(RAMP_DOWN_PID);
            break;
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