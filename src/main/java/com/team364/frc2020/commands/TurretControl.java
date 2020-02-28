package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

public class TurretControl extends CommandBase {
    private Turret s_Turret;
    private Vision s_Vision;
    private char letterSide = 'L';

    /**
     * Driver control
     */
    public TurretControl(Turret s_Turret, Vision s_Vision) {
        addRequirements(s_Turret);
        this.s_Turret = s_Turret;
        this.s_Vision = s_Vision;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double target;
        if(s_Vision.hasTarget()){
            target = to360Boundaries(s_Turret.getDegreePosition() + s_Vision.limeX());
            if(target < 0 && target > 350){
                target = 0;
            }else if(target <= -175){
                target += 360;
            }
        }else {
            double switchTarget = (letterSide == 'L') ? LEFTTURRETRANGE : RIGHTTURRETRANGE;
            target = -(Math.abs(s_Turret.getProperPosition()) - 90 + switchTarget) * (s_Turret.getProperPosition() / s_Turret.getProperPosition());
            if(withinDeadband((s_Turret.getProperPosition() - target), 0.1)){
                letterSide = (letterSide == 'L') ? 'R' : 'L';
            }
        }
        s_Turret.setPosition(toCounts(target));
    }

}
