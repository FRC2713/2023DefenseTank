package frc.robot;

import edu.wpi.first.math.util.Units;

public class Constants {

    public static final int zero = 0; // in case you need a zero :)
    public static final double onebillion = 1000000000; // in case you need a one billion :)


  public static final class RobotMap {
    public static final int frontLeftMotorPort = 4;
    public static final int backLeftMotorPort = 3;
    public static final int frontRightMotorPort = 6;
    public static final int backRightMotorPort = 7;

    //placeholder
    public static final int lightsPort = 0;
  }

  public static final class DriveConstants {
    public static final double kJoystickTurnDeadzone = 0.04;
    public static final double wheelDiameter = 5;
    public static final double gearRatio = 60.0 / 11.0 * 28.0 / 20; // 60.0 / 10.0;
    public static final double distPerPulse =
        (1.0 / gearRatio) * Units.inchesToMeters(wheelDiameter) * Math.PI;
    public static final int currentLimit = 65;
    public static final double robotMassKG  = 20;
    public static final double jKGMetersSquared = 1;
    
    private static final double bumperlessRobotLength = Units.inchesToMeters(26);
    private static final double bumperlessRobotWidth = Units.inchesToMeters(24);
    private static final double bumperThickness = Units.inchesToMeters(3);
    public static final double fullRobotWidth = bumperlessRobotWidth + bumperThickness * 2;
    public static final double fullRobotLength = bumperlessRobotLength + bumperThickness * 2;
 }
 public static final class LightConstants{
    public static final int stripLength = 20;
    public static final double breathDuration = 10;
 }
    
}
