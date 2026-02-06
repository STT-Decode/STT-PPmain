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


public class AlignWithAprilTag extends Command
{
    private AprilTagProcessor aprilTag;
    private MotorEx backLeft;
    private MotorEx frontLeft;
    private MotorEx backRight;
    private MotorEx frontRight;
    private VisionPortal visionPortal;
    private int id;
    private double offset;
    private boolean alignedToApriltag = false;
    private boolean doneWithCalculations = false;
    private double targetAngle = 0;

    private Size camSize;

    private static double OFFSET;

    /**
     * Aims the robot to a April Tag, used for scoring for example
     *
     * @param id the ID of the April Tag to aim at. -1 works if there is only one April Tag visible
     */
    public AlignWithAprilTag(HardwareMap hardwareMap, int id, MotorEx bl, MotorEx fl, MotorEx br, MotorEx fr, AprilTagProcessor aprilVision, Size camSize, boolean useMatchOffset)
    {
        this.backLeft = bl;
        this.frontLeft = fl;
        this.backRight = br;
        this.frontRight = fr;
        this.camSize = camSize;

        this.aprilTag = aprilVision;
        this.id = id;
        alignedToApriltag = false;
        doneWithCalculations = false;
        imu = new IMUEx("imu", Direction.BACKWARD, Direction.UP).zeroed();

        OFFSET = useMatchOffset ? 50 : 87;
    }

    @Override
    public void update()
    {
        //if (!alignedToApriltag)
        //{
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
                    double rotation = (detection.center.x - ((double) camSize.getWidth() / 2) - offset) / camSize.getWidth() * 2 * 1;
                    ActiveOpMode.telemetry().addData("Rotation", rotation);
                    ActiveOpMode.telemetry().update();

                    if (Math.abs(rotation) > 0.06)
                    {
                        backLeft.setPower(-rotation);
                        backRight.setPower(rotation);
                        frontRight.setPower(rotation);
                        frontLeft.setPower(-rotation);
                    } else {
                        backLeft.setPower(-Math.copySign(0.06, rotation));
                        backRight.setPower(Math.copySign(0.06, rotation));
                        frontRight.setPower(Math.copySign(0.06, rotation));
                        frontLeft.setPower(-Math.copySign(0.06, rotation));
                    }

                    /*if ((Math.abs(detection.center.x - ((double) camSize.getWidth() / 2)) <= 7) && ((detection.id == this.id) || (this.id == -1)))
                    {
                        alignedToApriltag = true;
                    }*/
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
        /*} else
        {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();

            for (AprilTagDetection detection : currentDetections)
            {
                if (!doneWithCalculations)
                {
                    targetAngle = Math.atan((Math.sin(imu.get().inDeg) * detection.center.y * 0.0254 + 0.36) / (Math.cos(imu.get().inDeg) * detection.center.y * 0.0254 + 0.3));
                    doneWithCalculations = true;
                }
            }
            double rotation = (targetAngle - imu.get().inDeg) / 20;
            ActiveOpMode.telemetry().addData("Rotation", rotation);
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
        }*/
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

            ActiveOpMode.telemetry().addData("DetectionID", detection.id);

            if ((Math.abs(detection.center.x - ((double) camSize.getWidth() / 2) - offset) <= 4) && ((detection.id == this.id) || (this.id == -1)))
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
}
