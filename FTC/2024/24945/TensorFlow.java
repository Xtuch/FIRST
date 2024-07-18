/*
Pure TensorFlow
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;

@TeleOp(name = "TensorFlow", group = "Concept")
public class TensorFlow extends LinearOpMode {

    private static final boolean USE_WEBCAM = false;  // true for webcam, false for phone camera
    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    private boolean isVisionActivated = true;
    private int zone = 1;

    @Override
    public void runOpMode() {

        initTfod();

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive() && isVisionActivated == true) {

                double[] coordinates = getcoordinates();
                telemetry.addLine("Zona " + zone);
                telemetry.addLine("X " + coordinates[0]);
                telemetry.update();

                if (coordinates[0] < 100) {//LEFT

                    zone = 1;

                } else if (coordinates[0] > 500) {//RIGHT

                    zone = 2;

                } else {//MIDDLE

                    zone = 3;

                }
                sleep(20);
            }
        }
        visionPortal.close();

    }

    private void initTfod() {

        tfod = TfodProcessor.easyCreateWithDefaults();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, tfod);
        }

    }

    double[] getcoordinates() {

        double[] coordenadas = {0, 0};

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        for (Recognition recognition : currentRecognitions) {
            coordenadas[0] = (recognition.getLeft() + recognition.getRight()) / 2; //center X
            coordenadas[1] = (recognition.getTop() + recognition.getBottom()) / 2; //center Y

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", coordenadas[0], coordenadas[1]);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());

        }

        return coordenadas;

    }
}
