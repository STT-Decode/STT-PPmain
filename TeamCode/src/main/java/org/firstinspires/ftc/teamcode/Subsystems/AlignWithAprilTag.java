package org.firstinspires.ftc.teamcode.Subsystems;

import android.app.ActionBar;
import android.util.Size;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;


public class AlignWithAprilTag extends Command
{
    private AprilTagProcessor aprilTag;
    private MotorEx backLeft;
    private MotorEx frontLeft;
    private MotorEx backRight;
    private MotorEx frontRight;
    private VisionPortal visionPortal;
    private int id;

    private Size camSize;

    private final double OFFSET = 25;

    Follower follower;

    /**
     * Aims the robot to a April Tag, used for scoring for example
     *
     * @param id the ID of the April Tag to aim at. -1 works if there is only one April Tag visible
     */
    public AlignWithAprilTag(HardwareMap hardwareMap, int id, MotorEx bl, MotorEx fl, MotorEx br, MotorEx fr, AprilTagProcessor aprilVision, Size camSize)
    {
        this.backLeft = bl;
        this.frontLeft = fl;
        this.backRight = br;
        this.frontRight = fr;
        this.camSize = camSize;

        this.aprilTag = aprilVision;
        this.id = id;
    }

    @Override
    public void update()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections)
        {
            if ((detection.id == this.id) || (this.id == -1))
            {
                double offset = 0;

                if (detection.id == 24)
                {
                    offset = -OFFSET;
                }
                else if (detection.id == 20)
                {
                    offset = OFFSET;
                }

                double rotation = (detection.center.x - ((double) camSize.getWidth() / 2) - offset) / camSize.getWidth() * 2 * 1.7;
                ActiveOpMode.telemetry().addData("Rotation", rotation);

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
            double offset = 0;

            if (detection.id == 24)
            {
                offset = -OFFSET;
            }
            else if (detection.id == 20)
            {
                offset = OFFSET;
            }

            if ((Math.abs(detection.center.x - ((double) camSize.getWidth() / 2) - offset) < 20) && ((detection.id == this.id) || (this.id == -1)))
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

}
