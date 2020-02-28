package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.Intake;

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
		addRequirements(s_Intake);
	}

    @Override
	public void execute() {
		s_Intake.setPiston(activate);
	}

	@Override
	public void end(boolean interrupted){
		s_Intake.setPiston(null);
	}


}
