package com.team364.frc2020.OI;

import edu.wpi.first.wpilibj.Joystick;



public class OperatorOI {
    //Driver Controller
    //Xbox One Wired Controller
    public Joystick buttoBoxo;
    //Lift Buttons
  
    /**
     * OI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public OperatorOI() {
        updateControl();
    }
    public void updateControl(){

        //Initialize Operator Controller
        buttoBoxo = new Joystick(1);
 
    }


}