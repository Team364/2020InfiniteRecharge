/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020;

import java.util.HashMap;
import java.util.List;

import com.team364.frc2020.commandGroups.autos.BasicForward;
import com.team364.frc2020.commands.*;
import com.team364.frc2020.commands.autos.DriveToDistance;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Hang s_Hang = new Hang();
  private final Shooter s_Shooter = new Shooter();
  private final Vision s_Vision = new Vision();
  private final Swerve s_Swerve = new Swerve();
  private final Hood s_Hood = new Hood();
  private final Turret s_Turret = new Turret();
  private final Intake s_Intake = new Intake();
  private final Hopper s_Hopper = new Hopper();

  public Configuration configuring = new Configuration(s_Vision, s_Swerve);
  private Command m_autoCommand = new BasicForward(s_Swerve, s_Turret, s_Shooter, s_Hood, s_Vision, configuring, s_Hopper);

  private final static Joystick controller = new Joystick(0);
  private final static Joystick operator = new Joystick(1);
  private final JoystickButton hopperButton = new JoystickButton(operator, 8);
  private final JoystickButton indexButton = new JoystickButton(operator, 7);
  private final JoystickButton intakeSwitch = new JoystickButton(operator, 3);
  private final JoystickButton outtakeSwitch = new JoystickButton(operator, 2);
  private final JoystickButton reverseHopperButton = new JoystickButton(operator, 1);

  private final JoystickButton deploySwitch = new JoystickButton(operator, 6);
  private final JoystickButton retractSwitch = new JoystickButton(operator, 5);

  private final JoystickButton aimSwitch = new JoystickButton(operator, 4);

  private final JoystickButton zeroGyro = new JoystickButton(controller, 2);
 // private final JoystickButton climbDrive = new JoystickButton(controller, 1);  

  private final JoystickButton upClimb = new JoystickButton(controller, 4);
  private final JoystickButton downClimb = new JoystickButton(controller, 1);

  private final JoystickButton lockingClimb = new JoystickButton(controller, 8);



  public static boolean THE_SWITCH = false;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign default commands
    s_Swerve.setDefaultCommand(new OpenLoopSwerve(s_Swerve));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings(){

    //climbDrive.whileHeld(new LockTurnSwerve(s_Swerve));
    upClimb.whileHeld(new HangControl(1, s_Hang));
    downClimb.whileHeld(new HangControl(-1, s_Hang));
    lockingClimb.whenPressed(new InstantCommand(() -> {s_Hang.setPiston();}));

    zeroGyro.whileHeld(new InstantCommand(() -> s_Swerve.zeroGyro()));

    intakeSwitch.whileHeld(new IntakeControl(-0.8, s_Intake));
    outtakeSwitch.whileHeld(new IntakeControl(0.8, s_Intake));
    indexButton.whenPressed(new IndexBall(s_Hopper));
    hopperButton.whileHeld(new HopperControl(-0.6, s_Hopper));
    reverseHopperButton.whileHeld(new HopperControl(0.5, s_Hopper));

    deploySwitch.whenPressed(new DeployControl(true, s_Intake));
    retractSwitch.whenPressed(new DeployControl(false, s_Intake));

    aimSwitch.whenPressed(new InstantCommand(() -> activate_THE_SWITCH()))
      .whenReleased(
        new ParallelCommandGroup(
          new InstantCommand(() -> deactivate_THE_SWITCH()),
          new InstantCommand(() -> s_Shooter.setFlyWheelOff())
        )
      )
      .whileHeld(
        new ParallelCommandGroup(
          new TurretControl(s_Turret, s_Vision, s_Swerve),
          new HoodControl(300, s_Hood, configuring),
          new ShooterControl(4000, s_Shooter, configuring)
        )
      );
  }

  private void activate_THE_SWITCH() {
    THE_SWITCH = true;
  }

  private void deactivate_THE_SWITCH() {
    THE_SWITCH = false;
  }

  public static HashMap<String, Double> SwerveConfig() {
    HashMap<String, Double> SwerveControls = new HashMap<>();
    SwerveControls.put("forward", -controller.getRawAxis(1));
    SwerveControls.put("strafe", controller.getRawAxis(0));
    SwerveControls.put("rotation", controller.getRawAxis(4));

    return SwerveControls;
  }

  public List<SwerveMod> swerveModules() {
    return s_Swerve.modules;
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
  
}
