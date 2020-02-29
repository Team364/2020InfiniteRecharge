package com.team364.frc2020.commands;

import com.team364.frc2020.Robot;
import com.team364.frc2020.subsystems.Intake;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeControl extends CommandBase {
	private double power;
	private Intake s_Intake;


	public IntakeControl(double power, Intake s_Intake){
		this.power = power;
		this.s_Intake = s_Intake;
	}
	
    @Override
	public void initialize() {
		Robot.IntakeControl.setValue(true);
		addRequirements(s_Intake);
	}

    @Override
	public void execute() {
		s_Intake.setPower(power);
	}

	@Override
	public void end(boolean interrupted){
		Robot.IntakeControl.setValue(false);
		s_Intake.setPower(0);
	}


}
