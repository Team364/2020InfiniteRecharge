package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

import static com.team364.frc2020.RobotMap.*;

public class Hood extends SubsystemBase {
    private int num_rotations_ = 0;
    private double last_angle_;
    private double offset = 295.5;
    private double setpoint = 0;

    public PWM hoodServo;
    public PWM slaveServo;
    public AnalogInput hoodEncoder;
    private PIDController pidController;


	public Hood(){
        hoodServo = new PWM(HOOD);
        slaveServo = new PWM(HOODSLAVE);
        hoodEncoder = new AnalogInput(HOODENCODER);
        last_angle_ = getRawAngle();
        pidController = new PIDController(0.01, 0, 0);
        pidController.setTolerance(5);
        setAngle(0);
    }

    public void setAngle(double angle){
        setpoint = angle;
    }

    public void setPower(){
        pidController.setSetpoint(MathUtil.clamp(setpoint, 0, 600));
        double rawPower = pidController.calculate(getPosition());
        SmartDashboard.putNumber("rawPower", rawPower);
        double clamped = MathUtil.clamp(rawPower, -1, 1);
        SmartDashboard.putNumber("clamped", clamped);
        hoodServo.setSpeed(-clamped);
        slaveServo.setSpeed(clamped);
    }

    public double getPosition(){
        return -(getRawAngle() - num_rotations_ * 360) + offset;
    }

    public double getRawAngle(){
        double angle = hoodEncoder.getVoltage() / RobotController.getVoltage5V() * 360;
        return angle;
    }

    @Override
    public void periodic(){
        if(getRawAngle() - last_angle_ > 180){
            ++num_rotations_;
        }else if(getRawAngle() - last_angle_ < -180){
            --num_rotations_;
        }        
        last_angle_ = getRawAngle();
        SmartDashboard.putNumber("position", getPosition());
        
        setPower();
    }
}
