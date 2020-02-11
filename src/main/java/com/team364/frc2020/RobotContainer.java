/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020;

import java.util.HashMap;

import com.team364.frc2020.commands.*;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;



/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public Configuration configuring;
  private final Shooter s_Shooter = new Shooter();
  private final Vision s_Vision = new Vision();
  private final Swerve s_Swerve = new Swerve();


  public final static Joystick controller = new Joystick(0);
  public final static Joystick operator = new Joystick(1);
  private final Hopper s_Hopper = new Hopper();
  private final JoystickButton hopperbutto = new JoystickButton(operator, 0);
  private final JoystickButton aimSwitch = new JoystickButton(operator, 1);
  private final JoystickButton zeroGyro = new JoystickButton(controller, 1);
  public static boolean THE_SWITCH = false;


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Assign default commands
    s_Swerve.setDefaultCommand(new OpenLoopSwerve(-controller.getRawAxis(1), controller.getRawAxis(0), controller.getRawAxis(4), s_Swerve));
    s_Shooter.setDefaultCommand(new ShooterControl(s_Shooter, s_Vision, configuring));
    zeroGyro.whenPressed(new RunCommand(() -> s_Swerve.zeroGyro()));

    // Configure the button bindings
    configureButtonBindings();
    configuring = new Configuration(s_Vision, s_Swerve);

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    aimSwitch.whenPressed(new RunCommand(() -> activate_THE_SWITCH()));
    aimSwitch.whenReleased(new RunCommand(() -> deactivate_THE_SWITCH()));
    hopperbutto.whenPressed(new RunHopper(s_Hopper));
  }

  private void activate_THE_SWITCH(){
    THE_SWITCH = true;
  }
  private void deactivate_THE_SWITCH(){
    THE_SWITCH = false;
  }


  public static HashMap<String, Double> SwerveConfig(){
    HashMap<String, Double> SwerveControls = new HashMap<>();
    SwerveControls.put("forward", -controller.getRawAxis(1));
    SwerveControls.put("strafe", controller.getRawAxis(0));
    SwerveControls.put("rotation", controller.getRawAxis(4));

    return SwerveControls;
  }
  /**
   * Literally nothing...
   * Rohit doesn't like the yellow that visual studio 
   * puts on a class instance when it isn't used, lol imagine.
   */
  public void nothing(){

  }
  /*
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   *
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
  */
}
