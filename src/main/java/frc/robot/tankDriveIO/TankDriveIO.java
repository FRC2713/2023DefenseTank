package frc.robot.tankDriveIO;

import org.littletonrobotics.junction.AutoLog;

public interface TankDriveIO {

 @AutoLog
 public static class TankInputs {
    public double leftEncoderDist;
    public double rightEncoderDist;
    public double headingDegrees = 0;
    public double degrees = 0;
    public double turnRate = 0;
    public double leftVoltage;
    public double rightVoltage;
    public double leftSpeed = 0;
    public double rightSpeed = 0;
 }

 public void updateInputs(TankInputs tank);

 public void setVoltage(double voltsLeft, double voltsRight);
}
