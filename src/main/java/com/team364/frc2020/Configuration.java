package com.team364.frc2020;

import static com.team364.frc2020.RobotMap.SWERVEJSON;
import static com.team364.frc2020.RobotMap.TARGETJSON;
import static com.team364.frc2020.States.configState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.team364.frc2020.States.ConfigStates;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Configuration {
    private Swerve s_Swerve;
    private int cycles = 0;
    private ShuffleboardTab configTab = Shuffleboard.getTab("Configuration");
        private ShuffleboardLayout config = configTab.getLayout("NewStates", BuiltInLayouts.kList).withSize(3, 3).withPosition(0, 0);
        private ShuffleboardLayout Swerve = configTab.getLayout("Swerve", BuiltInLayouts.kList).withSize(2, 2).withPosition(4, 0);
        private ShuffleboardLayout Target = configTab.getLayout("Targets", BuiltInLayouts.kList).withSize(2, 3).withPosition(6, 0);
            private NetworkTableEntry Shooter_Velocity;
            private NetworkTableEntry Hood_Angle;
    private NetworkTableEntry target;
    private NetworkTableEntry swerve;
    private NetworkTableEntry match;
    private NetworkTableEntry addPoint;
    private NetworkTableEntry calibrateTarget;
    private NetworkTableEntry calibrateSwerve;

    public static JsonSimplifier<Object, List<Double>> TargetJson = new JsonSimplifier<>(TARGETJSON);
    public static JsonSimplifier<Object, Double> SwerveJson = new JsonSimplifier<>(SWERVEJSON);

    public double ShooterVelocity = 0;
    public double HoodAngle = 0;

    public Configuration(Vision s_Vision, Swerve s_Swerve){
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

    }
}
