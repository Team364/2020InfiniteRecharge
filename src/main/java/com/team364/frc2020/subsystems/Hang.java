package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.team364.frc2020.Robot;

import static com.team364.frc2020.RobotMap.*;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hang extends SubsystemBase {
	public TalonFX hangFx;
	public DoubleSolenoid piston;
	public boolean isLocked = false;
	public boolean used = false;

	public Hang(){
		hangFx = new TalonFX(HANG);
		
		//Configure Hang Motor
		hangFx.configFactoryDefault();
		TalonFXConfiguration hangFxConfiguration = new TalonFXConfiguration();

		//Configure Hang Min and Max Limits
		hangFxConfiguration.reverseSoftLimitThreshold = HANGMINSOFT;
		hangFxConfiguration.reverseSoftLimitEnable = true;
		hangFxConfiguration.forwardSoftLimitThreshold = HANGMAXSOFT;
		hangFxConfiguration.forwardSoftLimitEnable = true;

		//Setup Hang Current Limiting
		SupplyCurrentLimitConfiguration hangSupplyLimit = new SupplyCurrentLimitConfiguration(HANGENABLECURRENTLIMIT, HANGCONTINUOUSCURRENTLIMIT, HANGPEAKCURRENTDURATION, HANGPEAKCURRENTDURATION);
		hangFxConfiguration.supplyCurrLimit = hangSupplyLimit;

		//Writing Settings to Hang Motor
		hangFx.configAllSettings(hangFxConfiguration);
		hangFx.setInverted(HANGINVERT);
		hangFx.setNeutralMode(HANGNEUTRALMODE);

		hangFx.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);

		piston = new DoubleSolenoid(6, 7);
	}

	public void setPower(double motorPower){
		SmartDashboard.putBoolean("islocked", isLocked);
		hangFx.set(ControlMode.PercentOutput, motorPower);
		SmartDashboard.putNumber("hanging", motorPower);
	}
	
	public void init(){
		piston.set(kForward);
	}

	public void setPiston(){
		isLocked = !isLocked;
		piston.set(isLocked ? kForward : kReverse);
		SmartDashboard.putBoolean("Piston", isLocked);
		Robot.Piston.setBoolean(isLocked);
	}

	@Override
	public void periodic() {
		if(!used){
			init();
			used = true;
			isLocked = true;
		}
		SmartDashboard.putNumber("hang counts", hangFx.getSelectedSensorPosition());
	}

}
