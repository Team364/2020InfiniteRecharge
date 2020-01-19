package com.team364.frc2020.commands;

import com.team364.frc2020.RobotContainer;
import com.team364.frc2020.misc.math.Vector2;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static com.team364.frc2020.RobotMap.*;

public class OpenSwerve extends CommandBase {

    public int cycles;
    private double forward;
    private double strafe;
    private double rotation;
    private Vector2 translation;
    
    private Vector2 lastTranslation;
    private double lastRotation;


    /**
     * Driver control
     */
    public OpenSwerve() {
    }

    @Override
    public void initialize() {
        addRequirements(RobotContainer.s_Swerve);
        cycles = 0;
    }

    @Override
    public void execute() {
        forward = -RobotContainer.controller.getRawAxis(1);
        strafe = RobotContainer.controller.getRawAxis(0);
        rotation = RobotContainer.controller.getRawAxis(4);
        boolean zeroPoint = false;
        if(zeroPoint){
            translation = new Vector2(-1, 0);
        }
        else{
            translation = new Vector2(forward, strafe);
        }
        if (Math.abs(forward) > STICKDEADBAND || Math.abs(strafe) > STICKDEADBAND || Math.abs(rotation) > STICKDEADBAND) {
            RobotContainer.s_Swerve.holonomicDrive(translation, rotation, !zeroPoint);
            lastTranslation = translation;
            lastRotation = rotation;
            cycles++;

        } else {
            if(cycles != 0){
                RobotContainer.s_Swerve.holonomicDrive(lastTranslation, lastRotation, false);
            }
        }	
        if(cycles != 0){
            RobotContainer.s_Swerve.updateKinematics();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
