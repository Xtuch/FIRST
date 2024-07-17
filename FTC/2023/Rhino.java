/*
FTC 2024 Constructor
*/

package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Rhino {

    //CHASIS
    DcMotor FR;
    DcMotor FL;
    DcMotor BR;
    DcMotor BL;

    //MOTOR TOOLS
    DcMotor Barredora;
    DcMotor ElevadorDerecho;
    DcMotor ElevadorIzquierdo;
    DcMotor Gancho;

    //SERVO TOOLS
    Servo Cremallera;
    Servo Avion;
    Servo BaseBrazo;
    Servo garraIzq;
    Servo garraDer;

    //SENSORS
    IMU imu;
    DistanceSensor sensorDeDistancia;
    
    //VARIABLES
    
    int adelante = -1;
    int atras = 1;

    public Rhino(DcMotor FR, DcMotor FL, DcMotor BR, DcMotor BL,
                 DcMotor Barredora, DcMotor ElevadorDerecho, DcMotor ElevadorIzquierdo, DcMotor Gancho,
                 Servo Cremallera, Servo Avion, Servo BaseBrazo, Servo garraIzq, Servo garraDer,  IMU imu) {

        this.FR = FR;
        this.FL = FL;
        this.BR = BR;
        this.BL = BL;
        this.imu = imu;

    }

    public void resetChasis() {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Barredora.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ElevadorDerecho.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ElevadorIzquierdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Gancho.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void mapping(){

        FL = hardwareMap.get(DcMotor.class, "0");
        FR = hardwareMap.get(DcMotor.class, "1");
        BL = hardwareMap.get(DcMotor.class, "2");
        BR = hardwareMap.get(DcMotor.class, "3");
        Barredora = hardwareMap.get(DcMotor.class, "m0");
        ElevadorDerecho = hardwareMap.get(DcMotor.class, "m1");
        ElevadorIzquierdo = hardwareMap.get(DcMotor.class, "m2");
        Gancho = hardwareMap.get(DcMotor.class, "m3");
        BaseBrazo = hardwareMap.get(Servo.class, "s0");
        garraDer = hardwareMap.get(Servo.class, "s1");
        garraIzq = hardwareMap.get(Servo.class, "s2");
        Avion = hardwareMap.get(Servo.class, "s3");
        Cremallera = hardwareMap.get(Servo.class, "s4");
        imu = hardwareMap.get(IMU.class, "imu");
    }

    public void setDirections(){
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        ElevadorDerecho.setDirection(DcMotorSimple.Direction.REVERSE);
        garraDer.setDirection(Servo.Direction.REVERSE);
    }

    public void autoSetup() {

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Barredora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ElevadorDerecho.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ElevadorIzquierdo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Gancho.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void teleSetup() {

        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Barredora.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ElevadorDerecho.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ElevadorIzquierdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Gancho.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void adelante(int distanciaCM, double power) {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double diametroCM = 10;

        int ticksPerRev = 300;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(target * adelante);
        FL.setTargetPosition(target * adelante);
        BR.setTargetPosition(target * adelante);
        BL.setTargetPosition(target * adelante);
        
        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        while ( (target - 10) < FR.getCurrentPosition() || FR.getCurrentPosition() < (target + 10)) {

            //YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));

            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }

    public void atras(int distanciaCM, double power) {

        double diametroCM = 10;

        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(-target);
        FL.setTargetPosition(target);
        BR.setTargetPosition(-target);
        BL.setTargetPosition(target);

        while (FR.isBusy()) {

            //YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));

            FR.setPower(power);
            FL.setPower(power);
            BR.setPower(power);
            BL.setPower(power);

            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }


    public void izquierda(int distanciaCM, double power) {

        double diametroCM = 10;

        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(target);
        FL.setTargetPosition(target);
        BR.setTargetPosition(-target);
        BL.setTargetPosition(-target);

        while (FR.isBusy()) {

            //YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));

            FR.setPower(power);
            FL.setPower(power);
            BR.setPower(power);
            BL.setPower(power);

            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }

    public void derecha(int distanciaCM, double power) {

        double diametroCM = 10;

        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(-target);
        FL.setTargetPosition(-target);
        BR.setTargetPosition(target);
        BL.setTargetPosition(target);

        while (FR.isBusy()) {

            //YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));

            FR.setPower(power);
            FL.setPower(power);
            BR.setPower(power);
            BL.setPower(power);

            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }

    public void rightTurn(int distanciaCM, double power) {

        double diametroCM = 10;

        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(-target);
        FL.setTargetPosition(-target);
        BR.setTargetPosition(-target);
        BL.setTargetPosition(-target);

        while (FR.isBusy()) {

            //YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));

            FR.setPower(power);
            FL.setPower(power);
            BR.setPower(power);
            BL.setPower(power);

            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }

    public void leftTurn(int distanciaCM, double power) {

        double diametroCM = 10;

        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(target);
        FL.setTargetPosition(target);
        BR.setTargetPosition(target);
        BL.setTargetPosition(target);

        while (FR.isBusy()) {

            //YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));

            FR.setPower(power);
            FL.setPower(power);
            BR.setPower(power);
            BL.setPower(power);

            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }

    public void turn(int grado, double power){

        // Define a margin of error for the target heading
        double headingTolerance = 10; // You can adjust this value based on your robot's accuracy requirements

        // Calculate the minimum and maximum acceptable heading
        double minHeading = grado - headingTolerance;
        double maxHeading = grado + headingTolerance;

        // Keep turning until the robot reaches the target heading
        while (true) {
            YawPitchRollAngles getAngles = imu.getRobotYawPitchRollAngles();
            double currentHeading = getAngles.getYaw(AngleUnit.DEGREES);

            if (currentHeading >= minHeading && currentHeading <= maxHeading) {
                // Robot is within the acceptable range of the target heading
                FR.setPower(0);
                FL.setPower(0);
                BR.setPower(0);
                BL.setPower(0); // Stop the motors or set power to zero
                break; // Exit the loop
            } else if (currentHeading < grado) {
                // Turn right (clockwise) to reach the target heading
                FR.setPower(power * adelante);
                FL.setPower(power * atras);
                BR.setPower(power * adelante);
                BL.setPower(power * atras);
            } else {
                // Turn left (counter-clockwise) to reach the target heading
                FR.setPower(power * atras);
                FL.setPower(power * adelante);
                BR.setPower(power * atras);
                BL.setPower(power * adelante);
            }
        }
    }
    
}
