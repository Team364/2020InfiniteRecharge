package com.team364.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.Robot;
import com.team364.frc2020.subsystems.*;

public class HangControl extends CommandBase {
    private Hang s_Hang;
    private int power;

    public HangControl(Hang s_Hang, int power) {
        this.s_Hang = s_Hang;
        this.power = power;
    }

    @Override
    public void initialize() {
        Robot.HangControl.setValue(true);
        addRequirements(s_Hang);
    }

    @Override
    public void execute() {
        s_Hang.climb(power);
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.HangControl.setValue(false);
        s_Hang.climb(power);
    }
}