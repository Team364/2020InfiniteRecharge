package com.team364.frc2020.commands;

import com.team364.frc2020.misc.math.Vector2;
import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj2.command.CommandBase;
import static com.team364.frc2020.RobotMap.*;

import com.team364.frc2020.Robot;
import com.team364.frc2020.RobotContainer;

public class OpenLoopSwerve extends CommandBase {

    public int cycles;
    private double forward;
    private double strafe;
    private double rotation;
    private Vector2 translation;
    
    private Vector2 lastTranslation;
    private double lastRotation;
    private Swerve s_Swerve;



    /**
     * Driver control
     */
    public OpenLoopSwerve(Swerve swerve) {
        s_Swerve = swerve;
        addRequirements(s_Swerve);
        //withTimeout(0.01);
    }

    @Override
    public void initialize() {
        Robot.OpenLoopSwerve.setValue(true);
        addRequirements(s_Swerve);
        cycles = 0;
    }

    @Override
    public void execute() {
        forward = RobotContainer.SwerveConfig().get("forward");
        strafe = RobotContainer.SwerveConfig().get("strafe");
        rotation = RobotContainer.SwerveConfig().get("rotation");
        
        boolean zeroPoint = false;
        if(zeroPoint){
            translation = new Vector2(-1, 0);
        }
        else{
            translation = new Vector2(forward, strafe);
        }
        if (Math.abs(forward) > STICKDEADBAND || Math.abs(strafe) > STICKDEADBAND || Math.abs(rotation) > STICKDEADBAND) {
            //s_Swerve.holonomicDrive(translation, rotation, !zeroPoint);
            s_Swerve.holonomicDrive(translation, rotation, false);

            lastTranslation = translation;
            lastRotation = rotation;
            cycles++;

        } else {
            if(cycles != 0){
                s_Swerve.holonomicDrive(lastTranslation, lastRotation, false);
            }
        }	
        if(cycles != 0){
            s_Swerve.updateKinematics(false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.OpenLoopSwerve.setValue(false);
    }

}
