/*
Code to test custom functions in Rhino
*/

package org.firstinspires.ftc.teamcode.Eliud;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Path1", group="Linear Opmode")
public class Path1 extends LinearOpMode {

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;
    private IMU imu;
    private Servo garra;
    static RevHubOrientationOnRobot.LogoFacingDirection[] logoFacingDirections
            = RevHubOrientationOnRobot.LogoFacingDirection.values();
    static RevHubOrientationOnRobot.UsbFacingDirection[] usbFacingDirections
            = RevHubOrientationOnRobot.UsbFacingDirection.values();
    int logoFacingDirectionPosition = 5;
    int usbFacingDirectionPosition = 0;
    boolean orientationIsValid = true;

    @Override
    public void runOpMode() {

        FR = hardwareMap.dcMotor.get("0");
        FL = hardwareMap.dcMotor.get("1");
        BR = hardwareMap.dcMotor.get("2");
        BL = hardwareMap.dcMotor.get("3");
        garra = hardwareMap.get(Servo.class, "5");
        imu = hardwareMap.get(IMU.class, "imu");

        Rhino chasis = new Rhino(FR, FL, BR, BL, garra, imu);
        chasis.resetChasis();
        chasis.autoSetup();
        imu.resetYaw();
        updateOrientation();

        waitForStart();

        chasis.drive(22, 1, 0);
        chasis.turn(90, 0.4);

        while (opModeIsActive()) {

            telemetry.addLine("DEGREES: ");
            telemetry.addLine("yaw: " + chasis.getYaw());
            telemetry.addLine("MOTORS: ");
            telemetry.addLine("FR: " + FR.getCurrentPosition());
            telemetry.addLine("FL: " + FL.getCurrentPosition());
            telemetry.addLine("BR: " + BR.getCurrentPosition());
            telemetry.addLine("BL: " + BL.getCurrentPosition());
            telemetry.update();

        }

    }
    void updateOrientation() {
        RevHubOrientationOnRobot.LogoFacingDirection logo = logoFacingDirections[logoFacingDirectionPosition];
        RevHubOrientationOnRobot.UsbFacingDirection usb = usbFacingDirections[usbFacingDirectionPosition];
        try {
            RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logo, usb);
            imu.initialize(new IMU.Parameters(orientationOnRobot));
            orientationIsValid = true;
        } catch (IllegalArgumentException e) {
            orientationIsValid = false;
        }
    }
}
