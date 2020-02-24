package com.team364.frc2020.commands;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.Conversions.*;

public class TurretControl extends CommandBase {
    private Turret s_Turret;
    private Vision s_Vision;
    private Swerve s_Swerve;

    /**
     * Driver control
     */
    public TurretControl(Turret s_Turret, Vision s_Vision, Swerve s_Swerve, Configuration Config) {
        addRequirements(s_Turret);
        this.s_Turret = s_Turret;
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(THE_SWITCH){
            double target;
            if(s_Vision.hasTarget() == 1){
                target = to180Boundaries(s_Turret.getDegreePosition() - s_Vision.limeX());
                if(target < -160 && target > -175){
                    target = -160;
                }else if(target <= -175){
                    target += 360;
                }
            }else {
                target = -(Math.abs(s_Turret.getProperPosition()) - 90) * (s_Turret.getProperPosition() / s_Turret.getProperPosition());
            }
            s_Turret.setPosition(toCounts(target));
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
