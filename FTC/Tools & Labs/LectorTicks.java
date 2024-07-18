package RhinoSharing.Tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="lectorTicks", group="Robot")
@Disabled
public class LectorTicks extends LinearOpMode {

    //COMPONENTES ROBOT

    private DcMotor derecha;
    private DcMotor izquierda;
    private DcMotor derechaAtras;
    private DcMotor izquierdaAtras;

    @Override
    public void runOpMode() {

        //MAPEO

        derecha = hardwareMap.dcMotor.get("0");
        izquierda = hardwareMap.dcMotor.get("1");
        derechaAtras = hardwareMap.dcMotor.get("2");
        izquierdaAtras = hardwareMap.dcMotor.get("3");

        derecha.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        izquierda.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        derechaAtras.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        izquierdaAtras.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        while (opModeIsActive()){

            int ticksDer = derecha.getCurrentPosition();
            int ticksIzq = izquierda.getCurrentPosition();
            int ticksDerAtras = derechaAtras.getCurrentPosition();
            int ticksIzqAtras = izquierdaAtras.getCurrentPosition();
            telemetry.addLine("ticksDer: " + ticksDer);
            telemetry.addLine("ticksIzq: " + ticksIzq);
            telemetry.addLine("ticksDerAtras: " + ticksDerAtras);
            telemetry.addLine("ticksIzqAtras: " + ticksIzqAtras);
            telemetry.update();

            //TPR (Ticks Por Revolucion) 570 (REV HD Hex Motor)

        }

    }
}
