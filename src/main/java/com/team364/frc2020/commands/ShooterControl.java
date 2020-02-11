package com.team364.frc2020.commands;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;
import static com.team364.frc2020.States.*;

public class ShooterControl extends CommandBase {
    private Shooter s_Shooter;
    private Vision s_Vision;
    private Configuration Config;

    /**
     * Driver control
     */
    public ShooterControl(Shooter s_Shooter, Vision s_Vision, Configuration Config) {
        addRequirements(s_Shooter);
        this.s_Shooter = s_Shooter;
        this.s_Vision = s_Vision;
        this.Config = Config;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (THE_SWITCH) {
            // "0" means the system is shooter
            s_Shooter.setFlyWheelVel(s_Vision.targetLogic(0));
        }
        if (configState == ConfigStates.TARGET) {
           s_Shooter.setFlyWheelVel(Config.getShooterVel());
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
