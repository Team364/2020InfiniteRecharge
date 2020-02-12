package com.team364.frc2020;

import static com.team364.frc2020.RobotMap.SWERVEJSON;
import static com.team364.frc2020.RobotMap.TARGETJSON;
import static com.team364.frc2020.States.configState;

import java.io.FileWriter;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.DoubleArraySerializer;
import com.team364.frc2020.States.ConfigStates;
import com.team364.frc2020.subsystems.*;

import org.json.JSONObject;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class Configuration {
    private Vision s_Vision;
    private Swerve s_Swerve;
    private int cycles = 0;
    private static ShuffleboardTab configTab = Shuffleboard.getTab("Configuration");
        private static ShuffleboardLayout config = configTab.getLayout("NewStates", BuiltInLayouts.kList).withSize(3, 3).withPosition(0, 0);
        private static ShuffleboardLayout Swerve = configTab.getLayout("Swerve", BuiltInLayouts.kList).withSize(2, 2).withPosition(4, 0);
        private static ShuffleboardLayout Target = configTab.getLayout("Targets", BuiltInLayouts.kList).withSize(2, 3).withPosition(6, 0);
            private static NetworkTableEntry Shooter_Velocity;
            private static NetworkTableEntry Hood_Angle;
    private NetworkTableEntry target;
    private NetworkTableEntry swerve;
    private NetworkTableEntry match;
    private NetworkTableEntry addPoint;
    private NetworkTableEntry calibrateTarget;
    private NetworkTableEntry calibrateSwerve;

    public static JsonSimplifier<Double, List<Double>> TargetJson = new JsonSimplifier<>(TARGETJSON);
    public static JsonSimplifier<Integer, Double> SwerveJson = new JsonSimplifier<>(SWERVEJSON);

    public static double ShooterVelocity = 0;
    public static double HoodAngle = 0;

    Map<Integer, List<Double>> testing = new HashMap<>(); ;

    public Configuration(Vision s_Vision, Swerve s_Swerve){
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;

        //Shuffleboard.selectTab("Configuration");
        target = config.add("Target State", true).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        swerve = config.add("Swerve State", true).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        match = config.add("Match State", true).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        
        Shooter_Velocity = Target.add("Shooter Velocity", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(1, 5).getEntry();
        Hood_Angle = Target.add("Hood Angle", 0.0).withWidget(BuiltInWidgets.kTextView).getEntry();
        addPoint = Target.add("Add Point", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        calibrateTarget = Target.add("Target Calibrate", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

        calibrateSwerve = Swerve.add("Swerve Calibrate", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        List<Double> value = new ArrayList<>(Arrays.asList(1.0, 2.0));
        testing.put(1, value);
        SmartDashboard.putString("cheeeeck me out", testing.toString());
    }

    private void configTarget(double shooterVel, double hoodAng){
        if(configState == ConfigStates.TARGET){
            List<Double> value = new ArrayList<>(Arrays.asList(shooterVel, hoodAng));
            TargetJson.writeElement(0.0, value);
        }
        SmartDashboard.putNumber("Cycle test", cycles);
        cycles++;
    }

    private void configTargetJson(){
        if(configState == ConfigStates.TARGET){
            TargetJson.writeJson(false);
        }
    }

    public void setShooterVel(){
        ShooterVelocity = Shooter_Velocity.getDouble(0);
    }
    public void setHoodAng(){
        HoodAngle = Hood_Angle.getDouble(0);

    }

    private void configSwerve(){
        if(configState == ConfigStates.SWERVE){
            SwerveJson.resetJson();
            for(SwerveMod mod : s_Swerve.modules){
                SwerveJson.writeElement(mod.moduleNumber, mod.getCANCoderAngle());
            }
            SwerveJson.writeJson(false);
        }
    }
    public void changeState(ConfigStates state){
        configState = state;
    }

    public void doTheConfigurationShuffle(){
        if(match.getBoolean(false)){
            target.setValue(false);
            swerve.setValue(false);
            changeState(ConfigStates.MATCH);
        }
        if(target.getBoolean(false)){
            if(configState != ConfigStates.TARGET){
                match.setValue(false);
                swerve.setValue(false);
            }
            changeState(ConfigStates.TARGET);
        }
        if(swerve.getBoolean(false)){
            if(configState != ConfigStates.SWERVE){
                target.setValue(false);
                match.setValue(false);
            }
            changeState(ConfigStates.SWERVE);
        }

        //actual calibration buttons--------------------------------
        if(addPoint.getBoolean(false)){
            addPoint.setValue(false);
            configTarget(ShooterVelocity, HoodAngle);
        }
        if(calibrateTarget.getBoolean(false)){
            calibrateTarget.setValue(false);
            configTargetJson();
        }
        if(calibrateSwerve.getBoolean(false)){
            calibrateSwerve.setValue(false);
            configSwerve();
        }
        setShooterVel();
        setHoodAng();
        SmartDashboard.putNumber("check me out", new Double[] {1.0, 2.0}[0]);

    }
}
