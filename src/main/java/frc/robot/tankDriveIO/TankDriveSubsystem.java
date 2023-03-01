package frc.robot.tankDriveIO;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public RelativeEncoder getLeftEncoder() { //IO
        return inputs.leftEncoder;
      }
    
      public RelativeEncoder getRightEncoder() { //IO
        return inputs.rightEncoder;
      }
    
      public Rotation2d getHeading() { //IO
        return inputs.heading;
      }
    
      public double getDegrees() { //IO
        return inputs.degrees;
      }
    
      public double getTurnRate() { //IO
        return inputs.turnRate;
      }
    
      public Pose2d getPose() { //IO
        return inputs.pose;
      }
    
      public void resetOdometry(Pose2d pose) {
        roboOdometry.resetPosition(gyro.getRotation2d(), 0, 0, pose);
        resetEncoders();
      }
    
      public void resetEncoders() {
        getLeftEncoder().setPosition(Constants.zero);
        getRightEncoder().setPosition(Constants.zero);
      }
    
      public void resetGyro() {
        gyro.reset();
      }
    
      public double getAverageEncoderDist() {
        return ((getRightEncoder().getPosition() + getLeftEncoder().getPosition()) / 2.0);
      }
    
      public void tankDriveVolts(double left, double right) {
        IO.setVoltage(left, right);
      }
    
      @Override
      public void periodic() {
        // This method will be called once per scheduler run
        roboOdometry.update(
            getHeading(),
            getLeftEncoder().getPosition(),
            getRightEncoder().getPosition());
    
        SmartDashboard.putNumber("Left Enc", getLeftEncoder().getPosition());
        SmartDashboard.putNumber("Right Enc", getRightEncoder().getPosition());
    
        SmartDashboard.putNumber("Odo X", roboOdometry.getPoseMeters().getX());
        SmartDashboard.putNumber("Odo Y", roboOdometry.getPoseMeters().getY());
        SmartDashboard.putNumber("Odo H", roboOdometry.getPoseMeters().getRotation().getDegrees());
    
        SmartDashboard.putNumber("L1 Current", inputs.leftVoltage);
        SmartDashboard.putNumber("L2 Current", inputs.leftVoltage);
        SmartDashboard.putNumber("R1 Current", inputs.rightVoltage);
        SmartDashboard.putNumber("R2 Current", inputs.rightVoltage);
      }
    
      public void GTADrive(double leftTrigger, double rightTrigger, double turn) {
        turn = MathUtil.applyDeadband(turn, Constants.DriveConstants.kJoystickTurnDeadzone);
        turn = turn * turn * Math.signum(turn);
    
        double left = rightTrigger - leftTrigger + turn;
        double right = rightTrigger - leftTrigger - turn;
        left = Math.min(1.0, Math.max(-1.0, left));
        right = Math.max(-1.0, Math.min(1.0, right));
    
       // this.right1.set(right);
       // this.left1.set(left);
      }
}
