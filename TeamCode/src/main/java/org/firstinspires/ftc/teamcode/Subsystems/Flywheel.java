package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.aprilTag;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
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
    double flywheelPower2 = 0.80;
    boolean state;

    double CLOSEVELOCITY = 1900;
    double FARVELOCITY = 2000;

    @Override
    public void initialize()
    {
        flywheel1 = new MotorEx("flywheel1").brakeMode();
        flywheel2 = new MotorEx("flywheel2").reversed().brakeMode();

        flywheelVelocityGoal = FARVELOCITY;
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
                flywheelPower += ((flywheelVelocityGoal - Math.abs(flywheel2.getVelocity())) / 50000);
                new SetPower(flywheel1, flywheelPower).schedule();
                new SetPower(flywheel2, flywheelPower).schedule();
            }
        } else
        {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }

        flywheelPower = Math.min(flywheelPower, 1);
        ActiveOpMode.telemetry().addData("flywheelPower", flywheelPower);
        ActiveOpMode.telemetry().addData("goalVelocity", flywheelVelocityGoal);
        ActiveOpMode.telemetry().addData("current Velocity", flywheel2.getVelocity());
        ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
    }

    public void turnOn()
    {
        state = true;
        flywheelPower = 0.80;
        new SetPower(flywheel1, flywheelPower).schedule();
        new SetPower(flywheel2, flywheelPower).schedule();
        ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
    }

    public void turnOn2()
    {
        state = true;
        new SetPower(flywheel1, flywheelPower2).schedule();
        new SetPower(flywheel2, flywheelPower2).schedule();
        ActiveOpMode.telemetry().addData("flywheelPower", flywheelPower2);
        ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
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

    public Command bumpFlywheelSpeed()
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                flywheelPower2 += 0.03;
                return true;
            }
        };
    }

    public Command unbumpFlywheelSpeed()
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                flywheelPower2 -= 0.03;
                return true;
            }
        };
    }

    public Command changeFlywheelPower(double change)
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                flywheelPower2 += change;
                return true;
            }
        };
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
