// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Tank extends TimedRobot {
  public static final CommandXboxController driver =
          new CommandXboxController(/*Constants.RobotMap.DRIVER_PORT*/0);
  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();


  @Override
  public void robotInit() {
    //Controls
     driveSubsystem.setDefaultCommand(
        new RunCommand(
            () -> {
              driveSubsystem.GTADrive(
                  driver.getLeftTriggerAxis(), driver.getRightTriggerAxis(), driver.getLeftX());
            },
            driveSubsystem));

  }
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    driveSubsystem.setHalfBrakeHalfCoast();
  }

  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void disabledInit() {
    driveSubsystem.setAllCoast();
  }

  @Override
  public void disabledPeriodic() {

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {

  }

  @Override
  public void simulationPeriodic() {

  }
}
