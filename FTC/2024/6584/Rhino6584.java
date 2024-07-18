/*
2024 Constructor
*/


package org.firstinspires.ftc.teamcode.Eliud;



import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;



public class Rhino6584 {



    //CHASIS
    DcMotor FR;
    DcMotor FL;
    DcMotor BR;
    DcMotor BL;



    //SENSORS
    IMU imu;



    //VARIABLES
    int adelante = -1;
    int atras = 1;



    public Rhino6584(DcMotor FR, DcMotor FL, DcMotor BR, DcMotor BL, IMU imu) {

        //CHASIS
        this.FR = FR;
        this.FL = FL;
        this.BR = BR;
        this.BL = BL;

        //SENSORES
        this.imu = imu;

    }

    public void adelante(int distanciaCM, double power) {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double diametroCM = 8;

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

        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(FR.isBusy()){}

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void atras(int distanciaCM, double power) {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double diametroCM = 8;

        int ticksPerRev = 300;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(target * atras);
        FL.setTargetPosition(target * atras);
        BR.setTargetPosition(target * atras);
        BL.setTargetPosition(target * atras);

        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(FR.isBusy()){}

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void derecha(int distanciaCM, double power) {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double diametroCM = 8;

        int ticksPerRev = 300;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(target * atras);
        FL.setTargetPosition(target * adelante);
        BR.setTargetPosition(target * adelante);
        BL.setTargetPosition(target * atras);

        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(FR.isBusy()){}

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void izquierda(int distanciaCM, double power) {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double diametroCM = 8;

        int ticksPerRev = 300;

        double conversion = (distanciaCM * ticksPerRev)/(Math.PI * diametroCM);

        int target = (int)conversion;

        FR.setTargetPosition(target * adelante);
        FL.setTargetPosition(target * atras);
        BR.setTargetPosition(target * atras);
        BL.setTargetPosition(target * adelante);

        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(FR.isBusy()){}

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
