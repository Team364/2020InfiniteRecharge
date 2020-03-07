package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWM;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.team364.frc2020.RobotMap.*;

public class Intake extends SubsystemBase {
	public PWM intake;
	public DoubleSolenoid leftPiston;


	public Intake(){
		intake = new PWM(INTAKE);
		leftPiston = new DoubleSolenoid(0, 1);
	}

	public void setPower(double power){
		intake.setSpeed(power);
	}

	public void setPiston(boolean activate){
		leftPiston.set(Boolean.class.cast(activate) ? kReverse : kForward);
	}

}
