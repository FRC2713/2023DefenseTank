package frc.robot.tankDriveIO;

import org.littletonrobotics.junction.AutoLog;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public interface TankDriveIO {

 @AutoLog
 public static class TankInputs {
    public RelativeEncoder leftEncoder;
    public RelativeEncoder rightEncoder;
    public Rotation2d heading;
    public double degrees = 0;
    public double turnRate = 0;
    public Pose2d pose;
    public double leftVoltage;
    public double rightVoltage;
    public double leftSpeed = 0;
    public double rightSpeed = 0;
 }

 public void updateInputs(TankInputs tank);

 public void setVoltage(double voltsLeft, double voltsRight);
}
