package com.team364.frc2020.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.team364.frc2020.RobotMap.*;

import com.team254.lib.util.SynchronousPID;

public class Hood extends SubsystemBase {
    public PWM hoodServo;
    public PWM slaveServo;
    public AnalogInput hoodEncoder;
    public SynchronousPID pid;

	public Hood(){
        hoodServo = new PWM(HOOD);
        slaveServo = new PWM(HOODSLAVE);
        hoodEncoder = new AnalogInput(HOODENCODER);
        pid = new SynchronousPID(1, 0, 0);
        pid.setInputRange(0, 2);
        pid.setOutputRange(-1.0, 1.0);
        pid.setDeadband(0.1);
        pid.setSetpoint(0);
    }
    public void setAngle(double angle){
        pid.setSetpoint(angle);
        SmartDashboard.putNumber("wow", angle);
    }

    public int getPosition(){
        return hoodEncoder.getValue();
    }

    @Override
    public void periodic(){
        pid.calculate(getPosition());
        //hoodServo.setSpeed(pid.get());
        //slaveServo.setSpeed(-pid.get());
        //SmartDashboard.putNumber("output", pid.get());
        //SmartDashboard.putNumber("input", hoodEncoder.getValue());
    }
}
