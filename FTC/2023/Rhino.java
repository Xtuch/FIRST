/*
New functions ideas
*/

package org.firstinspires.ftc.teamcode.Eliud;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Rhino {

    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;
    public Servo garra;
    public IMU imu;

    public Rhino(DcMotor FR, DcMotor FL, DcMotor BR, DcMotor BL, Servo garra, IMU imu) {

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

    }

    public void mapping() {

        FR = hardwareMap.dcMotor.get("0");
        FL = hardwareMap.dcMotor.get("1");
        BR = hardwareMap.dcMotor.get("2");
        BL = hardwareMap.dcMotor.get("3");
        garra = hardwareMap.get(Servo.class, "5");
        imu = hardwareMap.get(IMU.class, "imu");

    }

    public void autoSetup() {

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void teleSetup() {

        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void drive(int distanciaCM, double power, double angulo) {

        double diametroCM = 10;
        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev) / (Math.PI * diametroCM);

        int target = (int) conversion;

        FR.setTargetPosition(target);
        FL.setTargetPosition(target);
        BR.setTargetPosition(target);
        BL.setTargetPosition(target);

        while (FR.isBusy() && linearOpMode.opModeIsActive()) {

            //talvez necesitamos esto...
            //double error = angulo - currentAngle.getYaw(AngleUnit.DEGREES);
            //double correccion = 1/Math.tan(currentAngle.getYaw(AngleUnit.DEGREES));
            YawPitchRollAngles getAngle = imu.getRobotYawPitchRollAngles();
            double currentAngle = getAngle.getYaw(AngleUnit.DEGREES);

            if (currentAngle < angulo) {

                double correccionR = 0.5;
                FR.setPower(power * correccionR);
                BR.setPower(power * correccionR);

            } else {

                double correccionR = 0;
                FR.setPower(power * correccionR);
                BR.setPower(power * correccionR);

            }

            if (currentAngle < angulo) {

                double correccionL = 0.5;
                FL.setPower(power * correccionL);
                BL.setPower(power * correccionL);

            } else {

                double correccionL = 0;
                FL.setPower(power * correccionL);
                BL.setPower(power * correccionL);

            }
            /*
            FR.setPower(power * correccionR);
            FL.setPower(power * correccionL);
            BR.setPower(power * correccionR);
            BL.setPower(power * correccionL);
            */

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

    public void turn(int targetDegree, double power) {

        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        boolean turning = true;

        while (turning) {

            YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();

            if (currentAngle.getYaw(AngleUnit.DEGREES) == targetDegree) {

                turning = false;

            }

        }

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }

    public double getYaw() {

        YawPitchRollAngles currentAngle = imu.getRobotYawPitchRollAngles();

        double yaw = currentAngle.getYaw(AngleUnit.DEGREES);

        return yaw;

    }

    public void secureDrive(int distanciaCM, double power) {

        double diametroCM = 10;

        int ticksPerRev = 570;

        double conversion = (distanciaCM * ticksPerRev) / (Math.PI * diametroCM);

        int target = (int) conversion;

        FR.setTargetPosition(-target);
        FL.setTargetPosition(target);
        BR.setTargetPosition(-target);
        BL.setTargetPosition(target);

        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        while (FR.getCurrentPosition() != target && linearOpMode.opModeIsActive()) {

            //talvez necesitamos esto...
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

}
