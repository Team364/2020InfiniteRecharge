package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static com.team364.frc2020.subsystems.Vision.TARGET;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.RobotContainer.*;

public class Shooter extends SubsystemBase {
    public double ShooterTarget;
    public TalonFX mFlyWheelMotor;
    public TalonFX mImmaSlave;
    public Shooter() {
        mImmaSlave = new TalonFX(1);
        mFlyWheelMotor = new TalonFX(12);
        mImmaSlave.follow(mFlyWheelMotor);
        ShooterTarget = 0;
    }

    public void setFlyWheelVel(double velocity) {

        mFlyWheelMotor.set(ControlMode.Velocity, velocity);
    }
    public double getFlyWheelVel() {
        return mFlyWheelMotor.getSelectedSensorVelocity();
    }

    @Override
    public void periodic() {
        if(THE_SWITCH){
            ShooterTarget = TARGET.get("Shooter");
            setFlyWheelVel(ShooterTarget);
        }

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
} 