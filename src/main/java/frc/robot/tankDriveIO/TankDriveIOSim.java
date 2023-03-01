package frc.robot.tankDriveIO;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.Constants;

public class TankDriveIOSim implements TankDriveIO{

    private static final DifferentialDrivetrainSim sim = new DifferentialDrivetrainSim(DCMotor.getNEO(4),
    Constants.DriveConstants.gearRatio,
    Constants.DriveConstants.jKGMetersSquared,
    Constants.DriveConstants.robotMassKG,
    Constants.DriveConstants.wheelDiameter/2,
    Constants.DriveConstants.fullRobotWidth,
    null);

    @Override
    public void updateInputs(TankInputs tank) {
      if (DriverStation.isDisabled()) {
            sim.setInputs(0, 0);
          }
        
        tank.leftEncoderDist = sim.getLeftPositionMeters();
        tank.rightEncoderDist = sim.getRightPositionMeters();
        tank.degrees = 0;
        tank.headingDegrees = sim.getHeading().getDegrees();
        tank.turnRate = 0;
        tank.leftVoltage = sim.getCurrentDrawAmps();
        tank.rightVoltage = sim.getCurrentDrawAmps();
        tank.leftSpeed = sim.getLeftVelocityMetersPerSecond();
        tank.rightSpeed = sim.getRightVelocityMetersPerSecond();
    }

    @Override
    public void setVoltage(double voltsLeft, double voltsRight) {
      sim.setInputs(voltsLeft, voltsRight);       
    }
}
