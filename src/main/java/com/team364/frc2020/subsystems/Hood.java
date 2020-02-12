package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.team364.frc2020.RobotMap.*;
import edu.wpi.first.wpilibj.PWM;

public class Hood extends SubsystemBase {
    public double desiredHoodAngle;    
    private PWM hoodServo;
    private PWM slaveHoodServo;
    
    public Hood() {
        hoodServo = new PWM(15);
        slaveHoodServo = new PWM(16);
    }
    public void changeHoodAngle(double poistion) {
        hoodServo.setPosition(poistion);
        slaveHoodServo.setPosition(poistion);
    }

}