package com.team364.frc2020.commands.autos;

import com.team364.frc2020.Robot;
import com.team364.frc2020.commands.OpenLoopSwerve;
import com.team364.frc2020.misc.math.Vector2;
import com.team364.frc2020.subsystems.Intake;
import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class DriveToDistance extends InstantCommand {
    private double distance;
    private double direction;
    private Swerve s_Swerve;
    private PIDController distanceControl = new PIDController(1, 0, 0);

	public DriveToDistance(double distance, double direction, Swerve s_Swerve){
        this.distance = distance;
        this.direction = direction;
        this.s_Swerve = s_Swerve;
	}
	
    @Override
	public void initialize() {
		addRequirements(s_Swerve);
	}

    @Override
	public void execute() {
        double speed = distanceControl.calculate( s_Swerve.getDriveDistance(), distance);
        s_Swerve.straightAutoDrive(direction, speed);
        s_Swerve.updateKinematics();
	}

	@Override
	public void end(boolean interrupted){
		
	}


}
