package com.team364.frc2020;

import static com.team364.frc2020.RobotMap.SWERVEJSON;
import static com.team364.frc2020.RobotMap.TARGETJSON;
import static com.team364.frc2020.States.configState;

import java.util.Map;

import com.team364.frc2020.States.ConfigStates;
import com.team364.frc2020.subsystems.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class Configuration {
    private Vision s_Vision;
    private Swerve s_Swerve;

    private static ShuffleboardTab configTab = Shuffleboard.getTab("Configuration");
        private static ShuffleboardLayout config = configTab.getLayout("States", BuiltInLayouts.kList).withSize(2, 2).withPosition(0, 0).withProperties(Map.of("Label position", "HIDDEN"));
        private static ShuffleboardLayout Swerve = configTab.getLayout("States", BuiltInLayouts.kList).withSize(2, 2).withPosition(0, 0).withProperties(Map.of("Label position", "HIDDEN"));
        private static ShuffleboardLayout Target = configTab.getLayout("States", BuiltInLayouts.kList).withSize(2, 2).withPosition(0, 0).withProperties(Map.of("Label position", "HIDDEN"));
            private static NetworkTableEntry Shooter_Velocity = Target.add("Shooter Velocity", 0.0).withWidget(BuiltInWidgets.kTextView).getEntry();
            private static NetworkTableEntry Hood_Angle = Target.add("Hood Angle", 0.0).withWidget(BuiltInWidgets.kTextView).getEntry();

    public static JsonSimplifier<Double, Double[]> TargetJson = new JsonSimplifier<>(TARGETJSON);
    public static JsonSimplifier<Integer, Double> SwerveJson = new JsonSimplifier<>(SWERVEJSON);

    public static double ShooterVelocity;

    public Configuration(Vision s_Vision, Swerve s_Swerve){
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;

        Shuffleboard.selectTab("Configuration");
        config.add("Target State", new InstantCommand(() -> changeState(ConfigStates.TARGET))).withWidget(BuiltInWidgets.kToggleButton);
        config.add("Swerve State", new InstantCommand(() -> changeState(ConfigStates.SWERVE))).withWidget(BuiltInWidgets.kToggleButton);
        config.add("Match State", new InstantCommand(() -> changeState(ConfigStates.MATCH))).withWidget(BuiltInWidgets.kToggleButton);

            Target.add("Add Point", new InstantCommand(() -> configTarget(getShooterVel(), getHoodAng())));
            Target.add("Calibrate", new InstantCommand(() -> configTargetJson()));

            Swerve.add("Calibrate", new InstantCommand(() -> configSwerve()));

    }

    private void configTarget(double shooterVel, double hoodAng){
        if(configState == ConfigStates.TARGET){
            Double[] value = {shooterVel, hoodAng};
            TargetJson.writeElement(String.valueOf(s_Vision.limeY()), value);
        }
    }

    private void configTargetJson(){
        if(configState == ConfigStates.TARGET){
            TargetJson.writeJson(TargetJson.getName());
        }
    }

    public double getShooterVel(){
        return Shooter_Velocity.getDouble(0.0);
    }
    public double getHoodAng(){
        return Hood_Angle.getDouble(0.0);
    }

    private void configSwerve(){
        if(configState == ConfigStates.SWERVE){
            for(SwerveMod mod : s_Swerve.modules){
                SwerveJson.replaceElement(String.valueOf(mod.moduleNumber), mod.getCANCoderAngle());
            }
            SwerveJson.writeJson(SwerveJson.getName());
        }
    }
    public static void changeState(ConfigStates state){
        configState = state;
    }
}
