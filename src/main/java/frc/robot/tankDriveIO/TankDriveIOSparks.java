package frc.robot.tankDriveIO;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants;

public class TankDriveIOSparks implements TankDriveIO{

    ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    private CANSparkMax left1 =
    new CANSparkMax(
        Constants.RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
private CANSparkMax right1 =
    new CANSparkMax(
        Constants.RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
private CANSparkMax left2 =
    new CANSparkMax(
        Constants.RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
private CANSparkMax right2 =
    new CANSparkMax(
        Constants.RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);


 public TankDriveIOSparks(){
            left1.restoreFactoryDefaults();
            right1.restoreFactoryDefaults();
            left2.restoreFactoryDefaults();
            right2.restoreFactoryDefaults();
        
            left2.follow(left1);
            right2.follow(right1);
        
            left1.setInverted(true);
            right1.setInverted(false);
        
            left1.setSmartCurrentLimit(Constants.DriveConstants.currentLimit);
            right1.setSmartCurrentLimit(Constants.DriveConstants.currentLimit);
        
            setAllCoast();
        
            left1.getEncoder().setPositionConversionFactor(Constants.DriveConstants.distPerPulse);
            right1.getEncoder().setPositionConversionFactor(Constants.DriveConstants.distPerPulse);
            left1.getEncoder().setVelocityConversionFactor(Constants.DriveConstants.distPerPulse / 60);
            right1.getEncoder().setVelocityConversionFactor(Constants.DriveConstants.distPerPulse / 60);
            }
        
          public void setHalfBrakeHalfCoast() {
            left1.setIdleMode(IdleMode.kBrake);
            left2.setIdleMode(IdleMode.kCoast);
            right1.setIdleMode(IdleMode.kBrake);
            right2.setIdleMode(IdleMode.kCoast);
          }
        
          public void setAllCoast() {
            left1.setIdleMode(IdleMode.kCoast);
            left2.setIdleMode(IdleMode.kCoast);
            right1.setIdleMode(IdleMode.kCoast);
            right2.setIdleMode(IdleMode.kCoast);
          }

    @Override
    public void updateInputs(TankInputs tank) {       
        tank.leftEncoderDist = left1.getEncoder().getPosition();
        tank.rightEncoderDist = left1.getEncoder().getPosition();
        tank.headingDegrees = gyro.getRotation2d().getDegrees();
        tank.degrees = left1.getEncoder().getPosition();
        tank.rightVoltage = right1.getOutputCurrent();
        tank.leftVoltage = left1.getOutputCurrent();
    }

    @Override
    public void setVoltage(double voltsLeft, double voltsRight) {
        left1.setVoltage(voltsLeft);  
        left2.setVoltage(voltsLeft);      
        right1.setVoltage(voltsRight);
        right2.setVoltage(voltsRight);        
    }

}
