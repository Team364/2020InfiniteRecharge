package com.team364.frc2020.commands;

import com.team364.frc2020.Robot;
import com.team364.frc2020.subsystems.Hopper;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IndexBall extends CommandBase {
	private Hopper s_Hopper;


	public IndexBall(Hopper s_Hopper){
		this.s_Hopper = s_Hopper;
	}
	
    @Override
	public void initialize() {
		Robot.IndexBall.setValue(true);
		addRequirements(s_Hopper);
	}

    @Override
	public void execute() {
		s_Hopper.setPower(1);
	}

	@Override
    public boolean isFinished() {
        return s_Hopper.getInfrared();
    }
 
	@Override
	public void end(boolean interrupted){
		Robot.IndexBall.setValue(false);
		s_Hopper.setPower(0);
	}


}
