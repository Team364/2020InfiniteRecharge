package com.team364.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj.I2C;
import static com.team364.frc2020.States.*;

import java.nio.ByteBuffer;

import com.team364.frc2020.Robot;


public class ColorSensor extends CommandBase {
    private ByteBuffer buf = ByteBuffer.allocate(5);
    /**
     * 255, 0, 0 = red, 
     * 0, 255, 255 = blue,  
     * 0, 255, 0 = green, 
     * 255, 255, 0 = yello 
    */
    public static int[] detectedColor;
    I2C sensor;
    public ColorSensor(){
        sensor = new I2C(I2C.Port.kOnboard, 0x39);
        sensor.write(0x00,192);
    }
    public void setDetectedColor(){
        detectedColor = getColorArray();
    }
    public int[] getColorArray(){
        return new int[] {red(), green(), blue()};
    }
    public int red(){
        sensor.read(0x16, 3, buf);
        return buf.get(0);
    }
    public int green(){ 
        sensor.read(0x18, 2, buf);
        return buf.get(0);
    }
    public int blue(){
        sensor.read(0x1a, 2, buf);
        return buf.get(0);
    }
    
}