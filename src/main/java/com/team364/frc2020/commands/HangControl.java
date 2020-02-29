package com.team364.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.Robot;
import com.team364.frc2020.subsystems.*;

public class HangControl extends CommandBase {
    private Hang s_Hang;
    private Configuration config;

    public HangControl(Hang s_Hang, Configuration config) {
        addRequirements(s_Hang);
        this.config = config;
        this.s_Hang = s_Hang;
    }

    @Override
    public void initialize() {
        Robot.HangControl.setValue(true);
    }

    @Override
    public void execute() {
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.HangControl.setValue(false);
    }
}