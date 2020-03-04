package com.team364.frc2020.commands;

import com.team364.frc2020.Robot;
import com.team364.frc2020.subsystems.Hang;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class HangControl extends CommandBase {
	private double power;
	private Hang s_Hang;


	public HangControl(double power, Hang s_Hang){
		this.power = power;
		this.s_Hang = s_Hang;
	}
	
    @Override
	public void initialize() {
		addRequirements(s_Hang);
	}

    @Override
	public void execute() {
		s_Hang.setPower(power);
	}

	@Override
	public void end(boolean interrupted){
		s_Hang.setPower(0);
	}


}