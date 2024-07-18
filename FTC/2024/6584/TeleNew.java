/*
2024 TeleOp
*/

package org.firstinspires.ftc.teamcode.Eliud;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp(name="TeleNew", group="Linear Opmode")
public class TeleNew extends LinearOpMode {



    //CHASIS
    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;



    //MOTOR TOOLS
    private DcMotor CD;
    private DcMotor CI;
    private DcMotor INTAKE;
    private DcMotor BRAZO;



    //SERVO TOOLS
    private Servo MI;
    private Servo MD;
    private Servo SA;
    private Servo SCI;
    private Servo SCD;



    //SENSORS
    private IMU imu;
    private DistanceSensor sensorDeDistancia;



    @Override
    public void runOpMode() {



        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");



        CD = hardwareMap.get(DcMotor.class, "CD");
        CI = hardwareMap.get(DcMotor.class, "CI");
        INTAKE = hardwareMap.get(DcMotor.class, "INTAKE");
        BRAZO = hardwareMap.get(DcMotor.class, "BRAZO");



        MD = hardwareMap.get(Servo.class, "MD");
        MI = hardwareMap.get(Servo.class, "MI");
        SA = hardwareMap.get(Servo.class, "SA");
        SCI = hardwareMap.get(Servo.class, "SCI");
        SCD = hardwareMap.get(Servo.class, "SCD");



        waitForStart();



        while (opModeIsActive()) {



            //DRIVER 1



            FR.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            FL.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
            BR.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x);
            BL.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);



            if (gamepad1.b) {SA.setPosition(1);} else {SA.setPosition(0.5);}



            //DRIVER 2



            MD.setPosition(-gamepad2.right_stick_y + 0.5);
            MI.setPosition(gamepad2.right_stick_y + 0.5);



            INTAKE.setPower(gamepad2.left_trigger - gamepad2.right_trigger);



            BRAZO.setPower(-gamepad2.left_stick_y);



            if (gamepad2.left_bumper) {SCI.setPosition(0);
            } else if (gamepad2.right_bumper) {SCI.setPosition(1);
            }else{SCI.setPosition(0.5);}

            if (gamepad2.y) {SCD.setPosition(0);
            } else if (gamepad2.a) {SCD.setPosition(1);
            }else{SCD.setPosition(0.5);}


            if (gamepad2.b) {
                while (opModeIsActive()) {
                    CI.setPower(0.5);
                    CD.setPower(-0.5);
                }
            }
        }
    }
}
