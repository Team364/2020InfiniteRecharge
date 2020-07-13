/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public RobotContainer m_robotContainer;
  public static boolean THE_TURRET_ZERO;
  public static DriverStation dStation;

  public static ShuffleboardTab RunningTab = Shuffleboard.getTab("Running Systems");
      public static ShuffleboardLayout Hood = RunningTab.getLayout("Hood", BuiltInLayouts.kList).withSize(2, 5).withPosition(0, 0);
          public static NetworkTableEntry HoodControl;
      public static ShuffleboardLayout Hopper = RunningTab.getLayout("Hopper", BuiltInLayouts.kList).withSize(2, 5).withPosition(2, 0);
          public static NetworkTableEntry IndexBall;
          public static NetworkTableEntry HopperControl;
      public static ShuffleboardLayout Intake = RunningTab.getLayout("Intake", BuiltInLayouts.kList).withSize(2, 5).withPosition(4, 0);
          public static NetworkTableEntry DeployControl;
          public static NetworkTableEntry IntakeControl;
      public static ShuffleboardLayout Shooter = RunningTab.getLayout("Shooter", BuiltInLayouts.kList).withSize(2, 5).withPosition(6, 0);
          public static NetworkTableEntry ShooterControl;
      public static ShuffleboardLayout Swerve = RunningTab.getLayout("Swerve", BuiltInLayouts.kList).withSize(2, 5).withPosition(8, 0);
          public static NetworkTableEntry OpenLoopSwerve;
      public static ShuffleboardLayout Turret = RunningTab.getLayout("Turret", BuiltInLayouts.kList).withSize(2, 5).withPosition(10, 0);
          public static NetworkTableEntry TurretControl;

      public static ShuffleboardTab LukeIsStupidTab = Shuffleboard.getTab("Luke Is Stupid");
        public static ShuffleboardLayout Hang = LukeIsStupidTab.getLayout("Hang", BuiltInLayouts.kList).withSize(2, 5).withPosition(0, 0);
          public static NetworkTableEntry Piston;
        public static ShuffleboardLayout Shooting = LukeIsStupidTab.getLayout("Shooting", BuiltInLayouts.kList).withSize(2, 5).withPosition(4, 0);
          public static NetworkTableEntry ShooterReady;
          public static NetworkTableEntry HoodReady;
          public static NetworkTableEntry TurretReady;
        public static ShuffleboardLayout Infrared = LukeIsStupidTab.getLayout("Index", BuiltInLayouts.kList).withSize(2, 5).withPosition(8, 0);
          public static NetworkTableEntry InfraredReady;
          public static NetworkTableEntry Distance;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    dStation = DriverStation.getInstance();
    THE_TURRET_ZERO = false;
    m_robotContainer = new RobotContainer();

    HoodControl = Hood.add("HoodContol", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    IndexBall = Hopper.add("IndexBall", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    HopperControl = Hopper.add("HopperControl", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    DeployControl = Intake.add("DeployControl", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    IntakeControl = Intake.add("IntakeControl", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    ShooterControl = Shooter.add("ShooterControl", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    OpenLoopSwerve = Swerve.add("OpenLoopSwerve", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    TurretControl = Turret.add("TurretControl", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    Piston = Hang.add("Piston", true).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "orange", "colorWhenFalse", "blue")).getEntry();

    ShooterReady = Shooting.add("Shooter Ready", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    HoodReady = Shooting.add("Hood Ready", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    TurretReady = Shooting.add("Turret Ready", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    InfraredReady = Infrared.add("Index Ready", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    Distance = Infrared.add("Distance", 0.0).withWidget(BuiltInWidgets.kTextView).getEntry();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    for (SwerveMod mod : m_robotContainer.swerveModules()) {
      //SmartDashboard.putNumber("CANCoder Mod " + mod.moduleNumber + " ", mod.getRaw());
    }
  
    m_robotContainer.configuring.doTheConfigurationShuffle();
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }      
    

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
