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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Shooter s_Shooter = new Shooter();
  private final Hang s_Hang = new Hang();
  private final WheelOfFortune s_Wof = new WheelOfFortune();
  private final Turret s_Turret = new Turret();
  private final Joystick controller = new Joystick(0);
  private final Swerve s_Swerve = new Swerve();
  private final Hopper s_Hopper = new Hopper();
  private final JoystickButton hopperbutto = new JoystickButton(controller, 0);
  private final JoystickButton shooterSwitch = new JoystickButton(controller, 1);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Assign default commands
    s_Swerve.setDefaultCommand(new OpenLoopSwerve(SwerveConfig(), s_Swerve));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    if (shooterSwitch.get()) {
      shooterState = ShooterStates.RAMP_UP;
      if (s_Shooter.getFlyWheelVel() == SHOOTERSPEED) {
        shooterState = ShooterStates.SHOOTING;
      }
    } else if (!shooterSwitch.get() && shooterState == ShooterStates.SHOOTING) {
      shooterState = ShooterStates.RAMP_DOWN;
    } else if (shooterState == ShooterStates.RAMP_DOWN && s_Shooter.getFlyWheelVel() == FERRYSPEED) {
      shooterState = ShooterStates.FERRY;
    }

    hopperbutto.whenPressed(new RunHopper(s_Hopper));
  }


  private HashMap<String, Double> SwerveConfig(){
    HashMap<String, Double> SwerveControls = new HashMap<>();
    SwerveControls.put("forward", -controller.getRawAxis(1));
    SwerveControls.put("strafe", controller.getRawAxis(0));
    SwerveControls.put("rotation", controller.getRawAxis(4));
    return SwerveControls;
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
