package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import static com.team364.frc2020.RobotMap.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
	public TalonFX mainRollers;
	public TalonSRX leftRollers;
	public TalonSRX rightRollers;
	public DigitalInput infrared;

	public Hopper(){
		mainRollers = new TalonFX(MAINROLLERS);
		leftRollers = new TalonSRX(LEFTROLLERS);
		rightRollers = new TalonSRX(RIGHTROLLERS);
		infrared = new DigitalInput(0);
	}

	public void setPower(double motorPower){
		mainRollers.set(ControlMode.PercentOutput, motorPower);
		leftRollers.set(ControlMode.PercentOutput, motorPower);
		rightRollers.set(ControlMode.PercentOutput, -motorPower);
	}

	public boolean getInfrared(){
		return !infrared.get();
	}

	@Override
	public void periodic() {
		SmartDashboard.putBoolean("infrared", infrared.get());
	}
}
