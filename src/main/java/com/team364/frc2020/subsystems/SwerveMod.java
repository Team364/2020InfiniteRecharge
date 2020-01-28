package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.team1323.lib.util.Util;
import com.team1323.loops.Loop;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team364.frc2020.misc.math.Vector2;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class SwerveMod implements Subsystem {
    public final int moduleNumber;

    private final CANCoder localTurn;
    private final TalonFX mAngleMotor;
    private final TalonFX mDriveMotor;
    private Vector2 modulePosition;
    private CANCoderConfiguration localCANConfig;

    public double smartAngle;
    public Vector2 velocity;
    public double currentAngle;

    protected PeriodicIO periodicIO = new PeriodicIO();

    public SupplyCurrentLimitConfiguration swerveSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);


    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonFX angleMotor, CANCoder turnEncoder, TalonFX driveMotor,
            boolean invertDrive, boolean invertSensorPhase, double offset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        currentAngle = 0;
        localTurn = turnEncoder;
        localCANConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        localCANConfig.magnetOffsetDegrees = offset;

        //Configure CANCoder
        localTurn.configAllSettings(localCANConfig);

        // Configure Angle Motor
        mAngleMotor.configFactoryDefault();
        mAngleMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, SLOTIDX, SWERVETIMEOUT);
        mAngleMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        mAngleMotor.setSensorPhase(invertSensorPhase);
        mAngleMotor.config_kP(SLOTIDX, ANGLEP, SWERVETIMEOUT);
        mAngleMotor.config_kI(SLOTIDX, ANGLEI, SWERVETIMEOUT);
        mAngleMotor.config_kD(SLOTIDX, ANGLED, SWERVETIMEOUT);
        mAngleMotor.setNeutralMode(NeutralMode.Brake);
        mAngleMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 10);
        mAngleMotor.configMotionAcceleration((int) (kSwerveRotationMaxSpeed * 12.5), 10);
        mAngleMotor.configMotionCruiseVelocity((int) (kSwerveRotationMaxSpeed), 10);
        mAngleMotor.set(ControlMode.Position, angleMotor.getSelectedSensorPosition(0));

        // Configure Drive Motor
        mDriveMotor.setNeutralMode(NeutralMode.Brake);
        mDriveMotor.setInverted(invertDrive);


        // Setup Current Limiting
        mAngleMotor.configSupplyCurrentLimit(swerveSupplyLimit, 20);
        mDriveMotor.configSupplyCurrentLimit(swerveSupplyLimit, 20);
    }

    public void openLoopOutput(){
        mDriveMotor.set(ControlMode.PercentOutput, periodicIO.speedDemand);
        mAngleMotor.set(ControlMode.MotionMagic, periodicIO.positionDemand);
    }

    public void setVectorVelocity(Vector2 velocity, boolean speed, double rotation) {
        this.velocity = velocity;
        periodicIO.setVelocityPosition(velocity.getAngle().toDegrees());
        if (speed) {
            periodicIO.setVelocitySpeed(velocity.length);
        } else {
            periodicIO.setVelocitySpeed(0);
        }
    }
    
    public synchronized void setAngle(double fTargetAngle) {
        //TODO: *CB* check the offset feature, add it or substract it, see getModuleAngle, also check for CANCoder angle
        double newAngle = Util.placeInAppropriate0To360Scope(getCANCoderAngle(), fTargetAngle);
        double setpoint = toCounts(newAngle);
        periodicIO.setDemandPosition(setpoint);
    }

    public synchronized void setSpeed(double fSpeed) {
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
        return new SwerveModuleState(mDriveMotor.getSelectedSensorPosition(), new Rotation2d(localTurn.getPosition()));
    }
    
    public Vector2 getModulePosition() {
        return modulePosition;
    }

    public double getCANCoderAngle(){
        return localTurn.getPosition();
    }


}
