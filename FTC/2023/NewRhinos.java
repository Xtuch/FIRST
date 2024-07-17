package org.firstinspires.ftc.teamcode.FTC.autonomo.samples;

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


@Autonomous(name="NewRhinos", group="Robot")
@Disabled
public class NewRhinos extends LinearOpMode {

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
    String posicion;
    boolean turning = false;
    boolean straight = false;
    int longstraight;
    int errorIzq;
    int errorDer;
    int correccion;

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

        updateOrientation();

    }

    public NewRhinos(DcMotor rightmotor, DcMotor leftmotor, DcMotor backrightmotor, DcMotor backleftmotor, DcMotor elevador1, DcMotor elevador2, Servo servo1, Servo servo2){
        this.rightmotor = rightmotor;
        this.leftmotor = leftmotor;
        this.backrightmotor = backrightmotor;
        this.backleftmotor = backleftmotor;
        this.elevador1 = elevador1;
        this.elevador2 = elevador2;
        this.servo1 = servo1;
        this.servo2 = servo2;
    }

    //RESETS

    public void resetTotal(){
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
    }

    public void resetChasis(){

        rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backrightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backrightmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backleftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void resetElevador(){

        elevador1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevador2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elevador1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevador2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    //PAROS

    public void paroTotal(){
        leftmotor.setPower(0);
        rightmotor.setPower(0);
        backleftmotor.setPower(0);
        backrightmotor.setPower(0);
        elevador1.setPower(0);
        elevador2.setPower(0);
        servo1.setPosition(0.5);
        servo2.setPosition(0.5);
    }

    public void paroChasis(){
        leftmotor.setPower(0);
        rightmotor.setPower(0);
        backleftmotor.setPower(0);
        backrightmotor.setPower(0);
    }

    public void paroEle(){
        elevador1.setPower(0);
        elevador2.setPower(0);
    }

    //MOVIMIENTOS

    public void adelante(double porVel, double ajusteIzq, double ajusteDer){

        leftmotor.setPower(1 * porVel * ajusteIzq);
        rightmotor.setPower(1 * porVel * ajusteDer);
        backleftmotor.setPower(1 * porVel * ajusteIzq);
        backrightmotor.setPower(1 * porVel * ajusteDer);

    }

    public void atras(double porVel, double ajusteIzq, double ajusteDer){

        leftmotor.setPower(-1 * porVel * ajusteIzq);
        rightmotor.setPower(-1 * porVel * ajusteDer);
        backleftmotor.setPower(-1 * porVel * ajusteIzq);
        backrightmotor.setPower(-1 * porVel * ajusteDer);

    }

    public void giroDer(double porVel, double ajusteIzq, double ajusteDer){

        leftmotor.setPower(1 * porVel * ajusteIzq);
        rightmotor.setPower(-1 * porVel * ajusteDer);
        backleftmotor.setPower(1 * porVel * ajusteIzq);
        backrightmotor.setPower(-1 * porVel * ajusteDer);

    }

    public void giroIzq(double porVel, double ajusteIzq, double ajusteDer){

        leftmotor.setPower(-1 * porVel * ajusteIzq);
        rightmotor.setPower(1 * porVel * ajusteDer);
        backleftmotor.setPower(-1 * porVel * ajusteIzq);
        backrightmotor.setPower(1 * porVel * ajusteDer);

    }

    public void mecaDer(double porVel, double ajusteIzq, double ajusteDer){

        leftmotor.setPower(1 * porVel * ajusteIzq);
        rightmotor.setPower(-1 * porVel * ajusteDer);
        backleftmotor.setPower(-1 * porVel * ajusteIzq);
        backrightmotor.setPower(1 * porVel * ajusteDer);

    }

    public void mecaIzq(double porVel, double ajusteIzq, double ajusteDer){

        leftmotor.setPower(-1 * porVel * ajusteIzq);
        rightmotor.setPower(1 * porVel * ajusteDer);
        backleftmotor.setPower(1 * porVel * ajusteIzq);
        backrightmotor.setPower(-1 * porVel * ajusteDer);

    }

    public void eleArriba(double porVel){

        elevador1.setPower(1 * porVel);
        elevador2.setPower(1 * porVel);

    }

    public void eleAbajo(double porVel){

        elevador1.setPower(-1 * porVel);
        elevador2.setPower(-1 * porVel);

    }

    //CORRECCIONES

    public void ADELANTE(double porVel, double velCorreccion, int limMen, int limMay, int yawNeg, int yawPos){

        straight = true;

        while (straight == true && opModeIsActive()) {

            YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

            //correción

            if (rightmotor.getCurrentPosition() > limMay) {

                if (yawNeg < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < yawPos) {
                    atras(velCorreccion, 1, 1);
                }

                if (yawNeg > angle.getYaw(AngleUnit.DEGREES)) {
                    atras(velCorreccion, velCorreccion * 0.8, 1);
                }

                if (angle.getYaw(AngleUnit.DEGREES) > yawPos) {
                    atras(velCorreccion, 1, velCorreccion * 0.8);
                }

            }

            //acción

            if (rightmotor.getCurrentPosition() < limMen) {

                if (yawNeg < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < yawPos) {
                    adelante(porVel, 1, 1);
                }

                if (yawNeg > angle.getYaw(AngleUnit.DEGREES)) {
                    adelante(porVel, 1, porVel * 0.8);
                }

                if (angle.getYaw(AngleUnit.DEGREES) > yawPos) {
                    adelante(porVel, porVel * 0.8, 1);
                }

            }

            //stop

            if (limMen < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < limMay) {

                sleep(200);

                if (limMen < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < limMay) {
                    paroChasis();
                    resetChasis();
                    straight = false;
                }
            }
        }
    }

    public void ATRAS(double porVel, double velCorreccion, int limMen, int limMay, int yawNeg, int yawPos){

        straight = true;

        while (straight == true && opModeIsActive()) {

            YawPitchRollAngles angle = imu.getRobotYawPitchRollAngles();

            //acción

            if (rightmotor.getCurrentPosition() > limMay) {

                if (yawNeg < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < yawPos) {
                    atras(porVel, 1, 1);
                }

                if (yawNeg > angle.getYaw(AngleUnit.DEGREES)) {
                    atras(porVel, porVel * 0.8, 1);
                }

                if (angle.getYaw(AngleUnit.DEGREES) > yawPos) {
                    atras(porVel, 1, porVel * 0.8);
                }

            }

            //corrección

            if (rightmotor.getCurrentPosition() < limMen) {

                if (yawNeg < angle.getYaw(AngleUnit.DEGREES) && angle.getYaw(AngleUnit.DEGREES) < yawPos) {
                    adelante(velCorreccion, 1, 1);
                }

                if (yawNeg > angle.getYaw(AngleUnit.DEGREES)) {
                    adelante(velCorreccion, 1, velCorreccion * 0.8);
                }

                if (angle.getYaw(AngleUnit.DEGREES) > yawPos) {
                    adelante(velCorreccion, velCorreccion * 0.8, 1);
                }

            }

            //stop

            if (limMen < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < limMay) {

                sleep(200);

                if (limMen < rightmotor.getCurrentPosition() && rightmotor.getCurrentPosition() < limMay) {
                    paroChasis();
                    resetChasis();
                    straight = false;
                }
            }
        }
    }

    public void VUELTA(double porVel, double velCorreccion, int yawMen, int yawMay){

        turning = true;

        while (turning == true && opModeIsActive()) {

            YawPitchRollAngles turn = imu.getRobotYawPitchRollAngles();

            //acción

            if (yawMen >= turn.getYaw(AngleUnit.DEGREES)) {
                giroIzq(porVel, 1, 1);
            }

            //correción

            if (turn.getYaw(AngleUnit.DEGREES) >= yawMay) {
                giroDer(velCorreccion, 1, 1);
            }

            //stop

            if (yawMen <= turn.getYaw(AngleUnit.DEGREES) && turn.getYaw(AngleUnit.DEGREES) <= yawMay) {

                sleep(200);

                YawPitchRollAngles check = imu.getRobotYawPitchRollAngles();

                if (yawMen <= check.getYaw(AngleUnit.DEGREES) && check.getYaw(AngleUnit.DEGREES) <= yawMay) {

                    paroChasis();
                    resetChasis();
                    turning = false;

                }

            }
        }
    }

    public void updateOrientation() {
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
