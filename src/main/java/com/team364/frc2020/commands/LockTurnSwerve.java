package com.team364.frc2020.commands;

import com.team364.frc2020.RobotContainer;
import com.team364.frc2020.misc.math.Vector2;
import com.team364.frc2020.subsystems.Swerve;
import static com.team364.frc2020.RobotMap.*;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;


public class LockTurnSwerve extends CommandBase {

    public int cycles;
    private double forward;
    private double strafe;
    private double rotation;
    private Vector2 translation;
    
    private Vector2 lastTranslation;
    private double lastRotation;

    private Swerve s_Swerve;
    private PIDController snapController;

    public LockTurnSwerve(Swerve s_Swerve) {
        this.s_Swerve = s_Swerve;
        snapController = new PIDController(0.01, 0, 0);
        snapController.setTolerance(5);
    }

    @Override
    public void initialize() {
        addRequirements(s_Swerve);
        cycles = 0;
        snapController.setSetpoint(135);
    }

    @Override
    public void execute() {
        forward = RobotContainer.SwerveConfig().get("forward");
        strafe = RobotContainer.SwerveConfig().get("strafe");
        rotation = MathUtil.clamp(snapController.calculate(s_Swerve.getYaw()), -1, 1); 

        translation = new Vector2(forward, strafe);

        if (Math.abs(forward) > STICKDEADBAND || Math.abs(strafe) > STICKDEADBAND || Math.abs(rotation) > STICKDEADBAND) {
            s_Swerve.holonomicDrive(translation, rotation, true);
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

}
