/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;

import java.util.HashMap;
import org.json.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Vision implements Subsystem {
    public HashMap<Double, String> visionHeights = new HashMap();
    public double[] closestTarget = new double[2];
    public static HashMap<String, Double> TARGET = new HashMap<String, Double>();

    private JSONObject targetFile;

    public Vision() {
        register();

        // fill in other heights
        visionHeights.put(1.0, "height1");

        try {
            targetFile = new JSONObject("D:/ColtonJson.txt");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public double visionTrack() {
        double height = 0;
        return height;
    }

    public void setTarget() {
        findClosestTargets(visionTrack());
        TARGET = calculateTarget();
    }

    public HashMap<String, Double> calculateTarget() {
        HashMap<String, Double> finalTarget = new HashMap<>();
        double[] linearHolder = linearInterpolation();
        finalTarget.put("Shooter", linearHolder[0]);
        finalTarget.put("Hood", linearHolder[0]);
        return finalTarget;
    }

    public double[] linearInterpolation() {
        double[] JSONplots = new double[10];
        try {

        JSONplots[0] = 2.0;//(double)targetFile.get("name");
            // targetFile.get(visionHeights.get(closestTarget[0]));
            // targetFile.get(visionHeights.get(closestTarget[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }

       return JSONplots;
   }

   public void findClosestTargets(double height){
       visionHeights.forEach((target, name) -> {
           double holder = Math.abs(height - target);
           if(holder < closestTarget[0] || holder < closestTarget[1] ){
               if(Math.abs(holder - closestTarget[0]) < Math.abs(holder - closestTarget[1])){
                   closestTarget[1] = height;
               }
               else{
                   closestTarget[0] = height;
               }
           }
       });
   }
   @Override
   public void periodic(){
       if(THE_SWITCH){

       }   
       setTarget();
       try{
            System.out.println(targetFile);
       }catch(Exception e){System.out.println(e);}

       //System.out.println(TARGET);
   }


}

