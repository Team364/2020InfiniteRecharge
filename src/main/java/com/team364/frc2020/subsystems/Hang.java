package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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


    private SupplyCurrentLimitConfiguration hangSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

	public Hang(){
        hangFx = new TalonFX(HANG);
        hangFx.configFactoryDefault();
		hangFx.configSupplyCurrentLimit(hangSupplyLimit, 20);
		piston = new DoubleSolenoid(6, 7);
		hangFx.setSelectedSensorPosition(0);
		hangFx.configForwardSoftLimitThreshold(0);
		//hangFx.configReverseSoftLimitThreshold(100);
		hangFx.configForwardSoftLimitEnable(true);
		hangFx.setNeutralMode(NeutralMode.Brake);
	}

	public void setPower(double motorPower){
		SmartDashboard.putBoolean("islocked", isLocked);
		if(!isLocked){
			hangFx.set(ControlMode.PercentOutput, -motorPower);
		}
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
	}

}
