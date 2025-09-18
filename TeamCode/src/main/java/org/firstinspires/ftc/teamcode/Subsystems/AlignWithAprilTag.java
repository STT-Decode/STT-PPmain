package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.impl.MotorEx;


public class AlignWithAprilTag extends Command
{
    private static AprilTagProcessor aprilTag;
    private final MotorEx backLeft;
    private final MotorEx frontLeft;
    private final MotorEx backRight;
    private final MotorEx frontRight;
    private VisionPortal visionPortal;
    private int id;

    /**
     * Aims the robot to a April Tag, used for scoring for example
     *
     * @param id               the ID of the April Tag to aim at. -1 works if there is only one April Tag visible, else it will return exit code 1
     */
    public AlignWithAprilTag(HardwareMap hardwareMap, int id, MotorEx bl, MotorEx fl, MotorEx br, MotorEx fr)
    {
        this.backLeft = bl;
        this.frontLeft = fl;
        this.backRight = br;
        this.frontRight = fr;

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .setCameraResolution(new Size(320, 240))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();

        this.id = id;
    }

    @Override
    public void update()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if ((Math.abs(detection.center.x - 160) > 100) && ((detection.id == this.id) || (this.id == -1)))
            {
                backLeft.setPower((detection.center.x - 160) / 160);
                frontLeft.setPower((detection.center.x - 160) / 160);
                frontRight.setPower(-(detection.center.x - 160) / 160);
                backRight.setPower(-(detection.center.x - 160) / 160);
                break;

            }
        }

    }

    @Override
    public boolean isDone()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if ((Math.abs(detection.center.x - 160) < 100) && ((detection.id == this.id) || (this.id == -1)))
            {
                return true;
            }
        }

        return currentDetections.isEmpty();
    }

    @Override
    public void stop(boolean interrupted)
    {
        backLeft.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }
}
