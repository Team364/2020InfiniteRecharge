package com.team364.frc2020.commands.autos;

import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToHeadingDistance extends CommandBase {
    private double distance;
    private double direction;
    private double heading;
    private Swerve s_Swerve;
    private PIDController distanceControl;
    private PIDController snapController;


	public DriveToHeadingDistance(double distance, double direction, double heading, Swerve s_Swerve){
        this.distance = distance;
        this.direction = direction;
        this.heading = heading;
        this.s_Swerve = s_Swerve;
        distanceControl = new PIDController(1, 0, 0);
        snapController = new PIDController(0.01, 0, 0);
        snapController.setTolerance(5);
	}
	
    @Override
	public void initialize() {
		addRequirements(s_Swerve);
	}

    @Override
	public void execute() {
        double speed = distanceControl.calculate( s_Swerve.getDriveDistance(), distance);
        snapController.setSetpoint(heading);
        double rotation = snapController.calculate(s_Swerve.getYaw());
        s_Swerve.holomonicAutoDrive(direction, speed, rotation);
        s_Swerve.updateKinematics();
	}
    
    @Override
    public boolean isFinished() {
        return (Math.abs(s_Swerve.getDriveDistance() - distance) < 1) && (Math.abs(s_Swerve.getYaw() - heading) < 10);
    }

	@Override
	public void end(boolean interrupted){
		
	}


}
