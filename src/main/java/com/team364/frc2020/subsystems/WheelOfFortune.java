/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.commands.ColorSensor.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

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
  private TalonSRX mWheelOfFortune;
  
  public WheelOfFortune() {
    mWheelOfFortune = new TalonSRX(20);
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
          mWheelOfFortune.set(ControlMode.Position, 20);
        break;
        case BLUE:
          mWheelOfFortune.set(ControlMode.Position, 30);
        break;
        case GREEN:
          mWheelOfFortune.set(ControlMode.Position, -10);
        break;
        case YELLO:
          mWheelOfFortune.set(ControlMode.Position, -20);
        break;
        default: 
          SmartDashboard.
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