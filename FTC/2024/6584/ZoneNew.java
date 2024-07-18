/*
Auto 2024
*/

package org.firstinspires.ftc.teamcode.Eliud;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;



@Autonomous(name="ZoneNew", group="Linear Opmode")
public class ZoneNew extends LinearOpMode {



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



    //VARIABLES
    int zone = 2;
    double distancia;
    int ultimoGrado;
    


    @Override
    public void runOpMode() {



        //SETTING ORIENTATION
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.DOWN;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);



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


        
        sensorDeDistancia = hardwareMap.get(DistanceSensor.class, "DS");



        imu = hardwareMap.get(IMU.class, "imu");



        //INITS
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        Rhino6584 bot = new Rhino6584(FR, FL, BR, BL, imu);



        waitForStart();
        
        
        
        //RESET IMU
        imu.resetYaw();



        //ZONE SQUARE
        bot.adelante(55, 0.2);



        //CALIBRATION SLEEP
        sleep(500);



        //SEARCH BEACON ALGORITHM
        for(int i = -90; i < 90; i += 3){



            YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();
            distancia = sensorDeDistancia.getDistance(DistanceUnit.CM);



            bot.turn(i, 0.2);
            
            
            
            //CALIBRATION SLEEP
            sleep(50);
            
            
            
            if(distancia < 20){ultimoGrado = i;break;}
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
        INTAKE.setPower(1);
        


        //TIME TO LEAVE PIXEL
        sleep(1000);
        
        
        //PLACING
        INTAKE.setPower(0);
        
        
        while(opModeIsActive()){telemetry.addLine("ultimo grado: " + ultimoGrado);}
        
        
        
    }
}
