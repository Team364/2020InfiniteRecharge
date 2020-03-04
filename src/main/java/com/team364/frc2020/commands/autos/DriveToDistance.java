package com.team364.frc2020.commands.autos;

import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToDistance extends CommandBase {
    private double distance;
    private double direction;
    private double startGyro;
    private Swerve s_Swerve;
    private PIDController distanceControl;
    private PIDController snapController;


	public DriveToDistance(double distance, double direction, Swerve s_Swerve){
        this.distance = distance;
        this.direction = direction;
        this.s_Swerve = s_Swerve;
        distanceControl = new PIDController(1, 0, 0);
        snapController = new PIDController(0.01, 0, 0);
        snapController.setTolerance(5);
	}
	
    @Override
	public void initialize() {
        addRequirements(s_Swerve);
        startGyro = s_Swerve.getYaw();
	}

    @Override
	public void execute() {
        double speed = distanceControl.calculate( s_Swerve.getDriveDistance(), distance);
        snapController.setSetpoint(startGyro);
        double rotation = snapController.calculate(s_Swerve.getYaw());
        s_Swerve.holomonicAutoDrive(direction, speed, rotation);
        s_Swerve.updateKinematics();
    }
    
    @Override
    public boolean isFinished() {
        return Math.abs(s_Swerve.getDriveDistance() - distance) < 1;
    }

	@Override
	public void end(boolean interrupted){
		
	}


}
