/*
Red Auto 2024
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.List;

@Autonomous(name = "AutoRojo", group = "Concept")

public class AutoRojo extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_20240214_164455.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/model_20240214_164455.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "Beacon",
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    DcMotor R;
    DcMotor L;
    DcMotor BR;
    DcMotor BL;
    DcMotor ElevadorR;
    DcMotor ElevadorL;
    Servo Garrita1;

    Servo Minipixel;

    //VARIABLES
    //int step = 1;
    //int zone = 2;
    public IMU imu;
    boolean pixel = false;

    @Override
    public void runOpMode() {

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        L = hardwareMap.get(DcMotor.class, "L");
        R = hardwareMap.get(DcMotor.class, "R");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        imu = hardwareMap.get(IMU.class, "imu");
        ElevadorR = hardwareMap.get(DcMotor.class, "ElevadorR");
        ElevadorL = hardwareMap.get(DcMotor.class, "ElevadorL");
        Minipixel = hardwareMap.get(Servo.class, "Minipixel");
        Garrita1 =hardwareMap.get(Servo.class, "Garrita1"); 


        R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ElevadorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ElevadorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ElevadorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ElevadorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        R.setDirection(DcMotorSimple.Direction.REVERSE);
        

        imu.resetYaw();

        waitForStart();


        //antes de que detecte
        
        Garrita1.setPosition(1);
        //Garrita2.setPosition(-0.1);

        Garrita1.setPosition(0.75);
        sleep(300);
        
        ElevadorR.setPower(-0.2);
        ElevadorL.setPower(-0.2);
        sleep(1600);
        
        ElevadorR.setPower(0);
        ElevadorL.setPower(0);
        
        BL.setPower(0.2);
        BR.setPower(0.2);
        sleep(800);
        L.setPower(0);
        R.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
        sleep(940);
        
        BL.setPower(-0.3);
        BR.setPower(0.3);
        sleep(150);
        L.setPower(0);
        R.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
        sleep(970);
        
        L.setPower(0.3);
        R.setPower(0.3);
        sleep(100);
        L.setPower(0);
        R.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
        sleep(540);

        for (int c =1; c <= 15; c++){
            pixel = telemetryTfod();
            if (pixel )  {
                break;
            }
            sleep(20);
        }

        if(!pixel){
            //Caso2
            
           
             BL.setPower(-0.2);
            BR.setPower(-0.2);
            sleep(310);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(-0.3);
            BR.setPower(0.3);
            sleep(510);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(0.2);
            BR.setPower(0.2);
            sleep(210);
            BL.setPower(0);
            BR.setPower(0);
            sleep(200);
            
            BL.setPower(0.3);
            BR.setPower(0.3);
            sleep(500);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(-0.3);
            BR.setPower(-0.3);
            sleep(400);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);

            for (int c =1; c <= 15; c++){
                pixel = telemetryTfod();
                if (pixel )  {
                    break;
                }
                sleep(20);
            }


        }

        if (!pixel){

            //Caso3
             BL.setPower(0.3);
            BR.setPower(-0.3);
            sleep(600);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(-0.3);
            BR.setPower(-0.3);
            sleep(250);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(0.3);
            BR.setPower(-0.3);
            sleep(500);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(0.2);
            BR.setPower(0.2);
            sleep(100);
            BL.setPower(0);
            BR.setPower(0);
            sleep(200);
            
            BL.setPower(0.3);
            BR.setPower(0.3);
            sleep(500);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            
            BL.setPower(-0.3);
            BR.setPower(-0.3);
            sleep(400);
            L.setPower(0);
            R.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            sleep(1000);
            

            for (int c =1; c <= 15; c++){
                pixel = telemetryTfod();
                if (pixel )  {
                    break;
                }
                sleep(20);
            }

            pixel = true;
        }






        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryTfod();



                // Push telemetry to the Driver Station.
                telemetry.update();


            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load, 
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                .setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to 
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */







    private boolean telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        boolean deteccion = false;

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop
        
        if (currentRecognitions.size() >= 1){

            
            /*ElevadorR.setPower(-0.2);
            ElevadorL.setPower(-0.2);
            sleep(3500);
        
            ElevadorR.setPower(0);
            ElevadorL.setPower(0);*/
            
            
            Garrita1.setPosition(1);
            sleep(1000);
            Garrita1.setPosition(0.5);
            sleep(1000);
            Garrita1.setPosition(1);
            sleep(1000);
            Garrita1.setPosition(0.5);
            sleep(1000);
            Garrita1.setPosition(1);
            sleep(1000);
            Garrita1.setPosition(0.5);
            sleep(1000);
            Garrita1.setPosition(1);
            sleep(1000);
            Garrita1.setPosition(0.5);
            sleep(1000);
            return true;
        }
        
        else{
            //Izquierda 
            return false;
        }
        
    }   // end method telemetryTfod()
    
}   // end class
