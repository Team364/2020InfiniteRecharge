package com.team364.frc2020.commands;

public class runHopper extends CommandBase {
	private Hopper s_Hopper
	public runHopper(Hopper s_Hopper){
		this.s_Hopper = s_Hopper;
    }
    @Override
	public void initialize() {
		addRequirements(s_Hopper);
	}
    @Override
	public void execute {
		s_Hopper.MoveHopper(0.5);
	}
}
