package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team364.frc2020.Robot;

import static com.team364.frc2020.RobotMap.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
	public PWM floorRollers;
	public TalonSRX sideRollers;
	public DigitalInput infrared;

    private SupplyCurrentLimitConfiguration sideSupplyLimit = new SupplyCurrentLimitConfiguration(HOPPERENABLECURRENTLIMIT, HOPPERCONTINUOUSCURRENTLIMIT, HOPPERPEAKCURRENTDURATION, HOPPERPEAKCURRENTDURATION);

	public Hopper(){
		floorRollers = new PWM(FLOORROLLERS);
		sideRollers = new TalonSRX(SIDEROLLERS);
		infrared = new DigitalInput(HOPPERIR);
		sideRollers.configFactoryDefault();
		sideRollers.configSupplyCurrentLimit(sideSupplyLimit, 20);
		sideRollers.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);

	}

	public void setPower(double power){
		floorRollers.setSpeed(power);
		sideRollers.set(ControlMode.PercentOutput, power);
	}

	public boolean getInfrared(){
		return !infrared.get();
	}

	@Override
	public void periodic() {
		//SmartDashboard.putBoolean("infrared", getInfrared());
		Robot.HoodReady.setBoolean(getInfrared());
	}
}
