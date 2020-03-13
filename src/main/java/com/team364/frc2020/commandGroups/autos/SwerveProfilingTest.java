package com.team364.frc2020.commandGroups.autos;

import com.team364.frc2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SwerveProfilingTest extends SequentialCommandGroup {

    public SwerveProfilingTest( RobotContainer container) {
        addCommands(
            new ParallelDeadlineGroup(
                container.SwerveProfiling()
            )
        );
      }
}