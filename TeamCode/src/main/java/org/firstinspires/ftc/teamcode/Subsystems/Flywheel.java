package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.aprilTag;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.powerable.SetPower;

public class Flywheel implements Subsystem
{
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() {}

    private MotorEx flywheel1 = new MotorEx("flywheel1").reversed();
    private MotorEx flywheel2 = new MotorEx("flywheel2");

    double flywheelVelocityGoal = 2300;
    double flywheelPower;
    boolean state;

    @Override
    public void initialize()
    {
        flywheel1 = new MotorEx("flywheel1").reversed();
        flywheel2 = new MotorEx("flywheel2");

        flywheelVelocityGoal = 2300;
        flywheelPower = 0;
        state = false;
    }

    @Override
    public void periodic()
    {
        if (state)
        {
            flywheelPower += ((flywheelVelocityGoal - Math.abs(flywheel1.getVelocity())) / 5000);
            flywheel1.setPower(-flywheelPower);
            flywheel2.setPower(-flywheelPower);
        } else
        {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }

        flywheelPower = Math.min(flywheelPower, 0.93);
        ActiveOpMode.telemetry().addData("flywheelPower", flywheelPower);
        ActiveOpMode.telemetry().addData("goalVelocity", flywheelVelocityGoal);
        ActiveOpMode.telemetry().addData("current Velocity", flywheel1.getVelocity());
        ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
    }

    public void turnOn()
    {
        state = true;
        flywheelPower = 0.9;
        flywheel1.setPower(0.85);
        flywheel2.setPower(0.85);
    }

    public void turnOff()
    {
        state = false;
        flywheel1.setPower(0);
        flywheel2.setPower(0);
    }

    public Command setCustomPower(double power)
    {
        return new ParallelGroup(
                new SetPower(flywheel1, power),
                new SetPower(flywheel2, power)
        ).requires(this);
    }

    public Command increaseFlywheelSpeed(double increase)
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                flywheelVelocityGoal += increase;
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
