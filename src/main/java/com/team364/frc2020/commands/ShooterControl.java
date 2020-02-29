package com.team364.frc2020.commands;

import com.team364.frc2020.Configuration;
import com.team364.frc2020.Robot;
import com.team364.frc2020.States.ConfigStates;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.team364.frc2020.States.*;

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

    @Override
    public void execute() {
        Function exe = new Function((configState == ConfigStates.TARGET) ? configuration() : standard());
        exe.run();
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
            public void run(){s_Shooter.setFlyWheelVel(config.ShooterVelocity);}
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
