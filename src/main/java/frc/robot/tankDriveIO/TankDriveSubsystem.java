package frc.robot.tankDriveIO;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TankDriveSubsystem extends SubsystemBase {
    
    public final TankInputsAutoLogged inputs = new TankInputsAutoLogged();
    private final TankDriveIO IO;
    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    private final DifferentialDriveOdometry roboOdometry =
        new DifferentialDriveOdometry(gyro.getRotation2d(), 0, 0);
    
    public TankDriveSubsystem(TankDriveIO IO){
        IO.updateInputs(inputs);
        this.IO = IO;
    }

      public double getHeading() { 
        return inputs.headingDegrees;
      }
    
      public double getDegrees() { 
        return inputs.degrees;
      }
    
      public double getTurnRate() { //IO
        return inputs.turnRate;
      }
    
      public void resetOdometry(Pose2d pose) {
        roboOdometry.resetPosition(gyro.getRotation2d(), 0, 0, pose);
        resetEncoders();
      }
    
      public void resetEncoders() {
        inputs.leftEncoderDist = 0;
        inputs.rightEncoderDist =0;
      }
    
      public void resetGyro() {
        gyro.reset();
      }
    
      public double getAverageEncoderDist() {
        return ((inputs.leftEncoderDist + inputs.leftEncoderDist) / 2.0);
      }
    
      public void tankDriveVolts(double left, double right) {
        IO.setVoltage(left, right);
      }
      
      @Override
      public void periodic() {
        // This method will be called once per scheduler run
        IO.updateInputs(inputs);
        roboOdometry.update(
            Rotation2d.fromDegrees(getHeading()),
            inputs.leftEncoderDist,
            inputs.rightEncoderDist);
            Logger.getInstance().processInputs("tank", inputs);
            Logger.getInstance().recordOutput("Odometry", new double[] {roboOdometry.getPoseMeters().getX(), roboOdometry.getPoseMeters().getY(), roboOdometry.getPoseMeters().getRotation().getDegrees()});
      }
    
      public void GTADrive(double leftTrigger, double rightTrigger, double turn) {
        turn = MathUtil.applyDeadband(turn, Constants.DriveConstants.kJoystickTurnDeadzone);
        turn = turn * turn * Math.signum(turn);
    
        double left = rightTrigger - leftTrigger + turn;
        double right = rightTrigger - leftTrigger - turn;
        left = Math.min(1.0, Math.max(-1.0, left));
        right = Math.max(-1.0, Math.min(1.0, right));
    
        IO.setVoltage(left * 12, right * 12);
      }
}
