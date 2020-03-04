package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.team364.frc2020.RobotMap.*;

public class Intake extends SubsystemBase {
	public TalonFX intake;
	public DoubleSolenoid leftPiston;
	public DoubleSolenoid rightPiston;

	private SupplyCurrentLimitConfiguration intakeSupplyCurrent = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);

	public Intake(){
		intake = new TalonFX(INTAKE);
		intake.configFactoryDefault();
		intake.configSupplyCurrentLimit(intakeSupplyCurrent, 20);
		leftPiston = new DoubleSolenoid(0, 1);
		rightPiston = new DoubleSolenoid(2, 3);
	}

	public void setPower(double power){
		intake.set(ControlMode.PercentOutput, power);
	}

	public void setPiston(Object activate){
		if(activate != null){ 
			leftPiston.set(Boolean.class.cast(activate) ? kForward : kReverse);
			rightPiston.set(Boolean.class.cast(activate) ? kForward : kReverse);
		}
	}

}
