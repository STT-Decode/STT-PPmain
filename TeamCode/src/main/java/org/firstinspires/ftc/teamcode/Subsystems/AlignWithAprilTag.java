package org.firstinspires.ftc.teamcode.Subsystems;

import android.app.ActionBar;
import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;


public class AlignWithAprilTag extends Command
{
    private static AprilTagProcessor aprilTag;
    private final MotorEx backLeft;
    private final MotorEx frontLeft;
    private final MotorEx backRight;
    private final MotorEx frontRight;
    private final VisionPortal visionPortal;
    private final int id;
    Size camSize;

    /**
     * Aims the robot to a April Tag, used for scoring for example
     *
     * @param id the ID of the April Tag to aim at. -1 works if there is only one April Tag visible, else it will return exit code 1
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

        camSize = new Size(640, 480);

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .setCameraResolution(camSize)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();

        this.id = id;
    }

    @Override
    public void update()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections)
        {
            double rotation = (detection.center.x - ((double) camSize.getWidth() / 2)) / camSize.getWidth() * 2;
            ActiveOpMode.telemetry().addData("Rotation", rotation);
            ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());

            //TODO: min rotation speed

            if (Math.abs(rotation) > 0.1)
            {
                backLeft.setPower(-rotation);
                backRight.setPower(rotation);
                frontRight.setPower(rotation);
                frontLeft.setPower(-rotation);
            } else
            {
                backLeft.setPower(0);
                backRight.setPower(0);
                frontRight.setPower(0);
                frontLeft.setPower(0);
            }
            break;
        }

        if (currentDetections.isEmpty())
        {
            backLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);
            frontLeft.setPower(0);
        }

    }

    @Override
    public boolean isDone()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections)
        {
            if ((Math.abs(detection.center.x - ((double) camSize.getWidth() / 2)) < 40) && ((detection.id == this.id) || (this.id == -1)))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void stop(boolean interrupted)
    {
        backLeft.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    @Override
    public void start()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        if (currentDetections.isEmpty())
        {
            stop(false);
        }
    }
}
