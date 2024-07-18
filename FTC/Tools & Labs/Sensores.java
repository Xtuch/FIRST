/*
How to code basic sensors correctly
*/

package org.firstinspires.ftc.teamcode.Laboratorios;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="labSensores", group="Laboratorios")
//@Disabled
public class Sensores extends LinearOpMode {

    NormalizedColorSensor colorSensor;
    DistanceSensor distanceSensor;
    TouchSensor touchSensor;
    IMU imu;

    String color;
    double distancia;
    String presionado;

    static RevHubOrientationOnRobot.LogoFacingDirection[] logoFacingDirections
            = RevHubOrientationOnRobot.LogoFacingDirection.values();
    static RevHubOrientationOnRobot.UsbFacingDirection[] usbFacingDirections
            = RevHubOrientationOnRobot.UsbFacingDirection.values();

    int logoFacingDirectionPosition = 5;
    int usbFacingDirectionPosition = 0;
    boolean orientationIsValid = true;

    @Override
    public void runOpMode() {

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");
        imu = hardwareMap.get(IMU.class, "imu");

        imu.resetYaw();
        updateOrientation();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {



            //SENSOR DE COLOR



            //Lee el color del sensor de color
            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            //Regresa el color
            if (colors.red > colors.blue && colors.red > colors.green) {
                color = "rojo";
            }
            if (colors.green > colors.red && colors.green > colors.blue) {
                color = "verde";
            }
            if (colors.blue > colors.red && colors.blue > colors.green) {
                color = "azul";
            }



            //SENSOR DE DISTANCIA



            //Regresa la distancia
            distancia = distanceSensor.getDistance(DistanceUnit.CM);



            //BOTON



            //Regresa el valor del boton
            if(touchSensor.isPressed()){
                presionado = "¡Sí!";
            }else{
                presionado = "No...";
            }

          //WARNING we are configuring the Touch Sensor on port #1 instead of port #0. (On the DriverHub)
          //This is because when the REV Robotics Touch Sensor is connected to a digital port using a standard 4-wire JST sensor cable, it is the second digital pin that is connected. 
          //The first pin remains disconnected.


            //TELEMETRY



            telemetry.addLine("Color: " + color);
            telemetry.addLine("Distancia: " + distancia);
            telemetry.addLine("Presionado? " + presionado);
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
