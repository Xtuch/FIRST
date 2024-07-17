package org.firstinspires.ftc.teamcode.FTC.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="NewRhino", group="Linear Opmode")
@Disabled
public class NewRhino extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightmotor;
    private DcMotor backrightmotor;
    private DcMotor leftmotor;
    private DcMotor backleftmotor;
    private DcMotor elevador1;
    private DcMotor elevador2;
    Servo servo1;
    Servo servo2;
    int arriba = -1;
    int abajo = 1;
    String posicion;
    String palo;
    int registroPosicion = 2;

    double porVel = 1;

    boolean X = true;
    boolean A = false;
    boolean B = false;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        rightmotor = hardwareMap.dcMotor.get("RM");
        backrightmotor = hardwareMap.dcMotor.get("BRM");
        leftmotor = hardwareMap.dcMotor.get("LM");
        backleftmotor = hardwareMap.dcMotor.get("BLM");

        elevador1 = hardwareMap.dcMotor.get("elevador1");
        elevador2 = hardwareMap.dcMotor.get("elevador2");

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");

        leftmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backleftmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backrightmotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backrightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevador1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevador2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backrightmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backleftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevador1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevador2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive() && !isStopRequested() && X == true) {

            rightmotor.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
            backrightmotor.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x - 2 * gamepad1.right_stick_x);
            leftmotor.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x - 2 * gamepad1.right_stick_x);
            backleftmotor.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);

            elevador1.setPower(-gamepad2.left_stick_y);
            elevador2.setPower(-gamepad2.left_stick_y);

            servo1.setPosition(gamepad2.right_stick_y + 0.5);
            servo2.setPosition(gamepad2.right_stick_y + 0.5);

            if(gamepad1.b){
                X = false;
                B = true;
            }

            if(gamepad1.a){
                X = false;
                A = true;
            }

                while(B && opModeIsActive()) {

                    rightmotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * 0.8);
                    backrightmotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x - 2 * gamepad1.right_stick_x) * 0.8);
                    leftmotor.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x - 2 * gamepad1.right_stick_x) * 0.8);
                    backleftmotor.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * 0.8);

                    elevador1.setPower(-gamepad2.left_stick_y);
                    elevador2.setPower(-gamepad2.left_stick_y);

                    servo1.setPosition(gamepad2.right_stick_y + 0.5);
                    servo2.setPosition(gamepad2.right_stick_y + 0.5);

                    if (gamepad1.x) {
                        B = false;
                        X = true;
                    }

                    if (gamepad1.a) {
                        B = false;
                        A = true;
                    }

                }

                while(A && opModeIsActive()) {

                    rightmotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * 0.9);
                    backrightmotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x - 2 * gamepad1.right_stick_x) * 0.9);
                    leftmotor.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x - 2 * gamepad1.right_stick_x) * 0.9);
                    backleftmotor.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * 0.9);

                    elevador1.setPower(-gamepad2.left_stick_y);
                    elevador2.setPower(-gamepad2.left_stick_y);

                    servo1.setPosition(gamepad2.right_stick_y + 0.5);
                    servo2.setPosition(gamepad2.right_stick_y + 0.5);

                    if (gamepad1.x) {
                        B = false;
                        X = true;
                    }

                    if (gamepad1.b) {
                        X = false;
                        B = true;
                    }

                }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addLine("altitud: " + elevador2.getCurrentPosition());
            telemetry.addLine("palo: " + palo);
            telemetry.addLine("VelElevador: " + elevador1.getPower());
            telemetry.addLine("VelChasis: " + rightmotor.getPower());
            telemetry.update();
        }
    }
}
