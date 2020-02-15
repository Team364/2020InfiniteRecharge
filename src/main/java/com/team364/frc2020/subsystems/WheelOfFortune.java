/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import static com.team364.frc2020.States.*;

import java.nio.ByteBuffer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.I2C;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class WheelOfFortune extends SubsystemBase {
  private I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private double min;
  private double max;
  private int rgb;
  private TalonSRX mWoF;
  private ByteBuffer buf = ByteBuffer.allocate(5);

  
  Color detectedColor = m_colorSensor.getColor();
  double IR = m_colorSensor.getIR();
  int proximity = m_colorSensor.getProximity();

  /**
   * 
  */
  I2C sensor;
  public WheelOfFortune() {
    mWoF = new TalonSRX(1);
    sensor = new I2C(I2C.Port.kOnboard, 0x39);
    sensor.write(0x00, 192);

  }
  double[] colorArray = {detectedColor.red, detectedColor.green, detectedColor.blue};
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

    if(colorArray[rgb] < max && colorArray[rgb] > min) {
      return true;
    } else {
      return false;
    }   
  }
  public void colorSensor(double motorPower) {
    SmartDashboard.putString("Color", "none");
    if(getDetectedColor(0.4, 0.6, 0) && getDetectedColor(0.25, 0.45, 1) && getDetectedColor(0.05, 0.2, 2)) {
      colorState = ColorStates.RED;
    }
    if(getDetectedColor(0.05, 0.2, 0) && getDetectedColor(0.35, 0.5, 1) && getDetectedColor(0.3, 0.5, 2)) {
      colorState = ColorStates.BLUE;
    }
    if(getDetectedColor(0.1, 0.2, 0) && getDetectedColor(0.5, 0.65, 1) && getDetectedColor(0.15, 0.35, 2)) {
      colorState = ColorStates.GREEN;
    }
    if(getDetectedColor(0.25, 0.45, 0) && getDetectedColor(0.45, 0.65, 1) && getDetectedColor(0.05, 0.2, 2)) {
      colorState = ColorStates.YELLO;
    } 
    else {
      colorState = ColorStates.NONE;
    }
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
      SmartDashboard.putString("Color", "not a wheel of fortune color");
    }
  }
}