package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;
import static com.team364.frc2020.States.*;
import static com.team364.frc2020.Configuration.SwerveJson;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.team364.frc2020.misc.math.Rotation2;
import com.team364.frc2020.misc.math.Vector2;
import com.team364.frc2020.SwerveMod;

public class Swerve extends SubsystemBase {
    private static Swerve Instance = null;
    public PigeonIMU pigeon;

    /*
     * 1 is Front Left 2 is Front Right 3 is Back Left 4 is Back Right
     */
    private SwerveMod[] mSwerveModules;
    public List<SwerveMod> modules;
    public SwerveDriveOdometry m_odometry;
    public SwerveDriveKinematics m_kinematics;

    private ShuffleboardTab swervePID = Shuffleboard.getTab("Configuration");
        private NetworkTableEntry swervekP;
        private NetworkTableEntry swervekI;
        private NetworkTableEntry swervekD;

    public Swerve() {
        configOffsets();
        pigeon = new PigeonIMU(new TalonSRX(10));
        zeroGyro();
        mSwerveModules = new SwerveMod[] {
                new SwerveMod(1, new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0), new TalonFX(FLANGLE),
                        new CANCoder(FLCAN), new TalonFX(FLDRIVE), MOD1DRIVEINVERT, MOD1ANGLEINVERT, MOD1ANGLEPHASE,
                        MOD1OFFSET),
                new SwerveMod(2, new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0), new TalonFX(FRANGLE),
                        new CANCoder(FRCAN), new TalonFX(FRDRIVE), MOD2DRIVEINVERT, MOD1ANGLEINVERT, MOD1ANGLEPHASE,
                        MOD2OFFSET),
                new SwerveMod(3, new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0), new TalonFX(BLANGLE),
                        new CANCoder(BLCAN), new TalonFX(BLDRIVE), MOD3DRIVEINVERT, MOD1ANGLEINVERT, MOD1ANGLEPHASE,
                        MOD3OFFSET),
                new SwerveMod(4, new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0), new TalonFX(BRANGLE),
                        new CANCoder(BRCAN), new TalonFX(BRDRIVE), MOD4DRIVEINVERT, MOD1ANGLEINVERT, MOD1ANGLEPHASE,
                        MOD4OFFSET) };

        modules = Arrays.asList(mSwerveModules[0], mSwerveModules[1], mSwerveModules[2], mSwerveModules[3]);
        m_kinematics = new SwerveDriveKinematics(new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0));
        m_odometry = new SwerveDriveOdometry(m_kinematics, getAngle());
                
        swervekP = swervePID.add("swerve kP", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(1, 5).getEntry();
        swervekI = swervePID.add("swerve kI", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(2, 5).getEntry();
        swervekD = swervePID.add("swerve kD", 0.0).withWidget(BuiltInWidgets.kTextView).withPosition(3, 5).getEntry();
        resetOdometry();
    }

    public synchronized static Swerve getInstance() {
        if (Instance == null) {
            Instance = new Swerve();
        }
        return Instance;
    }

    public void configOffsets() {
        Map<Object, Double> offsetsMap = SwerveJson.getMap();
        MOD1OFFSET = offsetsMap.get("1");
        MOD2OFFSET = offsetsMap.get("2");
        MOD3OFFSET = offsetsMap.get("3");
        MOD4OFFSET = offsetsMap.get("4");
    }

    public SwerveMod[] getSwerveModules() {
        return mSwerveModules;
    }

    public void holonomicDrive(Vector2 translation, double rotation, boolean speed) {
        modules.forEach(mod -> {
            Vector2 newTranslation = translation.rotateBy(Rotation2.fromDegrees(getYaw()));
            Vector2 velocity = mod.getModulePosition().normal().scale(deadband(rotation, STICKDEADBAND)).add(newTranslation);
            mod.setVectorVelocity(velocity, speed);
        });
    }

    public void updateKinematics(boolean profiling) {
        modules.forEach(mod -> {
            mod.runOutputs(profiling);
        });
    }

    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(getYaw());
    }

    public double getYaw() {
        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        return ypr[0];
    }

    double loops = 0;
    public void zeroGyro() {
        SmartDashboard.putNumber("loops", loops++);
        pigeon.setYaw(0);
    }

    public void resetOdometry(){
        m_odometry.resetPosition(new Pose2d(), new Rotation2d(getYaw()));
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates The desired SwerveMod states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.normalizeWheelSpeeds(desiredStates,
                                                kMaxSpeedMetersPerSecond);
        modules.forEach(mod -> {
            mod.toFusionSwerve(desiredStates[mod.moduleNumber - 1]);
        });
        updateKinematics(true);
    }

    public void updateOdometry() {
        m_odometry.update(getAngle(), mSwerveModules[0].getState(), mSwerveModules[1].getState(),
                mSwerveModules[2].getState(), mSwerveModules[3].getState());
    }

    public SwerveDriveKinematics getKinematics() {
        return m_kinematics;
    }

    public SwerveDriveOdometry getOdometry() {
        return m_odometry;
    }
    public void setProfilingStates(SwerveModuleState[] moduleStates){
        SwerveDriveKinematics.normalizeWheelSpeeds(moduleStates, SWERVEMAXSPEED);
        modules.forEach(mod -> {
            mod.toFusionSwerve(moduleStates[mod.moduleNumber - 1]);
        });
    }

	public void stop() {
        modules.forEach(mod -> {
            mod.stop();
        });
    }

    public void resetDriveDistance(){
        modules.forEach(mod -> {
            mod.resetDriveDistance();
        });
    }

    private double additiveHold;
    public double getDriveDistance(){
        additiveHold = 0;
        modules.forEach(mod -> {
            additiveHold += mod.getDriveDistance();
        });
        return additiveHold / 4;
    }

    @Override
    public void periodic() {
        if(configState == ConfigStates.SWERVE){
            modules.forEach(mod -> {
            mod.getAngleMotor().config_kP(0, swervekP.getDouble(2.0));
            mod.getAngleMotor().config_kI(0, swervekI.getDouble(0.0));
            mod.getAngleMotor().config_kD(0, swervekD.getDouble(200.0));
        });  
        }
        updateOdometry();
        SmartDashboard.putNumber("pose x", m_odometry.getPoseMeters().getTranslation().getX());
        SmartDashboard.putNumber("pose y", m_odometry.getPoseMeters().getTranslation().getY());


    }

}
