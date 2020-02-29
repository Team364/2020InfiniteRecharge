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
import static com.team364.frc2020.Robot.*;
import static com.team364.frc2020.RobotMap.*;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Talon;

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
  protected double min;
  protected double max;
  protected int rgb;
  protected ByteBuffer buf = ByteBuffer.allocate(5);
  public double motorColor;
  private TalonSRX mWoF;
  protected boolean detectingColor;
  
  Color detectedColor = m_colorSensor.getColor();
  double IR = m_colorSensor.getIR();
  int proximity = m_colorSensor.getProximity();

  /**
   * 
  */
  I2C sensor;
  public WheelOfFortune() {
    sensor = new I2C(I2C.Port.kOnboard, 0x39);
    sensor.write(0x00, 192);
    mWoF = new TalonSRX(2);
    detectingColor = true;
  }
  public boolean red() {
    if((colorRed < 0.6 && colorRed > 0.4) && (colorGreen < 0.45 && colorGreen > 0.25) && (colorBlue < 0.2 && colorBlue > 0.05)) {
      return true;
    } else {
      return false;
    }
  }
  public boolean blue() {
    if ((colorRed < 0.2 && colorRed > 0.05) && (colorGreen < 0.55 && colorGreen > 0.4) && (colorBlue < 0.5 && colorBlue > 0.3)) {
      return true;
    } else {
      return false;
    }
  }
  public boolean green() {
    if ((colorRed < 0.2 && colorRed > 0.1) && (colorGreen < 0.65 && colorGreen > 0.5) && (colorBlue < 0.35 && colorBlue > 0.15)) {
      return true;
    } else {
      return false;
    }
  }
  public boolean yello() {
    if ((colorRed < 0.45 && colorRed > 0.25) && (colorGreen < 0.65 && colorGreen > 0.45) && (colorBlue < 0.2 && colorBlue > 0.05)) {
      return true;
    } else {
      return false;
    }
  }
  public void detectColor() {
    if (red()) {
      colorState = ColorStates.RED;
      mWoF.set(ControlMode.PercentOutput, 0.5);
    } else if (yello()) {
      colorState = ColorStates.YELLO;
    } else if (blue()) {
      colorState = ColorStates.BLUE;
    } else if (green()) {
      colorState = ColorStates.GREEN;
    } else {
      colorState = ColorStates.NONE;
    }
    motorColor = MOTORCOLORNONE;
    if(colorState == ColorStates.RED) {
      motorColor = MOTORCOLORRED;
      mWoF.set(ControlMode.PercentOutput, motorColor);
      SmartDashboard.putString("Read Color", "red");
      SmartDashboard.putString("Actual Color", "blue");
      SmartDashboard.putString("WoF Moving", "yes, speed: " + motorColor);
    } else if(colorState == ColorStates.GREEN) {
        motorColor = MOTORCOLORGREEN;
        mWoF.set(ControlMode.PercentOutput, motorColor);
        SmartDashboard.putString("Read Color", "green");
        SmartDashboard.putString("Actual Color", "yello");
        SmartDashboard.putString("WoF Moving", "yes, speed: " + motorColor);
    } else if(colorState == ColorStates.BLUE) {
        motorColor = MOTORCOLORBLUE;
        mWoF.set(ControlMode.PercentOutput, motorColor);
        SmartDashboard.putString("Read Color", "blue");
        SmartDashboard.putString("Actual Color", "red");
        SmartDashboard.putString("WoF Moving", "yes, speed: " + motorColor);
    } else if(colorState == ColorStates.YELLO) {
        motorColor = MOTORCOLORYELLO;
        mWoF.set(ControlMode.PercentOutput, motorColor);
        SmartDashboard.putString("Read Color", "yello");
        SmartDashboard.putString("Actual Color", "green");
        SmartDashboard.putString("WoF Moving", "yes, speed: " + motorColor);
    } else if(colorState == ColorStates.NONE) {
        motorColor = MOTORCOLORNONE;
        mWoF.set(ControlMode.PercentOutput, motorColor);
        SmartDashboard.putString("Read Color", "none, speed: 0");
        SmartDashboard.putString("Actual Color", "none");
        SmartDashboard.putString("WoF Moving", "no");
    }
  }
  public void stopColorDetecting() {
    mWoF.set(ControlMode.PercentOutput, 0);
    SmartDashboard.putString("WoF Moving", "no");
  }
  public void moveMotorPlease() {
    mWoF.set(ControlMode.PercentOutput, 0.5);
  }
}