package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import static com.team364.frc2020.RobotMap.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
	public TalonFX intake;
	//public DoubleSolenoid piston;

	public Intake(){
		intake = new TalonFX(17);
		//piston = new DoubleSolenoid(1, 0);

	}

	public void setPower(double motorPower){
		intake.set(ControlMode.PercentOutput, motorPower);
	}

	public void setPiston(Object activate){
		if(activate == null){
			//piston.set(Value.kOff);
		}else {
			//piston.set(Boolean.class.cast(activate) ? Value.kForward : Value.kReverse);
		}
	}

}
