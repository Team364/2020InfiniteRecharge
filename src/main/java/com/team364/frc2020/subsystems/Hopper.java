package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import static com.team364.frc2020.RobotMap.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
	public TalonFX floorRollers;
	public TalonSRX sideRollers;
	public DigitalInput infrared;

	public Hopper(){
		floorRollers = new TalonFX(FLOORROLLERS);
		sideRollers = new TalonSRX(SIDEROLLERS);
		infrared = new DigitalInput(0);
		floorRollers.setInverted(true);
	}

	public void setPower(double motorPower){
		floorRollers.set(ControlMode.PercentOutput, motorPower);
		sideRollers.set(ControlMode.PercentOutput, motorPower);
	}

	public boolean getInfrared(){
		return !infrared.get();
	}

	@Override
	public void periodic() {
		SmartDashboard.putBoolean("infrared", infrared.get());
	}
}
