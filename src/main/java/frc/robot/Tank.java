// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.tankDriveIO.TankDriveIOSparks;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.tankDriveIO.TankDriveIOSim;
import frc.robot.tankDriveIO.TankDriveSubsystem;

public class Tank extends LoggedRobot {
  public static final TankDriveSubsystem driveSubsystem = new TankDriveSubsystem(new TankDriveIOSparks());
  //manualy switch for sim or robo guy
  public static final XboxController driver = new XboxController(Constants.zero);

  @Override
  public void robotInit() {
    Logger.getInstance().addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
    Logger.getInstance().start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.

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
    //Command testComm = new InstantCommand(
    //        driveSubsystem.
    //)
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    // driveSubsystem.setHalfBrakeHalfCoast();
  }

  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void disabledInit() {
    // driveSubsystem.setAllCoast();
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
