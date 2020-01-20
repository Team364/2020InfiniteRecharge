package com.team364.frc2020.commands;

import com.team364.frc2020.RobotContainer;
import com.team364.frc2020.misc.math.Vector2;
import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj2.command.CommandBase;
import static com.team364.frc2020.RobotMap.*;

import java.util.HashMap;

public class OpenSwerve extends CommandBase {

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
    public OpenSwerve(HashMap<String, Double> controls, Swerve swerve) {
        this.forward = controls.get("forward");
        this.strafe = controls.get("strafe");
        this.rotation = controls.get("rotation");
        s_Swerve = swerve;
        addRequirements(s_Swerve);
    }

    @Override
    public void initialize() {
        addRequirements(s_Swerve);
        cycles = 0;
    }

    @Override
    public void execute() {
        boolean zeroPoint = false;
        if(zeroPoint){
            translation = new Vector2(-1, 0);
        }
        else{
            translation = new Vector2(forward, strafe);
        }
        if (Math.abs(forward) > STICKDEADBAND || Math.abs(strafe) > STICKDEADBAND || Math.abs(rotation) > STICKDEADBAND) {
            s_Swerve.holonomicDrive(translation, rotation, !zeroPoint);
            lastTranslation = translation;
            lastRotation = rotation;
            cycles++;

        } else {
            if(cycles != 0){
                s_Swerve.holonomicDrive(lastTranslation, lastRotation, false);
            }
        }	
        if(cycles != 0){
            s_Swerve.updateKinematics();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}