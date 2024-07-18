/*
Succesful PID Test for FTC movement
*/

package org.firstinspires.ftc.teamcode.FTC.Eliud;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Prueba PID 2", group="Robot")
//@Disabled
public class PIDFunciona extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    double integralSum = 0;
    double kp = 0.00035; //Constante posicion
    double ki = 0.00005; //Constante integral
    double kd = 0; //Constante derivativa

    private double lasterror = 0;

    private ElapsedTime runtime = new ElapsedTime(); //Tiempo corriendo
    private ElapsedTime timer1 = new ElapsedTime(); //

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.

    static final double COUNTS_PER_MOTOR_REV = 1440;    // "encoder counts"
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // Aqui van las relaciones en "ratio"
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // Diametro de llanta
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415); //calcula cuantos "counts necesita para recorrer una pulgada
    static final double DRIVE_SPEED = 0.6; //velocidad para rectas
    static final double TURN_SPEED = 0.5; //velocidad en curvas

    @Override
    public void runOpMode() {

        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //revisar que haya reseteado (que los encoders esten en 0)

        telemetry.addData("Starting at", "%7d :%7d",
                leftDrive.getCurrentPosition(),
                rightDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();

        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        //RECORRIDO:

        encoderDrive( 48, 25.0);  // S1: Forward 47 Inches with 5 Sec timeout

    }

    public double PIDControl(double objetivo, double posicionActual) {

        double distanciaFaltante = objetivo - posicionActual; //calcula distancia faltante para objetivo

        integralSum += distanciaFaltante * timer1.seconds();

        double derivative = (distanciaFaltante - lasterror) / timer1.seconds();

        lasterror = distanciaFaltante;

        timer1.reset();

        double output = (distanciaFaltante * kp) + (derivative * kd) + (integralSum * ki);
        return output;

    }

    /*

    la funcion: "encoderDrive" va a tomar varias cosas:

    -la potencia a designar a los motores dependiendo de lo que arroje la funcion: "PIDControl"
    -un input que requiere distancia a moverse (en pulgadas, pero se puede modificar en la declaracion)
    -un input que pide un tiempo maximo de ejecucion

    el proposito de la funcion es administrar la potencia que da "PIDControl" y dispensarla correctamente para recorrer la distancia adecuda

     */

    public void encoderDrive(double leftInches, double timeoutS) {

        int newLeftTarget;

        if (opModeIsActive()) {

            newLeftTarget = leftDrive.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);

            leftDrive.setTargetPosition(newLeftTarget);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset(); //empezamos un timer para el tiempo máximo

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.

            while (opModeIsActive() && (runtime.seconds() < timeoutS)) {

                double power = PIDControl(newLeftTarget, leftDrive.getCurrentPosition());

                leftDrive.setPower(power);

                telemetry.addLine("Running to: " + newLeftTarget);
                telemetry.addLine("Currently at: " + leftDrive.getCurrentPosition());
                telemetry.addLine("Power: " + power);
                telemetry.update();
            }

            leftDrive.setPower(0);

            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
}
