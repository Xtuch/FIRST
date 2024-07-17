/*
FTC 2024 TeleOp
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Tele", group="Linear Opmode")
public class Tele extends LinearOpMode {

    //CHASIS
    private DcMotor Right;
    private DcMotor Left;
    private DcMotor BackRight;
    private DcMotor BackLeft;

    //MOTOR TOOLS
    private DcMotor Barredora;
    private DcMotor ElevadorDerecho;
    private DcMotor ElevadorIzquierdo;
    private DcMotor Gancho;

    //SERVO TOOLS
    private Servo Cremallera;
    private Servo Avion;
    private Servo BaseBrazo;
    private Servo GarraA;
    private Servo GarraB;

    //SENSORS
    private IMU imu;
    private DistanceSensor sensorDeDistancia;

    //VARIABLES
    int step = 1;
    int zone = 2;

    @Override
    public void runOpMode() {


        Left = hardwareMap.get(DcMotor.class, "0");
        Right = hardwareMap.get(DcMotor.class, "1");
        BackLeft = hardwareMap.get(DcMotor.class, "2");
        BackRight = hardwareMap.get(DcMotor.class, "3");
        Barredora = hardwareMap.get(DcMotor.class, "m0");
        ElevadorDerecho = hardwareMap.get(DcMotor.class, "m1");
        ElevadorIzquierdo = hardwareMap.get(DcMotor.class, "m2");
        Gancho = hardwareMap.get(DcMotor.class, "m3");
        BaseBrazo = hardwareMap.get(Servo.class, "s0");
        GarraA = hardwareMap.get(Servo.class, "s1");
        GarraB = hardwareMap.get(Servo.class, "s2");
        Avion = hardwareMap.get(Servo.class, "s3");
        Cremallera = hardwareMap.get(Servo.class, "s4");

        ElevadorDerecho.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            Right.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x + gamepad1.left_trigger);
            Left.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x + gamepad1.right_trigger);
            BackRight.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x + gamepad1.right_trigger);
            BackLeft.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x + gamepad1.left_trigger);

            ElevadorIzquierdo.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
            ElevadorDerecho.setPower(gamepad2.right_trigger - gamepad2.left_trigger);

            if (gamepad1.a) {Barredora.setPower(1);}
            if (gamepad1.b) {Barredora.setPower(0);}

            BaseBrazo.setPosition(-gamepad2.left_stick_y + 0.5);

            GarraA.setPosition(gamepad2.right_stick_y + 0.5);
            GarraB.setPosition(-gamepad2.right_stick_y + 0.5);

        }
    }
}
