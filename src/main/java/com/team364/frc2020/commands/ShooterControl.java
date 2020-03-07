package com.team364.frc2020.commands;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.Robot;
import com.team364.frc2020.States.ConfigStates;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.States.*;
import static com.team364.frc2020.Conversions.*;
import com.team364.frc2020.misc.util.Function;

public class ShooterControl extends CommandBase {
    private double velocity;
    private Shooter s_Shooter;
    private Configuration config;

    /**
     * Driver control
     */
    public ShooterControl(double velocity, Shooter s_Shooter, Configuration config) {
        addRequirements(s_Shooter);
        this.velocity = velocity;
        this.s_Shooter = s_Shooter;
        this.config = config;
    }

    @Override
    public void initialize() {
        Robot.ShooterControl.setValue(true);
    }
//configState == ConfigStates.TARGET
    @Override
    public void execute() {
        Function exe = new Function((configState == ConfigStates.TARGET) ? configuration() : standard());
        exe.run();
        boolean inRange;
        if(Math.abs(s_Shooter.fromSensorCounts(s_Shooter.getFlyWheelVel()) - velocity) < 300){
            inRange = true;

        }else{
            inRange = false;
        }
        SmartDashboard.putBoolean("shooter error", inRange);

        Robot.ShooterReady.setBoolean(inRange);
        //withinDeadband(s_Shooter.getFlyWheelVel() - velocity, 300)
        Robot.ShooterReady.setBoolean(withinDeadband(s_Shooter.getFlyWheelVel() - velocity, 300));
    }


    private Runnable standard(){
        return new Runnable(){
            @Override
            public void run(){s_Shooter.setFlyWheelVel(velocity);}
        };  
    }
    private Runnable configuration(){
        return new Runnable(){
            @Override
            public void run(){s_Shooter.setFlyWheelVel(config.ShooterVelocity);
            velocity = config.ShooterVelocity;}

        };  
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted){
        Robot.ShooterControl.setValue(false);
    }


}
