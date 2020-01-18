package com.team364.frc2020.subsystems;

import static com.team364.frc2020.Conversions.*;
import static com.team364.frc2020.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.team1323.lib.util.Util;
import com.team1323.loops.Loop;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team364.frc2020.misc.math.Vector2;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class SwerveMod implements Subsystem {
    private double lastTargetAngle = 0;
    public final int moduleNumber;

    public final int mZeroOffset;

    private final TalonFX mAngleMotor;
    private final TalonFX mDriveMotor;
    private Vector2 modulePosition;
    private boolean driveInverted = false;

    public double targetAngle;
    public double targetSpeed;
    public double smartAngle;
    public Vector2 velocity;
    public double currentAngle;

    private PeriodicIO periodicIO = new PeriodicIO();

    public SupplyCurrentLimitConfiguration swerveSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 40, 0.1);


    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonFX angleMotor, TalonFX driveMotor,
            boolean invertDrive, boolean invertSensorPhase, int zeroOffset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        targetAngle = 0;
        targetSpeed = 0;
        currentAngle = 0;

        // Configure Angle Motor
        angleMotor.configFactoryDefault();
        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, SLOTIDX, SWERVETIMEOUT);
        angleMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        angleMotor.setSensorPhase(invertSensorPhase);
        angleMotor.config_kP(SLOTIDX, ANGLEP, SWERVETIMEOUT);
        angleMotor.config_kI(SLOTIDX, ANGLEI, SWERVETIMEOUT);
        angleMotor.config_kD(SLOTIDX, ANGLED, SWERVETIMEOUT);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 10);
        angleMotor.configMotionAcceleration((int) (kSwerveRotationMaxSpeed * 12.5), 10);
        angleMotor.configMotionCruiseVelocity((int) (kSwerveRotationMaxSpeed), 10);
        angleMotor.set(ControlMode.Position, angleMotor.getSelectedSensorPosition(0));

        // Configure Drive Motor
        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setInverted(invertDrive);


        // Setup Current Limiting
        angleMotor.configSupplyCurrentLimit(swerveSupplyLimit, 20);
        driveMotor.configSupplyCurrentLimit(swerveSupplyLimit, 20);

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

    public Vector2 getModulePosition() {
        return modulePosition;
    }
    public double getModuleAngle() {
        return getRawAngle() - mZeroOffset;
    }
    public double getRawAngle(){
        return mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
    }

    public synchronized void setAngle(double fTargetAngle) {
        //TODO: *CB* check the offset feature, add it or substract it, see getModuleAngle
        double newAngle = Util.placeInAppropriate0To360Scope(getRawAngle(), fTargetAngle + mZeroOffset);
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

}
