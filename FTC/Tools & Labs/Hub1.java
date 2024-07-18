/*
Zone detection lab using color sensor to point depending on color input
*/

package RhinoSharing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="hub1", group="Linear Opmode")
public class hub1 extends LinearOpMode {
    DcMotor motor;
    Servo servo;
    ColorRangeSensor sensor;
    int zona;
    int zonaUno;
    int zonaDos;
    int zonaTres;


    @Override
    public void runOpMode() {

        zonaUno =54022;

        motor = hardwareMap.get(DcMotor.class, "0");
        servo = hardwareMap.get(Servo.class, "5");
        sensor = hardwareMap.get(ColorRangeSensor.class, "sensor");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        servo.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while(opModeIsActive()){

            NormalizedRGBA colors = sensor.getNormalizedColors();

            if (colors.red > colors.blue && colors.red > colors.green) {

                zona = zonaUno;

            }
            if (colors.blue > colors.red && colors.blue > colors.green) {
                zona = zonaDos;

            }
            if (colors.green > colors.blue && colors.green > colors.red) {
                zona = zonaTres;

            }

            motor.setTargetPosition(zona);
            motor.setPower(1);

            while(motor.getCurrentPosition() != zona) {
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            motor.setPower(0);

            if(gamepad1.x) {
                servo.setPosition(1);
            }
            if(gamepad1.a) {
                servo.setPosition(0.5);
            }
            if(gamepad1.y) {
                servo.setPosition(0);
            }

            telemetry.addLine("zona: " + zona);
            telemetry.addLine("color: " + colors);
            telemetry.addLine("posicion motor: " + motor.getCurrentPosition());

        }

    }
}
