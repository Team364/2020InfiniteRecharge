package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
    public PWM hoodServo;
    public PWM slaveServo;

	public Hood(){
        hoodServo = new PWM(15);
        slaveServo = new PWM(16);
    }
    public void setAngle(double angle){
        hoodServo.setPosition(angle);
        slaveServo.setPosition(angle);
    }
    public double getPosition(){
        return hoodServo.getPosition();
    }

}
