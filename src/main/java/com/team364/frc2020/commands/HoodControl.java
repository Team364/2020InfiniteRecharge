package com.team364.frc2020.commands;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;
import static com.team364.frc2020.States.*;

public class HoodControl extends CommandBase {
    private Hood s_Hood;
    private Vision s_Vision;
    private Configuration Config;

    /**
     * Driver control
     */
    public HoodControl(Hood s_Hood, Vision s_Vision, Configuration Config) {
        addRequirements(s_Hood);
        this.s_Hood = s_Hood;
        this.s_Vision = s_Vision;
        this.Config = Config;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (THE_SWITCH) {
            // "1" means the system is hood
            //s_Hood(s_Vision.targetLogic(1));
        }
        if (configState == ConfigStates.TARGET) {
           //s_Shooter.setFlyWheelVel(Config.getShooterVel());
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
