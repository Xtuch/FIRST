/*
This code is the Auto for 2023 worlds
*/

package org.firstinspires.ftc.teamcode.FTC.autonomo.MUNDIAL;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.FTC.autonomo.samples.NewRhinos;

@Autonomous(name="DosUnCono", group="Robot")
@Disabled
public class DosUnCono extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightmotor;
    private DcMotor backrightmotor;
    private DcMotor leftmotor;
    private DcMotor backleftmotor;
    private DcMotor elevador1;
    private DcMotor elevador2;
    Servo servo1;
    Servo servo2;
    NormalizedColorSensor colorSensor;
    IMU imu;
    double repetir = 2;
    double zone = 1;
    double conos = 0;
    double desacelerar = 1;
    String posicion;
    boolean turning = false;
    boolean straight = false;
    int step = 1;


    static RevHubOrientationOnRobot.LogoFacingDirection[] logoFacingDirections
            = RevHubOrientationOnRobot.LogoFacingDirection.values();
    static RevHubOrientationOnRobot.UsbFacingDirection[] usbFacingDirections
            = RevHubOrientationOnRobot.UsbFacingDirection.values();

    int logoFacingDirectionPosition = 5;
    int usbFacingDirectionPosition = 0;
    boolean orientationIsValid = true;

    @Override
    public void runOpMode() throws InterruptedException {

        rightmotor = hardwareMap.dcMotor.get("RM");
        backrightmotor = hardwareMap.dcMotor.get("BRM");
        leftmotor = hardwareMap.dcMotor.get("LM");
        backleftmotor = hardwareMap.dcMotor.get("BLM");

        elevador1 = hardwareMap.dcMotor.get("elevador1");
        elevador2 = hardwareMap.dcMotor.get("elevador2");

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

        imu = hardwareMap.get(IMU.class, "imu");

        NewRhinos NewRhino = new NewRhinos(rightmotor, leftmotor, backrightmotor, backleftmotor, elevador1, elevador2, servo1, servo2);

        NewRhino.resetTotal();
        imu.resetYaw();
        updateOrientation();

        waitForStart();

        //GO TO THE CONE

        if (step == 1) {

            //ATRAS
            straight = true;

            while (straight == true && opModeIsActive()) {

                YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                //accion

                if (rightmotor.getCurrentPosition() > -500) {

                    if (-3 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 3) {
                        NewRhino.atras(0.2 * desacelerar, 1, 0.8);
                    }

                    if (-3 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.atras(0.2 * desacelerar, 1, 0.5);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 3) {
                        NewRhino.atras(0.2 * desacelerar, 0.5, 1);
                    }

                    //frenado

                    if (rightmotor.getCurrentPosition() < -500) {
                        desacelerar = 1;
                    }

                }

                //correccion

                if (rightmotor.getCurrentPosition() < -600) {

                    if (-3 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 3) {
                        NewRhino.adelante(0.2, 1, 1);
                    }

                    if (-3 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.adelante(0.2, 0.5, 1);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 3) {
                        NewRhino.adelante(0.2, 1, 0.5);
                    }

                }

                //stop

                if (-500 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -600) {

                    sleep(200);

                    if (-500 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -600) {
                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        straight = false;

                    }
                }
            }

            //READ THE CONE

            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            if (colors.red > colors.blue && colors.red > colors.green) {
                zone = 1;
            }
            if (colors.green > colors.red && colors.green > colors.blue) {
                zone = 2;
            }
            if (colors.blue > colors.red && colors.blue > colors.green) {
                zone = 3;
            }

            step = 2;

        }

        if (step == 2) {

            //ELEVATOR UP
            NewRhino.eleArriba(1);

            //GO TO THE POLE SQUARE

            //ATRAS
            straight = true;

            while (straight == true && opModeIsActive()) {

                YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                //check elevator
                if (elevador2.getCurrentPosition() > 1100) {
                    NewRhino.paroEle();
                }

                //accion

                if (rightmotor.getCurrentPosition() > -900) {

                    if (-3 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 3) {
                        NewRhino.atras(0.6 * desacelerar, 1, 0.9);
                    }

                    if (-3 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.atras(0.6 * desacelerar, 1, 0.5);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 3) {
                        NewRhino.atras(0.6 * desacelerar, 0.5, 1);
                    }

                    //frenado

                    if (rightmotor.getCurrentPosition() < -400) {
                        desacelerar = 0.5;
                    }

                    if (rightmotor.getCurrentPosition() < -700) {
                        desacelerar = 0.35;
                    }

                }

                //correccion

                if (rightmotor.getCurrentPosition() < -1000) {

                    if (-3 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 3) {
                        NewRhino.adelante(0.2, 1, 1);
                    }

                    if (-3 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.adelante(0.2, 0.5, 1);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 3) {
                        NewRhino.adelante(0.2, 1, 0.5);
                    }

                }

                //stop

                if (-900 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -1000) {

                    sleep(200);

                    if (-900 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -1000) {
                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        straight = false;

                    }
                }
            }

            step = 3;

        }

        if (step == 3) {

            //TURN TO THE POLE

            turning = true;

            while (turning == true && opModeIsActive()) {

                YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

                //check elevator

                if (elevador2.getCurrentPosition() >= 1100) {
                    NewRhino.paroEle();
                }

                if (40 > turn.getYaw(AngleUnit.DEGREES)) {
                    NewRhino.giroIzq(0.2, 1, 1);
                }

                if (turn.getYaw(AngleUnit.DEGREES) > 45) {
                    NewRhino.giroDer(0.2, 1, 1);
                }

                if (40 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < 45) {

                    sleep(200);

                    if (40 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < 45) {

                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        turning = false;
                    }
                }
            }

            step = 4;

        }

        if (step == 4) {

            //ADELANTE
            straight = true;

            while (straight == true && opModeIsActive()) {

                YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                //correccion

                if (rightmotor.getCurrentPosition() > 200) {

                    if (46 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 48) {
                        NewRhino.atras(0.2, 1, 1);
                    }

                    if (46 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.atras(0.2, 1, 0.5);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 48) {
                        NewRhino.atras(0.2, 0.5, 1);
                    }

                }

                //accion

                if (rightmotor.getCurrentPosition() < 150) {

                    if (46 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 48) {
                        NewRhino.adelante(0.2 * desacelerar, 1, 1);
                    }

                    if (46 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.adelante(0.2 * desacelerar, 0.5, 1);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 48) {
                        NewRhino.adelante(0.2 * desacelerar, 1, 0.5);
                    }

                    //frenado

                    if (rightmotor.getCurrentPosition() > 100) {
                        desacelerar = 0.5;
                    }

                }

                if (150 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 200) {

                    sleep(200);

                    if (150 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 200) {
                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        straight = false;
                    }
                }
            }

            //PLACE THE CONE

            servo1.setPosition(1);
            servo2.setPosition(1);
            sleep(1500);
            servo1.setPosition(0.5);
            servo2.setPosition(0.5);

            step = 5;

        }

        if (step == 5) {

            //ATRAS
            straight = true;

            while (straight == true && opModeIsActive()) {

                YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                //accion

                if (rightmotor.getCurrentPosition() > -200) {

                    if (46 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 48) {
                        NewRhino.atras(0.2 * desacelerar, 1, 0.8);
                    }

                    if (46 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.atras(0.2 * desacelerar, 1, 0.5);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 48) {
                        NewRhino.atras(0.2 * desacelerar, 0.5, 1);
                    }

                    //frenado

                    if (rightmotor.getCurrentPosition() < -150) {
                        desacelerar = 0.5;
                    }

                }

                //correccion

                if (rightmotor.getCurrentPosition() < -250) {

                    if (46 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 48) {
                        NewRhino.adelante(0.2, 1, 1);
                    }

                    if (46 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.adelante(0.2, 0.5, 1);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > 48) {
                        NewRhino.adelante(0.2, 1, 0.5);
                    }

                }

                //stop

                if (-200 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -250) {

                    sleep(200);

                    if (-200 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -250) {
                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        straight = false;

                    }
                }
            }

            NewRhino.eleAbajo(1);

            step = 6;

        }

        if (step == 6) {

            //TURN TO THE CONES

            turning = true;

            while (turning == true && opModeIsActive()) {

                YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

                //check elevator

                if (elevador2.getCurrentPosition() <= 500) {
                    NewRhino.paroEle();
                }

                if (-90 > turn.getYaw(AngleUnit.DEGREES)) {
                    NewRhino.giroIzq(0.3, 1, 1);
                }

                if (turn.getYaw(AngleUnit.DEGREES) > -75) {
                    NewRhino.giroDer(0.3, 1, 1);
                }

                if (-90 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -65) {

                    sleep(200);

                    if (-90 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -65) {

                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        turning = false;
                    }
                }
            }

            step = 7;
        }

        if (step == 7) {

            //TURN TO THE CONES

            turning = true;

            while (turning == true && opModeIsActive()) {

                YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

                if (-93 > turn.getYaw(AngleUnit.DEGREES)) {
                    NewRhino.giroIzq(0.2, 1, 1);
                }

                if (turn.getYaw(AngleUnit.DEGREES) > -87) {
                    NewRhino.giroDer(0.2, 1, 1);
                }

                if (-93 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -87) {

                    sleep(200);

                    if (-93 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -87) {

                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        turning = false;
                    }
                }
            }

            step = 8;

        }

        if (step == 8) {

            desacelerar = 1;

            //ADELANTE
            straight = true;

            while (straight == true && opModeIsActive()) {

                YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                //frenado

                if (rightmotor.getCurrentPosition() > 200) {
                    desacelerar = 0.2;
                }

                //correccion

                if (rightmotor.getCurrentPosition() > 900) {

                    if (-92 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -88) {
                        NewRhino.atras(0.2, 1, 1);
                    }

                    if (-92 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.atras(0.2, 1, 0.5);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > -88) {
                        NewRhino.atras(0.2, 0.5, 1);
                    }

                }

                //accion

                if (rightmotor.getCurrentPosition() < 800) {

                    if (-92 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -88) {
                        NewRhino.adelante(desacelerar, 1, 1);
                    }

                    if (-92 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.adelante(desacelerar, 0.5, 1);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > -88) {
                        NewRhino.adelante(desacelerar, 1, 0.5);
                    }

                }

                if (800 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 900) {

                    sleep(200);

                    if (800 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 900) {
                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        straight = false;
                    }
                }
            }

            //GRAB THE CONE

            NewRhino.eleAbajo(1);

            servo1.setPosition(0);
            servo2.setPosition(0);

            while (elevador2.getCurrentPosition() > 400) {

                if (elevador2.getCurrentPosition() < 400) {
                    NewRhino.paroEle();
                }

            }

            sleep(700);

            servo1.setPosition(0.5);
            servo2.setPosition(0.5);

            NewRhino.eleArriba(1);

            while (elevador2.getCurrentPosition() <= 400 && opModeIsActive()) {
                NewRhino.paroChasis();
            }

            step = 9;

        }

        if (step == 9) {

            //ELEVATOR UP
            NewRhino.eleArriba(1);

            //GO TO THE POLE SQUARE

            desacelerar = 1;

            //ATRAS
            straight = true;

            while (straight == true && opModeIsActive()) {

                YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                //check elevator
                if (elevador2.getCurrentPosition() > 600) {
                    NewRhino.paroEle();
                }

                //frenado

                if (rightmotor.getCurrentPosition() < -200) {
                    desacelerar = 0.2;
                }

                //accion

                if (rightmotor.getCurrentPosition() > -750) {

                    if (-88 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -84) {
                        NewRhino.atras(0.6 * desacelerar, 1, 1);
                    }

                    if (-88 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.atras(0.6 * desacelerar, 1, 0.5);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > -84) {
                        NewRhino.atras(0.6 * desacelerar, 0.5, 1);
                    }

                }

                //correccion

                if (rightmotor.getCurrentPosition() < -850) {

                    if (-88 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -84) {
                        NewRhino.adelante(0.2, 1, 1);
                    }

                    if (-88 > angle.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.adelante(0.2, 0.2, 1);
                    }

                    if (angle.getYaw(AngleUnit.DEGREES) > -84) {
                        NewRhino.adelante(0.2, 1, 0.2);
                    }

                }

                //stop

                if (-750 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -850) {

                    sleep(200);

                    if (-750 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -850) {
                        NewRhino.paroChasis();
                        NewRhino.resetChasis();
                        desacelerar = 1;
                        straight = false;

                    }
                }


                step = 10;

            }

            if (step == 10) {

                //TURN TO THE CONES

                turning = true;

                while (turning == true && opModeIsActive()) {

                    YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

                    if (-60 > turn.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.giroIzq(0.3, 1, 1);
                    }

                    if (turn.getYaw(AngleUnit.DEGREES) > -55) {
                        NewRhino.giroDer(0.3, 1, 1);
                    }

                    if (-60 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -55) {

                        sleep(200);

                        if (-60 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -55) {

                            NewRhino.paroChasis();
                            NewRhino.resetChasis();
                            desacelerar = 1;
                            turning = false;
                        }
                    }
                }

                step = 11;

            }

            if (step == 11) {

                desacelerar = 0.2;

                //ADELANTE
                straight = true;

                while (straight == true && opModeIsActive()) {

                    YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                    //frenado

                    if (rightmotor.getCurrentPosition() > 50) {
                        desacelerar = 0.2;
                    }

                    //correccion

                    if (rightmotor.getCurrentPosition() > 300) {

                        if (-55 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -50) {
                            NewRhino.atras(0.2, 1, 1);
                        }

                        if (-55 > angle.getYaw(AngleUnit.DEGREES)) {
                            NewRhino.atras(0.2, 1, 0.5);
                        }

                        if (angle.getYaw(AngleUnit.DEGREES) > -50) {
                            NewRhino.atras(0.2, 0.5, 1);
                        }

                    }

                    //accion

                    if (rightmotor.getCurrentPosition() < 200) {

                        if (-55 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -50) {
                            NewRhino.adelante(desacelerar, 1, 1);
                        }

                        if (-55 > angle.getYaw(AngleUnit.DEGREES)) {
                            NewRhino.adelante(desacelerar, 0.5, 1);
                        }

                        if (angle.getYaw(AngleUnit.DEGREES) > -50) {
                            NewRhino.adelante(desacelerar, 1, 0.5);
                        }

                    }

                    if (200 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 300) {

                        sleep(200);

                        if (200 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 300) {
                            NewRhino.paroChasis();
                            NewRhino.resetChasis();
                            desacelerar = 1;
                            straight = false;
                        }
                    }
                }

                //PLACE THE CONE

                servo1.setPosition(1);
                servo2.setPosition(1);
                sleep(1500);
                servo1.setPosition(0.5);
                servo2.setPosition(0.5);

                step = 12;

            }

            if (step == 12) {

                //ATRAS
                straight = true;

                while (straight == true && opModeIsActive()) {

                    YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                    //accion

                    if (rightmotor.getCurrentPosition() > -200) {

                        if (46 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 48) {
                            NewRhino.atras(0.2 * desacelerar, 1, 0.8);
                        }

                        if (46 > angle.getYaw(AngleUnit.DEGREES)) {
                            NewRhino.atras(0.2 * desacelerar, 1, 0.5);
                        }

                        if (angle.getYaw(AngleUnit.DEGREES) > 48) {
                            NewRhino.atras(0.2 * desacelerar, 0.5, 1);
                        }

                        //frenado

                        if (rightmotor.getCurrentPosition() < -150) {
                            desacelerar = 0.5;
                        }

                    }

                    //correccion

                    if (rightmotor.getCurrentPosition() < -250) {

                        if (46 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < 48) {
                            NewRhino.adelante(0.2, 1, 1);
                        }

                        if (46 > angle.getYaw(AngleUnit.DEGREES)) {
                            NewRhino.adelante(0.2, 0.5, 1);
                        }

                        if (angle.getYaw(AngleUnit.DEGREES) > 48) {
                            NewRhino.adelante(0.2, 1, 0.5);
                        }

                    }

                    //stop

                    if (-200 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -250) {

                        sleep(200);

                        if (-200 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -250) {
                            NewRhino.paroChasis();
                            NewRhino.resetChasis();
                            desacelerar = 1;
                            straight = false;

                        }
                    }
                }

                NewRhino.eleAbajo(1);

                step = 13;

            }

            if (step == 13) {

                //TURN TO THE CONES

                turning = true;

                while (turning == true && opModeIsActive()) {

                    YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

                    //check elevator

                    if (elevador2.getCurrentPosition() <= 500) {
                        NewRhino.paroEle();
                    }

                    if (-90 > turn.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.giroIzq(0.3, 1, 1);
                    }

                    if (turn.getYaw(AngleUnit.DEGREES) > -75) {
                        NewRhino.giroDer(0.3, 1, 1);
                    }

                    if (-90 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -65) {

                        sleep(200);

                        if (-90 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -65) {

                            NewRhino.paroChasis();
                            NewRhino.resetChasis();
                            desacelerar = 1;
                            turning = false;
                        }
                    }
                }

                step = 14;
            }

            if (step == 14) {

                //TURN TO THE CONES

                turning = true;

                while (turning == true && opModeIsActive()) {

                    YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

                    if (-93 > turn.getYaw(AngleUnit.DEGREES)) {
                        NewRhino.giroIzq(0.2, 1, 1);
                    }

                    if (turn.getYaw(AngleUnit.DEGREES) > -87) {
                        NewRhino.giroDer(0.2, 1, 1);
                    }

                    if (-93 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -87) {

                        sleep(200);

                        if (-93 < turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) < -87) {

                            NewRhino.paroChasis();
                            NewRhino.resetChasis();
                            desacelerar = 1;
                            turning = false;
                        }
                    }
                }

                step = 8;

            }

            if (step == 8) {

                if(zone == 1 || zone == 2) {

                    desacelerar = 1;

                    //ADELANTE
                    straight = true;

                    while (straight == true && opModeIsActive()) {

                        YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                        //frenado

                        if (rightmotor.getCurrentPosition() > 200) {
                            desacelerar = 0.2;
                        }

                        //correccion

                        if (rightmotor.getCurrentPosition() > 900) {

                            if (-92 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -88) {
                                NewRhino.atras(0.2, 1, 1);
                            }

                            if (-92 > angle.getYaw(AngleUnit.DEGREES)) {
                                NewRhino.atras(0.2, 1, 0.5);
                            }

                            if (angle.getYaw(AngleUnit.DEGREES) > -88) {
                                NewRhino.atras(0.2, 0.5, 1);
                            }

                        }

                        //accion

                        if (rightmotor.getCurrentPosition() < 800) {

                            if (-92 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -88) {
                                NewRhino.adelante(desacelerar, 1, 1);
                            }

                            if (-92 > angle.getYaw(AngleUnit.DEGREES)) {
                                NewRhino.adelante(desacelerar, 0.5, 1);
                            }

                            if (angle.getYaw(AngleUnit.DEGREES) > -88) {
                                NewRhino.adelante(desacelerar, 1, 0.5);
                            }

                        }

                        if (800 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 900) {

                            sleep(200);

                            if (800 < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < 900) {
                                NewRhino.paroChasis();
                                NewRhino.resetChasis();
                                desacelerar = 1;
                                straight = false;
                            }
                        }
                    }

                    //GRAB THE CONE

                    NewRhino.eleAbajo(1);

                    servo1.setPosition(0);
                    servo2.setPosition(0);

                    while (elevador2.getCurrentPosition() > 500) {

                        if (elevador2.getCurrentPosition() < 500) {
                            NewRhino.paroEle();
                        }

                    }

                    sleep(700);

                    servo1.setPosition(0.5);
                    servo2.setPosition(0.5);

                    NewRhino.eleArriba(1);

                    while (elevador2.getCurrentPosition() <= 400 && opModeIsActive()) {
                        NewRhino.paroChasis();
                    }

                }

                step = 15;

            }

        }

        //PARKING

        if (step == 15) {

            if (zone == 1) {

                NewRhino.paroChasis();
                NewRhino.resetChasis();

            }

            if (zone == 2) {

                desacelerar = 0.6;

                //ATRAS
                straight = true;

                while (straight == true && opModeIsActive()) {

                    YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                    //check elevator
                    if (elevador2.getCurrentPosition() > 600) {
                        NewRhino.paroEle();
                    }

                    //frenado

                    if (rightmotor.getCurrentPosition() < -100) {
                        desacelerar = 0.2;
                    }

                    //accion

                    if (rightmotor.getCurrentPosition() > -700) {

                        if (-94 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -90) {
                            NewRhino.atras(desacelerar, 1, 0.8);
                        }

                        if (-94 > angle.getYaw(AngleUnit.DEGREES)) {
                            NewRhino.atras(desacelerar, 1, 0.5);
                        }

                        if (angle.getYaw(AngleUnit.DEGREES) > -90) {
                            NewRhino.atras(desacelerar, 0.5, 1);
                        }

                    }

                    //correccion

                    if (rightmotor.getCurrentPosition() < -800) {

                        if (-94 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -90) {
                            NewRhino.adelante(0.2, 1, 1);
                        }

                        if (-94 > angle.getYaw(AngleUnit.DEGREES)) {
                            NewRhino.adelante(0.2, 0.5, 1);
                        }

                        if (angle.getYaw(AngleUnit.DEGREES) > -90) {
                            NewRhino.adelante(0.2, 1, 0.5);
                        }

                    }

                    //stop

                    if (-700 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -800) {

                        sleep(200);

                        if (-700 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -800) {
                            NewRhino.paroChasis();
                            NewRhino.resetChasis();
                            desacelerar = 1;
                            straight = false;

                        }
                    }
                }

            }

            if (zone == 3) {

                step = 20;

                if (step == 20) {

                    desacelerar = 0.6;

                    //ATRAS
                    straight = true;

                    while (straight == true && opModeIsActive()) {

                        YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                        //check elevator
                        if (elevador2.getCurrentPosition() > 600) {
                            NewRhino.paroEle();
                        }

                        //frenado

                        if (rightmotor.getCurrentPosition() < -100) {
                            desacelerar = 0.2;
                        }

                        //accion

                        if (rightmotor.getCurrentPosition() > -600) {

                            if (-94 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -90) {
                                NewRhino.atras(desacelerar, 1, 0.8);
                            }

                            if (-94 > angle.getYaw(AngleUnit.DEGREES)) {
                                NewRhino.atras(desacelerar, 1, 0.5);
                            }

                            if (angle.getYaw(AngleUnit.DEGREES) > -90) {
                                NewRhino.atras(desacelerar, 0.5, 1);
                            }

                        }

                        //correccion

                        if (rightmotor.getCurrentPosition() < -700) {

                            if (-94 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -90) {
                                NewRhino.adelante(0.2, 1, 1);
                            }

                            if (-94 > angle.getYaw(AngleUnit.DEGREES)) {
                                NewRhino.adelante(0.2, 0.5, 1);
                            }

                            if (angle.getYaw(AngleUnit.DEGREES) > -90) {
                                NewRhino.adelante(0.2, 1, 0.5);
                            }

                        }

                        //stop

                        if (-600 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -700) {

                            sleep(200);

                            if (-600 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -700) {
                                NewRhino.paroChasis();
                                NewRhino.resetChasis();
                                desacelerar = 1;
                                straight = false;

                            }
                        }
                    }

                    step = 21;

                }

                if (step == 21) {

                    desacelerar = 0.6;

                    //ATRAS
                    straight = true;

                    while (straight == true && opModeIsActive()) {

                        YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

                        //frenado

                        if (rightmotor.getCurrentPosition() < -100) {
                            desacelerar = 0.2;
                        }

                        //accion

                        if (rightmotor.getCurrentPosition() > -600) {

                            if (-94 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -90) {
                                NewRhino.atras(desacelerar, 1, 0.8);
                            }

                            if (-94 > angle.getYaw(AngleUnit.DEGREES)) {
                                NewRhino.atras(desacelerar, 1, 0.5);
                            }

                            if (angle.getYaw(AngleUnit.DEGREES) > -90) {
                                NewRhino.atras(desacelerar, 0.5, 1);
                            }

                        }

                        //correccion

                        if (rightmotor.getCurrentPosition() < -700) {

                            if (-94 < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < -90) {
                                NewRhino.adelante(0.2, 1, 1);
                            }

                            if (-94 > angle.getYaw(AngleUnit.DEGREES)) {
                                NewRhino.adelante(0.2, 0.5, 1);
                            }

                            if (angle.getYaw(AngleUnit.DEGREES) > -90) {
                                NewRhino.adelante(0.2, 1, 0.5);
                            }

                        }

                        //stop

                        if (-600 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -700) {

                            sleep(200);

                            if (-600 > rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() > -700) {
                                NewRhino.paroChasis();
                                NewRhino.resetChasis();
                                desacelerar = 1;
                                straight = false;

                            }
                        }
                    }
                }
            }
        }



        //SYSTEM SECURITY
        NewRhino.paroEle();

        //SEND INFO
        while (opModeIsActive()) {

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            AngularVelocity angularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);

            telemetry.addData("logo Direction (set with bumpers)", logoFacingDirections[logoFacingDirectionPosition]);
            telemetry.addData("usb Direction (set with triggers)", usbFacingDirections[usbFacingDirectionPosition] + "\n");

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
            telemetry.addData("Yaw (Z) velocity", "%.2f Deg/Sec", angularVelocity.zRotationRate);
            telemetry.addData("Pitch (X) velocity", "%.2f Deg/Sec", angularVelocity.xRotationRate);
            telemetry.addData("Roll (Y) velocity", "%.2f Deg/Sec", angularVelocity.yRotationRate);

            telemetry.addLine("Facing: " + posicion + "\n");

            telemetry.addLine("chasis: " + rightmotor.getCurrentPosition());
            telemetry.addLine("power: " + rightmotor.getPower());
            telemetry.addLine("elevador: " + elevador1.getCurrentPosition());
            telemetry.addLine("power: " + elevador1.getPower() + "\n");

            telemetry.addLine("zone: " + zone + "\n");
            telemetry.addLine("step: " + step);

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
