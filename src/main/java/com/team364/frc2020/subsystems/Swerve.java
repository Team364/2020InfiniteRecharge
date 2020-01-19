package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.team1323.lib.util.Util;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.team364.frc2020.misc.math.Vector2;

public class Swerve extends SubsystemBase {

    private static Swerve Instance = null;
	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
    private SwerveMod[] mSwerveModules;
    private List<SwerveMod> modules;

    public Swerve() {

            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonFX(FRANGLE),
                            new TalonFX(FRDRIVE),
                            MOD0DRIVEINVERT, 
                            false,
                            MOD0OFFSET),
                    new SwerveMod(1,
                            new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonFX(FLANGLE),
                            new TalonFX(FLDRIVE),
                            MOD1DRIVEINVERT,
                            false,
                            MOD1OFFSET),
                    new SwerveMod(2,
                            new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonFX(BLANGLE),
                            new TalonFX(BLDRIVE),
                            MOD2DRIVEINVERT,
                            false,
                            MOD2OFFSET),
                    new SwerveMod(3,
                            new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonFX(BRANGLE),
                            new TalonFX(BRDRIVE),
                            MOD3DRIVEINVERT,
                            false,
                            MOD3OFFSET)
            };
         
            modules = Arrays.asList(mSwerveModules[0], mSwerveModules[1], mSwerveModules[2], mSwerveModules[3]);

    } 

    public synchronized static Swerve getInstance() {
        if (Instance == null) {
          Instance = new Swerve();
        }
        return Instance;
      }

    public SwerveMod[] getSwerveModules() {
        return mSwerveModules;
    }

    public void openLoopOutputs(){
        modules.forEach(mod -> mod.openLoopOutput());
    }

    public void holonomicDrive(Vector2 translation, double rotation, boolean speed) {
        Vector2 velocity;
        for(SwerveMod mod : getSwerveModules()){
            Vector2 newTranslation = null;
            //newTranslation = translation.rotateBy(Rotation2.fromDegrees(getGyro()));
            newTranslation = translation;
            velocity = mod.getModulePosition().normal().scale(deadband(rotation)).add(newTranslation);
            mod.setVectorVelocity(velocity, speed, rotation);
        }        
    }
    public void updateKinematics(){
        for (SwerveMod mod : getSwerveModules()){
            if(Util.shouldReverse(mod.targetAngle, mod.getModuleAngle())){
                mod.setAngle(mod.targetAngle += 180);
                mod.setSpeed(mod.targetSpeed * -1);
            }else{
                mod.setAngle(mod.targetAngle);
                mod.setSpeed(mod.targetSpeed);
            }
        }
        openLoopOutputs();
    }

    
}
