/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team364.frc2020.subsystems;

import static com.team364.frc2020.RobotContainer.THE_SWITCH;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.json.*;
import org.json.simple.parser.JSONParser;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    public static StringBuilder reCalibrate = new StringBuilder();


    private JSONObject targetFile;
    private Map<String, Double> file = Map.of("name", 1.0, "extra", 2.0);

    public Vision() {
        register();
        targetFile = new JSONObject(file);

        // fill in other heights
        visionHeights.put(1.0, "height1");

    }

    public void printJson() {
        System.out.println(targetFile.toString());
    }

    public void putJson(String string, Object value) {
        try {
            targetFile.put(string, value);
        } catch (Exception e) {
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

    public void findClosestTargets(double height) {
        visionHeights.forEach((target, name) -> {
            double holder = Math.abs(height - target);
            if (holder < closestTarget[0] || holder < closestTarget[1]) {
                if (Math.abs(holder - closestTarget[0]) < Math.abs(holder - closestTarget[1])) {
                    closestTarget[1] = height;
                } else {
                    closestTarget[0] = height;
                }
            }
        });
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
            JSONplots[0] = (double) targetFile.get("name");
            JSONplots[1] = (double) targetFile.get("extra");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            // targetFile.get(visionHeights.get(closestTarget[0]));
            // targetFile.get(visionHeights.get(closestTarget[0]));



       return JSONplots;
   }




   public double limeX(){
       return 1.0;
   }
   public double limeY(){
       return 1.0;
   }

   public void reCalMap(){
    Iterator<String> keys = targetFile.keys();
    try{
        reCalibrate.append("{");
        for(int i = 0; i < targetFile.length(); i++){
            if(keys.hasNext()){
                String hold = keys.next();
                //TODO: check to the next line thing
                reCalibrate.append("\n\"" + hold + "\"" + ", " + (double)targetFile.get(hold));
            }   
        }
        reCalibrate.append("}");
    }catch(Exception e){}
   }

   @Override
   public void periodic(){
       if(THE_SWITCH){

       }   
      
       setTarget();
       reCalMap();
       System.out.println(reCalibrate);
   }


}

