package com.team364.frc2020.commandGroups.autos;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.commands.HoodControl;
import com.team364.frc2020.commands.HopperControl;
import com.team364.frc2020.commands.ShooterControl;
import com.team364.frc2020.commands.TurretControl;
import com.team364.frc2020.commands.autos.DriveToDistance;
import com.team364.frc2020.subsystems.Hood;
import com.team364.frc2020.subsystems.Hopper;
import com.team364.frc2020.subsystems.Shooter;
import com.team364.frc2020.subsystems.Swerve;
import com.team364.frc2020.subsystems.Turret;
import com.team364.frc2020.subsystems.Vision;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class BasicForward extends SequentialCommandGroup {

    public BasicForward(Swerve s_Swerve, Turret s_Turret, Shooter s_Shooter, Hood s_Hood, Vision s_Vision, Configuration config, Hopper s_Hopper) {
        addCommands(
          new WaitCommand(1),
          new ParallelDeadlineGroup(
            new WaitCommand(10),  
            new ParallelCommandGroup(
              new TurretControl(s_Turret, s_Vision, s_Swerve),
              new HoodControl(300, s_Hood, config),
              new ShooterControl(4250, s_Shooter, config),
              new SequentialCommandGroup(
                new WaitCommand(4),
                new HopperControl(-0.4, s_Hopper)
              )
            )
          ),
          new ParallelDeadlineGroup(new WaitCommand(2), new DriveToDistance(0.25, s_Swerve))
        );
      }

}