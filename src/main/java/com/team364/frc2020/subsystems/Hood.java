package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.team364.frc2020.RobotMap.*;
import edu.wpi.first.wpilibj.PWM;

public class Hood extends SubsystemBase {
    public double desiredHoodAngle;    
    private PWM servoHood;
    private PWM servoHoodSlave;
    
    public Hood() {
        servoHood = new PWM(SERVOHOOD1);
        servoHoodSlave = new PWM(SERVOHOODSLAVE);

    }
    public void changeHoodAngle(double poistion) {
        servoHood.setPosition(poistion);
    }

}