/*
Final Auto 2024
*/

package org.firstinspires.ftc.teamcode.Eliud.Auto.Zones;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Eliud.Rhino6584;



@Autonomous(name="Zone2", group="Linear Opmode")
public class Zone2 extends LinearOpMode {



    //CHASIS
    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;



    //SERVO TOOLS
    private Servo GI;
    private Servo GD;
    private Servo BI;
    private Servo BD;



    //SENSORS
    private IMU imu;
    private DistanceSensor sensorDeDistancia;




    //VARIABLES
    int zone = 2;
    double distancia = 200;
    int ultimoGrado;



    @Override
    public void runOpMode() {



        //SETTING ORIENTATION
    RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);



        //MAPPING
        FL = hardwareMap.get(DcMotor.class, "0");
        FR = hardwareMap.get(DcMotor.class, "1");
        BL = hardwareMap.get(DcMotor.class, "2");
        BR = hardwareMap.get(DcMotor.class, "3");

        GI = hardwareMap.get(Servo.class, "GI");
        GD = hardwareMap.get(Servo.class, "GD");
        BI = hardwareMap.get(Servo.class, "BI");
        BD = hardwareMap.get(Servo.class, "BD");

        sensorDeDistancia = hardwareMap.get(DistanceSensor.class, "DS");

        imu = hardwareMap.get(IMU.class, "imu");



        //INITS
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        Rhino6584 bot = new Rhino6584(FR, FL, BR, BL, imu);



        //CODE
        waitForStart();



        //RESET IMU
        imu.resetYaw();



        //ZONE SQUARE
        bot.adelante(50, 0.2);


        //CALIBRATION SLEEP
        sleep(500);


        //Turn
        FR.setPower(0.2);
        FL.setPower(-0.2);
        BR.setPower(0.2);
        BL.setPower(-0.2);


        //SEARCH BEACON ALGORITHM
        while(distancia > 20){

            distancia = sensorDeDistancia.getDistance(DistanceUnit.CM);
            
            if(distancia < 20){
                //ultimoGrado = i;
                telemetry.addLine("Object Detected");
                telemetry.update();
                break;}

        }



        //FILTERING ZONE ALGORITHM
        if(ultimoGrado < -30){bot.turn(-90, 0.3);}
        else if (ultimoGrado > 30) {bot.turn(90, 0.3);}
        else{bot.turn(0, 0.3);}


        //CALIBRATION SLEEP
        sleep(500);


        //PREPARE TO PLACE
        bot.adelante(20, 0.2);
        bot.atras(15, 0.2);



        //PLACING
        GI.setPosition(0);
        GD.setPosition(1);



        //TIME TO LEAVE PIXEL
        sleep(1000);
        
        while(opModeIsActive()){
            telemetry.addLine("ultimo grado: " + ultimoGrado);
        }

    }
}
