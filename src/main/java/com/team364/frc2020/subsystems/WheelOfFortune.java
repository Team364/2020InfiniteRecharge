/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static com.team364.frc2020.States.*;

import java.nio.ByteBuffer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.I2C;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class WheelOfFortune extends SubsystemBase {
  private double min;
  private double max;
  private int rgb;
  private TalonSRX mWoF;
  private ByteBuffer buf = ByteBuffer.allocate(5);
  /**
   * 
  */
  public static int[] detectedColor;
  I2C sensor;
  
  public WheelOfFortune() {
    mWoF = new TalonSRX(1);
    sensor = new I2C(I2C.Port.kOnboard, 0x39);
    sensor.write(0x00, 192);
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
  public void setDetectedColor(){
    detectedColor = getColorArray();
  }
  public int[] getColorArray(){
    return new int[] {red(), green(), blue()};
  }
  /**
   * @param min the minimum value for the rgb value you are retrieving*
   * @param max the maximum value for the rgb value you are retrieving*
   * @param rgb sets which rgb value you are retrieving,  0 gets the red,  1 gets the green,  2 gets the blue*
   * @return true if the color is detected <li>false if the color is not detected</li>
   * 
   */
  private boolean getDetectedColor(double min, double max, int rgb) {
    this.min = min;
    this.max = max;
    this.rgb = rgb;

    if(detectedColor[rgb] < max && detectedColor[rgb] > min) {
      return true;
    } else {
      return false;
    }   


  }
  public void detectedColor(){
    if(getDetectedColor(0.4, 0.6, 0) && getDetectedColor(0.25, 0.45, 1) && getDetectedColor(0.05, 0.2, 2)) {
      colorState = ColorStates.RED;
    }else if(getDetectedColor(0.05, 0.2, 0) && getDetectedColor(0.35, 0.5, 1) && getDetectedColor(0.3, 0.5, 2)) {
      colorState = ColorStates.BLUE;
    }else if(getDetectedColor(0.1, 0.2, 0) && getDetectedColor(0.5, 0.65, 1) && getDetectedColor(0.15, 0.35, 2)) {
      colorState = ColorStates.GREEN;
    }else if(getDetectedColor(0.25, 0.45, 0) && getDetectedColor(0.45, 0.65, 1) && getDetectedColor(0.05, 0.2, 2)) {
      colorState = ColorStates.YELLO;
    }
  }
  public void moveWoF(double motorPower) {
    if(colorState == ColorStates.RED) {
      mWoF.set(ControlMode.PercentOutput, motorPower);
      SmartDashboard.putString("Color", "red");
    } else if(colorState == ColorStates.GREEN) {
      mWoF.set(ControlMode.PercentOutput, motorPower);
      SmartDashboard.putString("Color", "green");
    } else if(colorState == ColorStates.BLUE) {
      mWoF.set(ControlMode.PercentOutput, motorPower);
      SmartDashboard.putString("Color", "blue");
    } else if(colorState == ColorStates.YELLO) {
      mWoF.set(ControlMode.PercentOutput, motorPower);
      SmartDashboard.putString("Color", "yello");
    } else if(colorState == ColorStates.NONE) {
      SmartDashboard.putString("color", "not a wheel of fortune color");
    }

  /**public void moveWoF(double motorPower) {
    switch (colorState) {
      case RED:
        mWoF.set(ControlMode.PercentOutput, motorPower);
        SmartDashboard.putString("Color", "red");
      break;
      case BLUE:
        mWoF.set(ControlMode.PercentOutput, motorPower);
        SmartDashboard.putString("Color", "blue");
      break;
      case GREEN:
        mWoF.set(ControlMode.PercentOutput, motorPower);
        SmartDashboard.putString("Color", "green");
      break;
      case YELLO:
        mWoF.set(ControlMode.PercentOutput, motorPower);
        SmartDashboard.putString("Color", "yello");
      break;
      default: 
        mWoF.set(ControlMode.PercentOutput, 0);
        SmartDashboard.putString("Color", "not a wheel of fortune color");
      break;

  }*/  
  }
}