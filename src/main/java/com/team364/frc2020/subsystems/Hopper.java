package com.team364.frc2020.subsystems;



public class hopper extends subsystem{
	public TalonFX = HopperMotor;
	Public hopper(){
		mHopperMotor = new TalonFX(1);
}
	public void MoveHopper(double motorPower){
		mHopperMotor.set(ControlMode.PercentOutPut, motorPower);
	}
}
