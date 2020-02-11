package com.team364.frc2020.commands;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;
import static com.team364.frc2020.States.*;

public class TurretControl extends CommandBase {
    private Turret s_Turret;
    private Vision s_Vision;

    /**
     * Driver control
     */
    public TurretControl(Turret s_Turret, Vision s_Vision, Configuration Config) {
        addRequirements(s_Turret);
        this.s_Turret = s_Turret;
        this.s_Vision = s_Vision;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (THE_SWITCH || configState == ConfigStates.TARGET) {
            //TODO: convert form limeY to degrees
            double target = s_Vision.limeY();
            if(target < 270){
                s_Turret.setPosition(target);
            }
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
