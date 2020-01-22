package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
	public TalonFX mHopperMotor;

	public Hopper(){
		mHopperMotor = new TalonFX(1);
}
	public void MoveHopper(double motorPower){
		mHopperMotor.set(ControlMode.PercentOutput, motorPower);
	}
}
