package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.Hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class HopperControl extends CommandBase {
	private double power;
	private Hopper s_Hopper;

	public HopperControl(double power, Hopper s_Hopper){
		this.power = power;
		this.s_Hopper = s_Hopper;
	}
	
    @Override
	public void initialize() {
		addRequirements(s_Hopper);
	}

    @Override
	public void execute() {
		s_Hopper.setPower(power);
	}

	@Override
	public void end(boolean interrupted){
		s_Hopper.setPower(0);
	}


}
