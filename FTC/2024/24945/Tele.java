/*
TeleOp Test 2024
*/

package org.firstinspires.ftc.teamcode.Dojani;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Tele", group="Linear Opmode")
public class Tele extends LinearOpMode {

    //TOUCH SENSOR
    TouchSensor touch;

    //CHASIS
    private DcMotor R;
    private DcMotor L;
    private DcMotor BR;
    private DcMotor BL;

    //PIXELES
    private DcMotor Elepixeles;
    private Servo Muñecapixeles;
    
    //AVION
    private Servo Avion;
    private Servo Trinquete;

    //ELEVADOR
    private DcMotor ElevadorR;
    private DcMotor ElevadorL;


    //SENSORS
    private IMU imu;
    private DistanceSensor sensorDeDistancia;

    //VARIABLES
    int step = 1;
    int zone = 2;
    boolean garra = true;
    
    //Garrita
    private Servo Garrita1;
    private Servo Garrita2;

    @Override
    public void runOpMode() {


        L = hardwareMap.get(DcMotor.class, "L");
        R = hardwareMap.get(DcMotor.class, "R");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        ElevadorR= hardwareMap.get(DcMotor.class, "ElevadorR");
        ElevadorL= hardwareMap.get(DcMotor.class, "ElevadorL");
        Elepixeles= hardwareMap.get(DcMotor.class, "Elepixeles");
        Muñecapixeles= hardwareMap.get(Servo.class, "Muñecapixeles");
        Avion= hardwareMap.get(Servo.class, "Avion");
        touch = hardwareMap.get(TouchSensor.class, "touch");
        Garrita1 =hardwareMap.get(Servo.class, "Garrita1"); 
        Garrita2 =hardwareMap.get(Servo.class, "Garrita2");
        Trinquete =hardwareMap.get(Servo.class, "Trinquete"); 
        imu = hardwareMap.get(IMU.class, "imu");
        
        //BL.setDirection(DcMotorSimple.Direction.REVERSE);
        R.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();

        while (opModeIsActive()) {

            R.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y);
            L.setPower(gamepad1.left_stick_x - gamepad1.left_stick_y);
            BR.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y);
            BL.setPower(gamepad1.left_stick_x - gamepad1.left_stick_y);

            ElevadorR.setPower(gamepad2.right_stick_y * 0.85);
            ElevadorL.setPower(gamepad2.right_stick_y* 0.85);

            telemetry.update();

            Elepixeles.setPower(-gamepad2.left_stick_y*0.5);

            if (gamepad2.dpad_right){
                Muñecapixeles.setPosition(0.7);}
            else if (gamepad2.dpad_left){
                Muñecapixeles.setPosition(0.4);}

            if (gamepad2.dpad_up){
                Avion.setPosition(1);}
            else if (gamepad2.dpad_down){
                Avion.setPosition(0.3);}
                
            if(gamepad2.left_trigger > 0.5){
                garra = true;
                
            }
            
            if(gamepad2.right_trigger > 0.5) {
                garra = false;
                
            }
            
            if(garra){
                Garrita1.setPosition(0.7);
                //Garrita2.setPosition(0.15);
            }else{
                Garrita1.setPosition(1);
                //Garrita2.setPosition(-0.1);
            }
            
            if(gamepad2.a) {
                Trinquete.setPosition(0.3);
            }
            if(gamepad2.b) {
                Trinquete.setPosition(0.45);
            }
        }
    }
}
