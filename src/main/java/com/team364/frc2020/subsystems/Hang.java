package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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


    private SupplyCurrentLimitConfiguration hangSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

	public Hang(){
        hangFx = new TalonFX(HANG);
        hangFx.configFactoryDefault();
		hangFx.configSupplyCurrentLimit(hangSupplyLimit, 20);
		piston = new DoubleSolenoid(4, 5);
	}

	public void setPower(double motorPower){
		hangFx.set(ControlMode.PercentOutput, motorPower);
		SmartDashboard.putNumber("hanging", motorPower);
	}

	public void setPiston(){
		piston.set(isLocked ? kForward : kReverse);
		isLocked = !isLocked;
	}

}
