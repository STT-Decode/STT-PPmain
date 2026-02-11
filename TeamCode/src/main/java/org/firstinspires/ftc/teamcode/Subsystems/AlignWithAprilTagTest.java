package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.imu;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

public class AlignWithAprilTagTest extends Command
{
    private AprilTagProcessor aprilTag;
    private MotorEx backLeft;
    private MotorEx frontLeft;
    private MotorEx backRight;
    private MotorEx frontRight;
    private VisionPortal visionPortal;
    private int id;
    private double offset;
    private boolean doneWithCalculations = false;
    private double targetAngle = 0;
    private double angleToAprilTag = 0;
    private static double stopaligning = 0;

    private Size camSize;

    private static double OFFSET;

    /**
     * Aims the robot to a April Tag, used for scoring for example
     *
     * @param id the ID of the April Tag to aim at. -1 works if there is only one April Tag visible
     */
    public AlignWithAprilTagTest(HardwareMap hardwareMap, int id, MotorEx bl, MotorEx fl, MotorEx br, MotorEx fr, AprilTagProcessor aprilVision, Size camSize, boolean useMatchOffset)
    {
        this.backLeft = bl;
        this.frontLeft = fl;
        this.backRight = br;
        this.frontRight = fr;
        this.camSize = camSize;

        this.aprilTag = aprilVision;
        this.id = id;
        doneWithCalculations = false;
        imu = new IMUEx("imu", Direction.BACKWARD, Direction.UP).zeroed();
        stopaligning = 0;
    }

    @Override
    public void update()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections)
        {
            if (!doneWithCalculations)
            {
                angleToAprilTag = imu.get().inDeg + detection.ftcPose.bearing;
                targetAngle = Math.toDegrees(Math.atan((Math.sin(Math.toRadians(angleToAprilTag)) * detection.ftcPose.range * 0.0254 - 0.36) / (Math.cos(Math.toRadians(angleToAprilTag)) * detection.ftcPose.range * 0.0254 + 0.3)));
                doneWithCalculations = true;
            }
            ActiveOpMode.telemetry().addData("bearing", detection.ftcPose.bearing);
            ActiveOpMode.telemetry().addData("distance to april tag in meter", detection.ftcPose.range * 0.0254);
            ActiveOpMode.telemetry().addData("current angle", imu.get().inDeg);
        }
        double rotation  = 0;
        if ((targetAngle - imu.get().inDeg) < 0){
            rotation = 0.4;
        } else {
            rotation = -0.4;
        }
        ActiveOpMode.telemetry().addData("Rotation", rotation);
        ActiveOpMode.telemetry().addData("angleToAprilTag", angleToAprilTag);
        ActiveOpMode.telemetry().addData("targetAngle", targetAngle);
        ActiveOpMode.telemetry().update();
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
    }

    @Override
    public boolean isDone()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections)
        {
            ActiveOpMode.telemetry().addData("DetectionID", detection.id);

            if (((Math.abs(targetAngle - imu.get().inDeg) <= 2) && ((detection.id == this.id) || (this.id == -1))) || stopaligning == 1)
            {
                return true;
            }
        }
        new Delay(0.5).schedule();
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

    public static Command setOffset(double offset)
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                OFFSET = offset;
                return true;
            }
        };
    }

    public static Command stopAligning()
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                stopaligning = 1;
                return true;
            }
        };
    }
}
