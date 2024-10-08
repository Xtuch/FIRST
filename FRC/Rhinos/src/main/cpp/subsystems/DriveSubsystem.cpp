// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#include "subsystems/DriveSubsystem.h"

#include <frc/geometry/Rotation2d.h>
#include <units/angle.h>
#include <units/angular_velocity.h>
#include <units/velocity.h>

#include <frc/smartdashboard/SmartDashboard.h>
#include "frc/smartdashboard/Smartdashboard.h"

#include "Constants.h"
#include "utils/SwerveUtils.h"
#include "utils/deg2rads.h"

#include "networktables/NetworkTable.h"
#include "networktables/NetworkTableInstance.h"
#include "networktables/NetworkTableEntry.h"
#include "networktables/NetworkTableValue.h"

using namespace DriveConstants;
using namespace LimelightConstants;

DriveSubsystem::DriveSubsystem()
    : m_frontLeft{kFrontLeftDrivingCanId, kFrontLeftTurningCanId,
                  kFrontLeftChassisAngularOffset},
      m_rearLeft{kRearLeftDrivingCanId, kRearLeftTurningCanId,
                 kRearLeftChassisAngularOffset},
      m_frontRight{kFrontRightDrivingCanId, kFrontRightTurningCanId,
                   kFrontRightChassisAngularOffset},
      m_rearRight{kRearRightDrivingCanId, kRearRightTurningCanId,
                  kRearRightChassisAngularOffset},
      m_odometry{kDriveKinematics,
                 frc::Rotation2d(units::radian_t{
                     deg2rad(-m_gyro.GetYaw())}),
                 {m_frontLeft.GetPosition(), m_frontRight.GetPosition(),
                  m_rearLeft.GetPosition(), m_rearRight.GetPosition()},
                 frc::Pose2d{}} {}

void DriveSubsystem::Periodic() {
  frc::SmartDashboard::PutNumber("heading:", deg2rad(-m_gyro.GetYaw()));
  // Implementation of subsystem periodic method goes here.
  m_odometry.Update(frc::Rotation2d(units::radian_t{
                        deg2rad(-m_gyro.GetYaw())}),
                    {m_frontLeft.GetPosition(), m_rearLeft.GetPosition(),
                     m_frontRight.GetPosition(), m_rearRight.GetPosition()});
                     

}

void DriveSubsystem::Drive(units::meters_per_second_t xSpeed,
                           units::meters_per_second_t ySpeed,
                           units::radians_per_second_t rot, bool fieldRelative,
                           bool rateLimit, bool yawReset, bool b_button) {

  if(b_button){

    std::shared_ptr<nt::NetworkTable> table = nt::NetworkTableInstance::GetDefault().GetTable("limelight");
    double targetOffsetAngle_Horizontal = -table->GetNumber("tx",0.0);

    if (targetOffsetAngle_Horizontal < 7.5 && targetOffsetAngle_Horizontal > -7.5) {
        integral_error += targetOffsetAngle_Horizontal*((double)frc::Timer::GetFPGATimestamp()-previous_time);
    } else {
        integral_error = 0;
    }

    pidOutput = targetOffsetAngle_Horizontal*kAlignmentP+integral_error*kAlignmentI;

    previous_time = (double)frc::Timer::GetFPGATimestamp();

    double tv = table->GetNumber("tv",0.0);

    //frc::SmartDashboard::PutNumber("tv:", tv);
    //frc::SmartDashboard::PutNumber("tx:", targetOffsetAngle_Horizontal);

    if(tv){
      auto states = kDriveKinematics.ToSwerveModuleStates(
        fieldRelative
            ? frc::ChassisSpeeds::FromFieldRelativeSpeeds(
                  (units::meters_per_second_t)0, (units::meters_per_second_t)0, (units::radians_per_second_t)pidOutput,
                  frc::Rotation2d(units::radian_t{
                      deg2rad(-m_gyro.GetYaw())}))
            : frc::ChassisSpeeds{(units::meters_per_second_t)0, (units::meters_per_second_t)0, (units::radians_per_second_t)pidOutput});

      kDriveKinematics.DesaturateWheelSpeeds(&states, DriveConstants::kMaxSpeed);

      auto [fl, fr, bl, br] = states;

      m_frontLeft.SetDesiredState(fl);
      m_frontRight.SetDesiredState(fr);
      m_rearLeft.SetDesiredState(bl);
      m_rearRight.SetDesiredState(br);
    }


  }
  else{
    previous_time = (double)frc::Timer::GetFPGATimestamp();
    
    if (yawReset) {
      this->ZeroHeading();
    }

    double xSpeedCommanded;
    double ySpeedCommanded;

    if (rateLimit) {
      // Convert XY to polar for rate limiting
      double inputTranslationDir = atan2(ySpeed.value(), xSpeed.value());
      double inputTranslationMag =
          sqrt(pow(xSpeed.value(), 2) + pow(ySpeed.value(), 2));

      // Calculate the direction slew rate based on an estimate of the lateral
      // acceleration
      double directionSlewRate;
      if (m_currentTranslationMag != 0.0) {
        directionSlewRate =
            abs(DriveConstants::kDirectionSlewRate / m_currentTranslationMag);
      } else {
        directionSlewRate = 500.0;  // some high number that means the slew rate
                                    // is effectively instantaneous
      }

      double currentTime = wpi::Now() * 1e-6;
      double elapsedTime = currentTime - m_prevTime;
      double angleDif = SwerveUtils::AngleDifference(inputTranslationDir,
                                                    m_currentTranslationDir);
      if (angleDif < 0.45 * std::numbers::pi) {
        m_currentTranslationDir = SwerveUtils::StepTowardsCircular(
            m_currentTranslationDir, inputTranslationDir,
            directionSlewRate * elapsedTime);
        m_currentTranslationMag = m_magLimiter.Calculate(inputTranslationMag);
      } else if (angleDif > 0.85 * std::numbers::pi) {
        if (m_currentTranslationMag >
            1e-4) {  // some small number to avoid floating-point errors with
                    // equality checking
          // keep currentTranslationDir unchanged
          m_currentTranslationMag = m_magLimiter.Calculate(0.0);
        } else {
          m_currentTranslationDir =
              SwerveUtils::WrapAngle(m_currentTranslationDir + std::numbers::pi);
          m_currentTranslationMag = m_magLimiter.Calculate(inputTranslationMag);
        }
      } else {
        m_currentTranslationDir = SwerveUtils::StepTowardsCircular(
            m_currentTranslationDir, inputTranslationDir,
            directionSlewRate * elapsedTime);
        m_currentTranslationMag = m_magLimiter.Calculate(0.0);
      }
      m_prevTime = currentTime;

      xSpeedCommanded = m_currentTranslationMag * cos(m_currentTranslationDir);
      ySpeedCommanded = m_currentTranslationMag * sin(m_currentTranslationDir);
      m_currentRotation = m_rotLimiter.Calculate(rot.value());

    } else {
      xSpeedCommanded = xSpeed.value();
      ySpeedCommanded = ySpeed.value();
      m_currentRotation = rot.value();
    }

    // Convert the commanded speeds into the correct units for the drivetrain
    units::meters_per_second_t xSpeedDelivered =
        xSpeedCommanded * DriveConstants::kMaxSpeed;
    units::meters_per_second_t ySpeedDelivered =
        ySpeedCommanded * DriveConstants::kMaxSpeed;
    units::radians_per_second_t rotDelivered =
        m_currentRotation * DriveConstants::kMaxAngularSpeed;

    auto states = kDriveKinematics.ToSwerveModuleStates(
        fieldRelative
            ? frc::ChassisSpeeds::FromFieldRelativeSpeeds(
                  xSpeedDelivered, ySpeedDelivered, rotDelivered,
                  frc::Rotation2d(units::radian_t{
                      deg2rad(-m_gyro.GetYaw())}))
            : frc::ChassisSpeeds{xSpeedDelivered, ySpeedDelivered, rotDelivered});

    kDriveKinematics.DesaturateWheelSpeeds(&states, DriveConstants::kMaxSpeed);

    auto [fl, fr, bl, br] = states;

    m_frontLeft.SetDesiredState(fl);
    m_frontRight.SetDesiredState(fr);
    m_rearLeft.SetDesiredState(bl);
    m_rearRight.SetDesiredState(br);
  }
}

                           

void DriveSubsystem::SetX() {
  m_frontLeft.SetDesiredState(
      frc::SwerveModuleState{0_mps, frc::Rotation2d{45_deg}});
  m_frontRight.SetDesiredState(
      frc::SwerveModuleState{0_mps, frc::Rotation2d{-45_deg}});
  m_rearLeft.SetDesiredState(
      frc::SwerveModuleState{0_mps, frc::Rotation2d{-45_deg}});
  m_rearRight.SetDesiredState(
      frc::SwerveModuleState{0_mps, frc::Rotation2d{45_deg}});
}

void DriveSubsystem::SetModuleStates(
    wpi::array<frc::SwerveModuleState, 4> desiredStates) {
  kDriveKinematics.DesaturateWheelSpeeds(&desiredStates,
                                         DriveConstants::kMaxSpeed);
  m_frontLeft.SetDesiredState(desiredStates[0]);
  m_frontRight.SetDesiredState(desiredStates[1]);
  m_rearLeft.SetDesiredState(desiredStates[2]);
  m_rearRight.SetDesiredState(desiredStates[3]);
}

void DriveSubsystem::ResetEncoders() {
  m_frontLeft.ResetEncoders();
  m_rearLeft.ResetEncoders();
  m_frontRight.ResetEncoders();
  m_rearRight.ResetEncoders();
}

units::degree_t DriveSubsystem::GetHeading() {
  return frc::Rotation2d(
             units::radian_t{deg2rad(-m_gyro.GetYaw())})
      .Degrees();
}

void DriveSubsystem::DriveForSeconds(units::meters_per_second_t xSpeed,double seconds) {
  units::time::second_t startTime = frc::Timer::GetFPGATimestamp();
  while (frc::Timer::GetFPGATimestamp() - startTime < (units::time::second_t)seconds) {
    Drive(xSpeed, 0_mps, 0_rad_per_s, false, false, false, false);
  }
  Drive(0_mps, 0_mps, 0_rad_per_s, false, false, false, false);
}

void DriveSubsystem::ZeroHeading() { m_gyro.Reset(); }

double DriveSubsystem::GetTurnRate() {
  return -m_gyro.GetRate();
}

frc::Pose2d DriveSubsystem::GetPose() { return m_odometry.GetPose(); }

void DriveSubsystem::ResetOdometry(frc::Pose2d pose) {
  m_odometry.ResetPosition(
      GetHeading(),
      {m_frontLeft.GetPosition(), m_frontRight.GetPosition(),
       m_rearLeft.GetPosition(), m_rearRight.GetPosition()},
      pose);
}
