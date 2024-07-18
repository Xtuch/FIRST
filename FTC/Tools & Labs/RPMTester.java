package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="RPMTester", group="Linear Opmode")
public class RPMTester extends LinearOpMode {

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;

    ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() {

        FR = hardwareMap.dcMotor.get("0");
        FL = hardwareMap.dcMotor.get("1");
        BR = hardwareMap.dcMotor.get("2");
        BL = hardwareMap.dcMotor.get("3");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        time.reset();

        while(opModeIsActive()) {

            FR.setPower(1);
            FL.setPower(1);
            BR.setPower(1);
            BL.setPower(1);

            if(time.seconds() == 60){

                int FRTicksInOneMinute = FR.getCurrentPosition();
                int FLTicksInOneMinute = FL.getCurrentPosition();
                int BRTicksInOneMinute = BR.getCurrentPosition();
                int BLTicksInOneMinute = BL.getCurrentPosition();

                telemetry.addLine("FR: " + FRTicksInOneMinute);
                telemetry.addLine("FL: " + FLTicksInOneMinute);
                telemetry.addLine("BR: " + BRTicksInOneMinute);
                telemetry.addLine("BL: " + BLTicksInOneMinute);
                telemetry.addLine("time: " + time);
                telemetry.update();

            }

            if(time.seconds() < 60){

                telemetry.addLine("FR: " + FR.getCurrentPosition());
                telemetry.addLine("FL: " + FL.getCurrentPosition());
                telemetry.addLine("BR: " + BR.getCurrentPosition());
                telemetry.addLine("BL: " + BL.getCurrentPosition());
                telemetry.addLine("time: " + time);
                telemetry.update();

            }
        }
    }
}
