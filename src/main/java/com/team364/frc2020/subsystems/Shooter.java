package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    public TalonFX mFlyWheelMotor;
    public TalonFX mSlaveFlyWheelMotor;    

    private ShuffleboardTab shooterPID = Shuffleboard.getTab("Configuration");
        private NetworkTableEntry shooterkP;
        private NetworkTableEntry shooterkI;
        private NetworkTableEntry shooterkD;
        private NetworkTableEntry shooterkF;

    public Shooter() {
        mFlyWheelMotor = new TalonFX(SHOOTER);   
        mSlaveFlyWheelMotor = new TalonFX(SHOOTERSLAVE);
    
        //Configure Shooter Motors
        mFlyWheelMotor.configFactoryDefault();
        mSlaveFlyWheelMotor.configFactoryDefault();
        TalonFXConfiguration shooterFxConfiguration = new TalonFXConfiguration();

        //Configure Shooter PIDF
        shooterFxConfiguration.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
        shooterFxConfiguration.slot0.kP = SHOOTERKP;
        shooterFxConfiguration.slot0.kI = SHOOTERKI;
        shooterFxConfiguration.slot0.kD = SHOOTERKD;
        shooterFxConfiguration.slot0.kF = SHOOTERKF;

        //Configure Current Limiting
        SupplyCurrentLimitConfiguration shooterSupplyLimit = new SupplyCurrentLimitConfiguration(SHOOTERENABLECURRENTLIMIT, SHOOTERCONTINUOUSCURRENTLIMIT, SHOOTERPEAKCURRENTDURATION, SHOOTERPEAKCURRENTDURATION);
        shooterFxConfiguration.supplyCurrLimit = shooterSupplyLimit;
        
        //Write Final Settings to Shooter Motors
        mFlyWheelMotor.configAllSettings(shooterFxConfiguration);
        mSlaveFlyWheelMotor.configAllSettings(shooterFxConfiguration);

        mFlyWheelMotor.setNeutralMode(SHOOTERNEUTRALMODE);
        mSlaveFlyWheelMotor.setNeutralMode(SHOOTERNEUTRALMODE);
        
        mFlyWheelMotor.setInverted(SHOOTERINVERT);
        mSlaveFlyWheelMotor.follow(mFlyWheelMotor);
        mSlaveFlyWheelMotor.setInverted(InvertType.OpposeMaster);

        mFlyWheelMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
        mSlaveFlyWheelMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);



        shooterkP = shooterPID.add("Shooter kP", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(1, 4).getEntry();
        shooterkI = shooterPID.add("Shooter kI", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(2, 4).getEntry();
        shooterkD = shooterPID.add("Shooter kD", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(3, 4).getEntry();
        shooterkF = shooterPID.add("Shooter kF", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(4, 4).getEntry();

    }

    public void setFlyWheelVel(double velocity) {
        mFlyWheelMotor.set(ControlMode.Velocity, toSensorCounts(velocity));
    }

    public double getFlyWheelVel() {
        return mFlyWheelMotor.getSelectedSensorVelocity();
    }

    @Override
    public void periodic() {
        if(configState == ConfigStates.TARGET){
            mFlyWheelMotor.config_kP(0, shooterkP.getDouble(SHOOTERKP));
            mFlyWheelMotor.config_kI(0, shooterkI.getDouble(SHOOTERKI));
            mFlyWheelMotor.config_kD(0, shooterkD.getDouble(SHOOTERKD));
            mFlyWheelMotor.config_kF(0, shooterkF.getDouble(SHOOTERKF));
        }
        SmartDashboard.putNumber("Shooter velocity", fromSensorCounts(getFlyWheelVel()));

    }

    public void setFlyWheelOff(){
        mFlyWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public double toSensorCounts(double shooterRpm){
        double sensorCounts = shooterRpm * (18.0 / 40.0) * (2048.0 / 600.0);
        return sensorCounts;
    }

    public double fromSensorCounts(double sensorCounts){
        double shooterRpm = sensorCounts / (18.0 / 40.0) / (2048.0 / 600.0);
        return shooterRpm;
    }
} 