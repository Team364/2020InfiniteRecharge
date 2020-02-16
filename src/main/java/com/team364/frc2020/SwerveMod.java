package com.team364.frc2020;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.team364.frc2020.misc.math.Vector2;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SwerveMod implements Subsystem {
    public final int moduleNumber;
    
    private final CANCoder localTurn;
    private final TalonFX mAngleMotor;
    private final TalonFX mDriveMotor;
    private Vector2 modulePosition;
    private CANCoderConfiguration localCANConfig = new CANCoderConfiguration();

    public double smartAngle;
    public Vector2 velocity;
    public double currentAngle;
    private boolean driveInvert = false;

    public PeriodicIO periodicIO = new PeriodicIO();

    public SupplyCurrentLimitConfiguration swerveAngleSupplyLimit = new SupplyCurrentLimitConfiguration(ANGLEENABLECURRENTLIMIT, ANGLECONTINUOUSCURRENTLIMIT, ANGLEPEAKCURRENT, ANGLEPEAKCURRENTDURATION);
    public SupplyCurrentLimitConfiguration swerveDriveSupplyLimit = new SupplyCurrentLimitConfiguration(DRIVEENABLECURRENTLIMIT, DRIVECONTINUOUSCURRENTLIMIT, DRIVEPEAKCURRENT, DRIVEPEAKCURRENTDURATION);

    private final PIDController m_speedPIDController = new PIDController(1, 0, 0);
    private final ProfiledPIDController m_anglePIDController = new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(SWERVEMAX_ANGLEVELOCITY, SWERVEMAX_ANGLEACCELERATION));

    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonFX angleMotor, CANCoder turnEncoder, TalonFX driveMotor,
            boolean invertDrive, boolean invertAngle, boolean invertAnglePhase, double offset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        currentAngle = 0;
        localTurn = turnEncoder;
        localCANConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        localCANConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        localCANConfig.magnetOffsetDegrees = offset;

        //Configure CANCoder
        localTurn.configAllSettings(localCANConfig, 30);

        // Configure Angle Motor
        mAngleMotor.configFactoryDefault();
        mAngleMotor.configRemoteFeedbackFilter(localTurn, 0, SWERVETIMEOUT);
        mAngleMotor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, SLOTIDX, SWERVETIMEOUT);
        mAngleMotor.selectProfileSlot(SLOTIDX, PIDLoopIdx);
        mAngleMotor.setInverted(invertAngle);
        mAngleMotor.setSensorPhase(invertAnglePhase);
        
        mAngleMotor.config_kP(SLOTIDX, ANGLEP, SWERVETIMEOUT);
        mAngleMotor.config_kI(SLOTIDX, ANGLEI, SWERVETIMEOUT);
        mAngleMotor.config_kD(SLOTIDX, ANGLED, SWERVETIMEOUT);
       
        mAngleMotor.setNeutralMode(NeutralMode.Coast);
        mAngleMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 10);
        

        // Configure Drive Motor
        mDriveMotor.configFactoryDefault();
        mDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, SLOTIDX, SWERVETIMEOUT);
        mDriveMotor.selectProfileSlot(SLOTIDX, PIDLoopIdx);
                
        mDriveMotor.config_kP(SLOTIDX, 1, SWERVETIMEOUT);
        mDriveMotor.config_kI(SLOTIDX, 0, SWERVETIMEOUT);
        mDriveMotor.config_kD(SLOTIDX, 0, SWERVETIMEOUT);
       
        mDriveMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 10);

        mDriveMotor.setNeutralMode(NeutralMode.Brake);
        mDriveMotor.setInverted(invertDrive);


        // Setup Current Limiting
        mAngleMotor.configSupplyCurrentLimit(swerveAngleSupplyLimit, 20);
        mDriveMotor.configSupplyCurrentLimit(swerveDriveSupplyLimit, 20);
    }

    public void openLoopOutput(boolean profiling){
        if(!profiling){
            mDriveMotor.set(ControlMode.PercentOutput, periodicIO.speedDemand);
            mAngleMotor.set(ControlMode.Position, periodicIO.positionDemand);    
        }else if(profiling){
            SmartDashboard.putNumber("speed Demand", periodicIO.speedDemand);
            SmartDashboard.putNumber("angle Demand", periodicIO.positionDemand);

            //mDriveMotor.set(ControlMode.Velocity, periodicIO.speedDemand);
            //mAngleMotor.set(ControlMode.Position, periodicIO.positionDemand);    
        }

    }

    public void setVectorVelocity(Vector2 velocity, boolean speed) {
        this.velocity = velocity;
        periodicIO.setVelocityPosition(velocity.getAngle().toDegrees());
        if (speed) {
            periodicIO.setVelocitySpeed(velocity.length);
        } else {
            periodicIO.setVelocitySpeed(0);
        }
    }
    

    public synchronized void setAngle(double targetAngle) {
        targetAngle = modulate360(-targetAngle);
        double currentAngle = toDegrees(mAngleMotor.getSelectedSensorPosition());
        double currentAngleMod = modulate360(currentAngle);
        if (currentAngleMod < 0) currentAngleMod += 360;

        double delta = currentAngleMod - targetAngle;
        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }

        delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if(delta > 90){
                targetAngle += 180;
            }
            else if(delta < -90){
                targetAngle -= 180;
            }            
            driveInvert = true;
        } else{
            driveInvert = false;
        }

        
        targetAngle += currentAngle - currentAngleMod;        
        targetAngle = toCounts(targetAngle);
        periodicIO.setDemandPosition(targetAngle);

    }


    public synchronized void setSpeed(double fSpeed) {
        if(driveInvert) fSpeed *= -1;
        periodicIO.setDemandSpeed(fSpeed);
    }

    public static class PeriodicIO {

        //Intermediates
        public double velocitySpeed;
        public double velocityPosition;

        public void setVelocityPosition(double velocityPosition) {
            this.velocityPosition = velocityPosition;
        }

        public void setVelocitySpeed(double velocitySpeed) {
            this.velocitySpeed = velocitySpeed;
        }

        // Outputs
        public double speedDemand;
        public double positionDemand;

        public void setDemandPosition(double positionDemand) {
            this.positionDemand = positionDemand;
        }

        public void setDemandSpeed(double speedDemand) {
            this.speedDemand = speedDemand;
        }
    }


    public SwerveModuleState getState(){
        double holdTurn = modulate360(localTurn.getPosition());
        if(holdTurn < 0) holdTurn *= -1;
        return new SwerveModuleState(mDriveMotor.getSelectedSensorVelocity(), new Rotation2d(holdTurn));
    }
    
    public Vector2 getModulePosition() {
        return modulePosition;
    }

    public double getCANCoderAngle(){
        double convert = modulate360(toDegrees(mAngleMotor.getSelectedSensorPosition()));
        if(convert < 0) convert += 360;
        return convert;
    }
    
    public double getDriveSpeed(){
        return mDriveMotor.getSelectedSensorVelocity();
    }

    public TalonFX getAngleMotor(){
        return mAngleMotor;
    }
    public void toFusionSwerve(SwerveModuleState state){
        final double speedOutput = m_speedPIDController.calculate(
            getDriveSpeed(), state.speedMetersPerSecond
        );

        setAngle(state.angle.getDegrees());
        setSpeed(state.speedMetersPerSecond);
        openLoopOutput(true);
    }
}
