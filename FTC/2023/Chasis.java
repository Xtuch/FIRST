/*
Chasis example
*/

package RhinoSharing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="CHASIS", group="Linear Opmode")
public class Chasis extends LinearOpMode {

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;
    private Servo garra;

    @Override
    public void runOpMode() {

        FR = hardwareMap.dcMotor.get("0");
        FL = hardwareMap.dcMotor.get("1");
        BR = hardwareMap.dcMotor.get("2");
        BL = hardwareMap.dcMotor.get("3");
        garra = hardwareMap.get(Servo.class, "5");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            FR.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
            BR.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x - 2 * gamepad1.right_stick_x);
            FL.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x - 2 * gamepad1.right_stick_x);
            BL.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);

            if (gamepad1.left_bumper) {garra.setPosition(1);}
            else if (gamepad1.right_bumper) {garra.setPosition(0);}
            else {garra.setPosition(0.5);}

        }
    }
}
