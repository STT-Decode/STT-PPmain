package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.aprilTag;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
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

    public MotorEx flywheel1;
    public MotorEx flywheel2;

    double flywheelVelocityGoal;
    double flywheelPower;
    boolean state;

    @Override
    public void initialize()
    {
        flywheel1 = new MotorEx("flywheel1").reversed().brakeMode();
        flywheel2 = new MotorEx("flywheel2").reversed().brakeMode();

        flywheelVelocityGoal = 1950;
        flywheelPower = 0;
        state = false;
    }

    @Override
    public void periodic()
    {
        if (state)
        {
            if (flywheelVelocityGoal != flywheel2.getVelocity())
            {
                flywheelPower += ((flywheelVelocityGoal - Math.abs(flywheel2.getVelocity())) / 30000);
                new SetPower(flywheel1, flywheelPower).schedule();
                new SetPower(flywheel2, flywheelPower).schedule();
            }
        } else
        {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }

        flywheelPower = Math.min(flywheelPower, 0.93);
        ActiveOpMode.telemetry().addData("flywheelPower", flywheelPower);
        ActiveOpMode.telemetry().addData("goalVelocity", flywheelVelocityGoal);
        ActiveOpMode.telemetry().addData("current Velocity", flywheel2.getVelocity());
    }

    public void turnOn()
    {
        state = true;
        flywheelPower = 0.9;
        new SetPower(flywheel1, 0.85).schedule();
        new SetPower(flywheel2, 0.85).schedule();
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
