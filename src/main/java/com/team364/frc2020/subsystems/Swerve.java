package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.team1323.lib.util.Util;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.Subsystem;
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
    public SwerveDriveOdometry m_odometry;
    public SwerveDriveKinematics m_kinematics;

    public Swerve() {

        mSwerveModules = new SwerveMod[] {
            new SwerveMod(0,
                new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new TalonFX(FLANGLE),
                new CANCoder(FLCAN),
                new TalonFX(FLDRIVE),
                MOD1DRIVEINVERT,
                false,
                CAN1CONFIG),
            new SwerveMod(1,
                new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new TalonFX(FRANGLE),
                new CANCoder(FRCAN),
                new TalonFX(FRDRIVE),
                MOD2DRIVEINVERT, 
                false,
                CAN2CONFIG),
            new SwerveMod(2,
                new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                new TalonFX(BLANGLE),
                new CANCoder(BLCAN),
                new TalonFX(BLDRIVE),
                MOD3DRIVEINVERT,
                false,
                CAN3CONFIG),
            new SwerveMod(3,
                new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                new TalonFX(BRANGLE),
                new CANCoder(BRCAN),
                new TalonFX(BRDRIVE),
                MOD3DRIVEINVERT,
                false,
                CAN4CONFIG)
        };
        
        modules = Arrays.asList(mSwerveModules[1], mSwerveModules[2], mSwerveModules[3], mSwerveModules[4]);

        m_kinematics = new SwerveDriveKinematics(
            new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0), 
            new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
            new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0), 
            new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0) );
        m_odometry = new SwerveDriveOdometry(m_kinematics, getAngle());
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
            if(Util.shouldReverse(mod.periodicIO.velocityPosition, mod.getCANCoderAngle())){
                mod.setAngle(mod.periodicIO.velocityPosition += 180);
                mod.setSpeed(mod.periodicIO.velocitySpeed * -1);
            }else{
                mod.setAngle(mod.periodicIO.velocityPosition);
                mod.setSpeed(mod.periodicIO.velocitySpeed);
            }
        }
        openLoopOutputs();
    }
    
    @Override
    public void periodic(){

    }
    public Rotation2d getAngle(){
        //TODO: *CB* get gyro in here
        return new Rotation2d(2);
    }
    public void updateOdometry() {
        
        m_odometry.update(
            getAngle(),
            mSwerveModules[1].getState(),
            mSwerveModules[2].getState(),
            mSwerveModules[3].getState(),
            mSwerveModules[4].getState()
        );
    }
    
}
