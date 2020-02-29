package com.team364.frc2020.commands;

import com.team364.frc2020.Robot;
import com.team364.frc2020.subsystems.Intake;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DeployControl extends CommandBase {
	private boolean activate;
	private Intake s_Intake;


	public DeployControl(Boolean activate, Intake s_Intake){
		this.activate = activate;
		this.s_Intake = s_Intake;
	}
	
    @Override
	public void initialize() {
		Robot.DeployControl.setValue(true);
		addRequirements(s_Intake);
	}

    @Override
	public void execute() {
		s_Intake.setPiston(activate);
		//end(true);
	}

	@Override
	public void end(boolean interrupted){
		Robot.DeployControl.setValue(false);
		s_Intake.setPiston(null);
	}


}
