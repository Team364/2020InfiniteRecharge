package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team364.frc2020.Robot;

import static com.team364.frc2020.RobotMap.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
	public TalonFX floorRollers;
	public TalonSRX sideRollers;
	public DigitalInput infrared;

	private SupplyCurrentLimitConfiguration floorSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);
    private SupplyCurrentLimitConfiguration sideSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

	public Hopper(){
		floorRollers = new TalonFX(FLOORROLLERS);
		sideRollers = new TalonSRX(SIDEROLLERS);
		infrared = new DigitalInput(0);
		floorRollers.setInverted(true);
		floorRollers.configFactoryDefault();
		sideRollers.configFactoryDefault();
		floorRollers.configSupplyCurrentLimit(floorSupplyLimit, 20);
        sideRollers.configSupplyCurrentLimit(sideSupplyLimit, 20);

	}

	public void setPower(double power){
		floorRollers.set(ControlMode.PercentOutput, power);
		sideRollers.set(ControlMode.PercentOutput, power);
	}

	public boolean getInfrared(){
		return !infrared.get();
	}

	@Override
	public void periodic() {
		SmartDashboard.putBoolean("infrared", getInfrared());
		Robot.HoodReady.setBoolean(getInfrared());
	}
}
