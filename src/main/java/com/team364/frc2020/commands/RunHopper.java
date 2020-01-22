package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.Hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunHopper extends CommandBase {
	private Hopper s_Hopper;

	public RunHopper(Hopper s_Hopper){
		this.s_Hopper = s_Hopper;
    }
    @Override
	public void initialize() {
		addRequirements(s_Hopper);
	}
    @Override
	public void execute() {
		s_Hopper.MoveHopper(0.5);
	}
}
