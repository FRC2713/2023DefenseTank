package frc.robot.tankDriveIO;


import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import frc.robot.Constants;

public class TankDriveIOSim implements TankDriveIO{

    private static final DifferentialDrivetrainSim sim = DifferentialDrivetrainSim.createKitbotSim(
      KitbotMotor.kDualCIMPerSide, // 2 CIMs per side.
      KitbotGearing.k10p71,        // 10.71:1
      KitbotWheelSize.kSixInch,    // 6" diameter wheels.
      null                         // No measurement noise.
    );

    @Override
    public void updateInputs(TankInputs tank) {
      if (DriverStation.isDisabled()) {
            sim.setInputs(0, 0);
          }

          sim.update(0.02);
        
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
