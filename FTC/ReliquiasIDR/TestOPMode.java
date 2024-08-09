package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Test", group="Linear Opmode")
public class TestOPMode extends LinearOpMode {
    
    
    @Override
    public void runOpMode(){
        waitForStart();
        DcMotor m1 = hardwareMap.dcMotor.get("m1");
        DcMotor m2 = hardwareMap.dcMotor.get("m2");
        DcMotor m3 = hardwareMap.dcMotor.get("m3");
        while(opModeIsActive()){
                telemetry.addLine(Double.toString(gamepad1.left_stick_x));
                telemetry.addLine(Double.toString(gamepad1.left_stick_y));
                telemetry.addLine(Double.toString(gamepad1.right_stick_x));
                telemetry.addLine(Double.toString(gamepad1.right_stick_y));
                telemetry.update();
                m1.setPower(0.07);
                m2.setPower(0.389);
                m3.setPower(-0.389);
        }
    }
}
