/*
Simple Servo code
*/

package org.firstinspires.ftc.teamcode.Laboratorios;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="labTransmision", group="Laboratorios")
//@Disabled
public class Transmision extends LinearOpMode {

    private Servo servo180;
    private Servo servo360;

    @Override
    public void runOpMode() {
        telemetry.addData("RELACIONES", "Initialized");
        telemetry.update();

        servo180 = hardwareMap.servo.get("servo180");
        servo360 = hardwareMap.servo.get("servo360");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            int posicion1 = 0;
            int posicion2 = 1;

            for(int i = 0; i < 2000; i++) {

                servo180.setPosition(posicion1);
                servo360.setPosition(posicion2);

                telemetry.addLine("Datos Motores:");
                telemetry.addLine("posicion actual s180: " + servo180.getPosition());
                telemetry.addLine("posicion actual s360: " + servo360.getPosition());
                telemetry.update();

                sleep(1);

            }

            for(int i = 0; i < 2000; i++) {

                servo180.setPosition(posicion2);
                servo360.setPosition(posicion1);

                telemetry.addLine("Datos Motores:");
                telemetry.addLine("posicion actual s180: " + servo180.getPosition());
                telemetry.addLine("posicion actual s360: " + servo360.getPosition());
                telemetry.update();

                sleep(1);

            }
        }
    }
}
