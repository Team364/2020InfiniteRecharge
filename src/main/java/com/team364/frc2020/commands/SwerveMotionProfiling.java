package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import static com.team364.frc2020.RobotMap.*;

import java.util.List;

public class SwerveMotionProfiling extends SwerveControllerCommand {
    
    private Timer timer;
    private Trajectory trajectory;
            // Create config for trajectory
    public static TrajectoryConfig config =
    new TrajectoryConfig(SWERVEMAXSPEED, SWERVEMAXSPEED / 100)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(Swerve.getInstance().getKinematics());


    // An example trajectory to follow.  All units in meters.
    public static Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(20, 0)), // Pass through these two interior waypoints, making an 's' curve path
            //List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(0, 0, new Rotation2d(45)), config);
    public SwerveMotionProfiling(Trajectory trajectory) {

        super(exampleTrajectory,
            Swerve.getInstance()::getPose,
            Swerve.getInstance().getKinematics(), 
            new PIDController(10, 0, 0), 
            new PIDController(10, 0, 0), 
            new ProfiledPIDController(10, 0, 0,
            new TrapezoidProfile.Constraints(SWERVEMAX_ANGLEVELOCITY, SWERVEMAX_ANGLEACCELERATION)),
            Swerve.getInstance()::setProfilingStates,
            Swerve.getInstance()
        );
        this.trajectory = trajectory;
        timer = new Timer();
    }

    public void initialize() {
        super.initialize();
        timer.start();
        //Pose2d initialPose = trajectory.getInitialPose();
        //Rotation2d currentRot = Rotation2d.fromDegrees(Swerve.getInstance().getYaw());
        //Swerve.getInstance().getOdometry().resetPosition(initialPose, currentRot);
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
      Swerve.getInstance().stop();
    }
  
    @Override
    public boolean isFinished() {
      return super.isFinished();
    }
}