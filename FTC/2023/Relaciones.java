/*
Simple code for motors
*/

package org.firstinspires.ftc.teamcode.Laboratorios;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="labRelaciones", group="Laboratorios")
//@Disabled
public class Relaciones extends LinearOpMode {

    private DcMotor motor1;
    private DcMotor motor2;

    @Override
    public void runOpMode() {
        telemetry.addData("RELACIONES", "Initialized");
        telemetry.update();

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            int poderm1 = 1;
            int poderm2 = 1;

            motor1.setPower(poderm1);
            motor2.setPower(poderm2);

            telemetry.addLine("Datos Motores:");
            telemetry.addLine("poder m1:" + poderm1);
            telemetry.addLine("poder m2:" + poderm2);
            telemetry.update();

        }
    }
}
