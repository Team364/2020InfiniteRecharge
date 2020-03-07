package com.team364.frc2020.commands.autos;

import java.util.Set;

import com.team364.frc2020.misc.math.Vector2;
import com.team364.frc2020.subsystems.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveToDistance extends CommandBase {
    private double distance;
    private double direction;
    private double startGyro;
    private Swerve s_Swerve;
    private PIDController distanceControl;
    private PIDController snapController;
    private int cycles = 0;
    private double speed;

    public DriveToDistance(double speed, Swerve s_Swerve) {
        this.distance = distance;
        this.direction = direction;
        this.speed = speed;
        this.s_Swerve = s_Swerve;
        distanceControl = new PIDController(1, 0, 0);
        snapController = new PIDController(0.01, 0, 0);
        snapController.setTolerance(5);
    }

    @Override
    public void initialize() {
        startGyro = s_Swerve.getYaw();
    }

    @Override
    public void execute() {
       /* double speed = distanceControl.calculate(s_Swerve.getDriveDistance(), distance);
        snapController.setSetpoint(startGyro);
        double rotation = snapController.calculate(s_Swerve.getYaw());
        SmartDashboard.putNumber("checking driving auto", cycles);*/
        s_Swerve.holonomicDrive(new Vector2(speed, 0.0), 0.0, true);
        s_Swerve.updateKinematics();

    }



        @Override
    public void end(boolean interrupted) {
        s_Swerve.holonomicDrive(new Vector2(0.0, 0.0), 0.0, true);
        s_Swerve.updateKinematics();
    }
}
