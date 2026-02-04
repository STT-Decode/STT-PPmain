package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.Tests;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotParts.All_Parts;
import org.firstinspires.ftc.teamcode.RobotParts.Drivetrain;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "AprilTag Easy", group = "Linear Opmode")
public class AprilTagTest extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    All_Parts allParts = new All_Parts();
    Drivetrain drivetrain  = new Drivetrain();

    @Override
    public void runOpMode() {

        initAprilTag();
        allParts.init(hardwareMap);
        drivetrain.init(hardwareMap);
        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryAprilTag();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end method runOpMode()

    /**
     * Initialize the AprilTag processor.
     */
    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();

    }   // end method initAprilTag()

    /**
     * Add telemetry about AprilTag detections.
     */
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            switch (detection.id) {
                case 20:
                    telemetry.addLine("BLUE");
                    break;

                case 21:
                    telemetry.addLine("GPP");
                    break;

                case 22:
                    telemetry.addLine("PGP");
                    break;

                case 23:
                    telemetry.addLine("PPG");
                    break;

                case 24:
                    telemetry.addLine("RED");
                    break;
            }

            telemetry.addLine("X center offset: " + (detection.center.x - 160));

            if ((Math.abs(detection.center.x - 320) > 60)) {
                allParts.drive0(0, 0, -(detection.center.x - 320) / 320, 1);
            }else{
                allParts.drive0(0, 0, 0, 0);
                drivetrain.flywheels(1);
            }
        }
        if (currentDetections.isEmpty()){
            allParts.drive0(0, 0, 0, 0);
        }
    }   // end method telemetryAprilTag()

}   // end class