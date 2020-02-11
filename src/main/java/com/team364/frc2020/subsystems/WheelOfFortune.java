/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.commands.ColorSensor.*;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class WheelOfFortune extends SubsystemBase {
  private int min;
  private int max;
  private int rgb;
  
  public WheelOfFortune() {

  }
  public void function(){
    if(getDetectedColor(230, 270, 0) && getDetectedColor(0, 25, 1) && getDetectedColor(0, 25, 3)) {
      colorState = ColorStates.RED;
    }else if(getDetectedColor(0, 25, 0) && getDetectedColor(230, 270, 1) && getDetectedColor(230, 270, 3)) {
      colorState = ColorStates.BLUE;
    }else if(getDetectedColor(0, 1, 0) && getDetectedColor(0, 1, 1) && getDetectedColor(0, 1, 3)) {
      colorState = ColorStates.GREEN;
    }else if(getDetectedColor(0, 1, 0) && getDetectedColor(0, 1, 1) && getDetectedColor(0, 1, 3)) {
      colorState = ColorStates.YELLO;
    }
    colorState = ColorStates.RED;
    switch (colorState) {
        case RED:
        break;
        case BLUE:
        break;
        case GREEN:
        break;
        case YELLO:
        break;
        default: 
        break;

    }   
  }
  /**
   * @param min the minimum value for the rgb value you are retrieving
   * @param max the maximum value for the rgb value you are retrieving
   * @param rgb sets which rgb value you are retrieving, 0 gets the red, 1 gets the green, 2 gets the blue
   * @return true or false: if the color is detected or not
   */
  private boolean getDetectedColor(int min, int max, int rgb) {
    this.min = min;
    this.max = max;
    this.rgb = rgb;

    if(detectedColor[rgb] < max && detectedColor[rgb] > min) {
      return true;
    } else {
      return false;
    }
  }
}