package com.team364.frc2020.commands;

import com.team364.frc2020.subsystems.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

import static com.team364.frc2020.Robot.*;
import com.team364.frc2020.Robot;

public class TurretControl extends CommandBase {
    private Turret s_Turret;
    private Vision s_Vision;
    private Swerve s_Swerve;
    private char letterSide = 'L';
    private double minSoft;
    private double maxSoft;
    private boolean flipping = false;
    private double target;

    /**
     * Driver control
     */
    public TurretControl(Turret s_Turret, Vision s_Vision, Swerve s_Swerve) {
        addRequirements(s_Turret);
        this.s_Turret = s_Turret;
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
        minSoft = s_Turret.toTurretDegrees(TURRETMINSOFT);
        maxSoft = s_Turret.toTurretDegrees(TURRETMAXSOFT);
    }

    @Override
    public void initialize() {
        s_Turret.controlled = true;
        Robot.TurretControl.setValue(true);
    }

    @Override
    public void execute() {
        if(THE_TURRET_ZERO){
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
            if(s_Vision.hasTarget()){
                flipping = false;
                target = to360Boundaries(s_Turret.getDegreePosition() + s_Vision.limeX());
                if(target <= 350 && s_Turret.getDegreePosition() < 45 && target > 180){
                    flipping = true;
                }else if(target > 0 && s_Turret.getDegreePosition() > 285 && target < 180){
                    flipping = true;
                }
            }else if(!s_Vision.hasTarget() && !flipping){
                double switchTarget = (letterSide == 'L') ? LEFTTURRETRANGE : RIGHTTURRETRANGE;
                target = to360Boundaries(52 + switchTarget + to180Boundaries(s_Swerve.getYaw()));

                if(withinDeadband(s_Turret.getDegreePosition() - target, 8)){
                    letterSide = (letterSide == 'L') ? 'R' : 'L';
                }
            } 
            if(target < minSoft && target > 350){
                target = minSoft;
            }else if(target <= 350 && s_Turret.getDegreePosition() < 45 && target > 180){
                target = maxSoft;
            }
            s_Turret.setPosition(s_Turret.toTurretCounts(target));
        }
    }

    @Override
    public void end(boolean interrupted){
        flipping = false;
        s_Turret.controlled = false;
        Robot.TurretControl.setValue(false);
    }

}
