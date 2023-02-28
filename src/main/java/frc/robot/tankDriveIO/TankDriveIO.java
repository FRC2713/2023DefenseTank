package frc.robot.tankDriveIO;

import org.littletonrobotics.junction.AutoLog;

import com.revrobotics.RelativeEncoder;

public interface TankDriveIO {

 @AutoLog
 public static class TankInputs {
    public RelativeEncoder lefEncoder;
    public RelativeEncoder righEncoder;
    public double heading = 0;
    public double degrees = 0;
    public double turnRate = 0;
    public double pose = 0;
    
 }

 public void updateInputs(TankInputs tank);

 public void setVoltage(double voltsLeft, double voltsRight);

 public void resetEncoders();

 public void resetGyro();

 public void getAverageEncoderDistance();

}
