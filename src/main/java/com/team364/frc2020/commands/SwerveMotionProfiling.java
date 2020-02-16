package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import static com.team364.frc2020.RobotMap.*;

public class SwerveMotionProfiling extends SwerveControllerCommand {
    
    private Timer timer;
    private Trajectory trajectory;
        
    public SwerveMotionProfiling(Trajectory trajectory) {
        super(trajectory,
            Swerve.getInstance()::getPose,
            Swerve.getInstance().getKinematics(), 
            new PIDController(1, 0, 0), 
            new PIDController(1, 0, 0), 
            new ProfiledPIDController(1, 0, 0,
            new TrapezoidProfile.Constraints(SWERVEMAX_ANGLEVELOCITY, SWERVEMAX_ANGLEACCELERATION)),
            Swerve.getInstance()::setProfilingStates,
            Swerve.getInstance()
        );
        this.trajectory = trajectory;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        super.initialize();
        timer.start();
        Pose2d initialPose = trajectory.getInitialPose();
        Rotation2d currentRot = Rotation2d.fromDegrees(Swerve.getInstance().getYaw());
        Swerve.getInstance().getOdometry().resetPosition(initialPose, currentRot);
    }   
    
    @Override
    public void execute() {
        super.execute();
        
        double deltaT = timer.get();
        SmartDashboard.putNumber("Time", timer.get());
        Trajectory.State state = trajectory.sample(deltaT);
        SmartDashboard.putString("state", state.toString());

        Translation2d desiredTranslation = state.poseMeters.getTranslation();
        double desiredRotation = state.poseMeters.getRotation().getDegrees();

        Translation2d currentTranslation = Swerve.getInstance().getPose().getTranslation();
        double currentRotation = Swerve.getInstance().getPose().getRotation().getDegrees();
        SmartDashboard.putNumber("desiredX", desiredTranslation.getX());
        SmartDashboard.putNumber("CurrentX", currentTranslation.getY());
        SmartDashboard.putNumber("Trajectory X Error", desiredTranslation.getX() - currentTranslation.getX());
        SmartDashboard.putNumber("Trajectory Y Error", desiredTranslation.getY() - currentTranslation.getY());
        SmartDashboard.putNumber("Trajectory Angle Error", desiredRotation - currentRotation);

        SmartDashboard.putNumber("Trajectory X", desiredTranslation.getX());
        SmartDashboard.putNumber("Trajectory Y", desiredTranslation.getY());
        SmartDashboard.putNumber("Trajectory Angle", desiredRotation);
    }

    @Override
    public void end(boolean interrupted) {
      timer.stop();
      timer.reset();
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }
}