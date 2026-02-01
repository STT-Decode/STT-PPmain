package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.Tests.rootOpmodetestytestyy.flywheel1;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.Tests.rootOpmodetestytestyy.flywheel2;
import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.aprilTag;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class FlywheelDcMotorEx implements Subsystem
{
    public static final FlywheelDcMotorEx INSTANCE = new FlywheelDcMotorEx();
    private FlywheelDcMotorEx() {}

    double flywheelVelocityGoal;
    double flywheelPower;
    boolean state;

    double CLOSEVELOCITY = 1700;
    double FARVELOCITY = 2000;

    @Override
    public void initialize()
    {
        flywheelVelocityGoal = FARVELOCITY;
        flywheelPower = 0;
        state = false;
    }

    @Override
    public void periodic()
    {
        flywheel2.setPower(flywheel1.getPower());
    }

    public void turnOn()
    {
        state = true;
        flywheelPower = 0.80;
        flywheel1.setVelocity(-flywheelVelocityGoal);
    }

    public void turnOff()
    {
        state = false;
        flywheel1.setPower(0);
        flywheel2.setPower(0);
    }


    public Command setFarZone(boolean far)
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                flywheelVelocityGoal = far ? FARVELOCITY : CLOSEVELOCITY;
                return true;
            }
        };
    }


    public Command autoSetFlywheelSpeed(int id)
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                List<AprilTagDetection> currentDetections = aprilTag.getDetections();

                for (AprilTagDetection detection : currentDetections)
                {
                    if ((detection.id == id) || (id == -1))
                    {
                        double distance = detection.center.y;
                        ActiveOpMode.telemetry().addData("Distance", distance);
                        flywheelVelocityGoal = distance * 0.01;
                    }
                }
                return true;
            }
        };

    }
}
