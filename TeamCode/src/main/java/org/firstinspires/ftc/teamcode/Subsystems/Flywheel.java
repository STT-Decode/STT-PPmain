package org.firstinspires.ftc.teamcode.Subsystems;

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
    private Flywheel() { }

    private MotorEx flywheel1 = new MotorEx("flywheel1");
    private MotorEx flywheel2 = new MotorEx("flywheel2").reversed();


    double flywheelVelocityGoal;
    double flywheelPower;


    @Override
    public void periodic()
    {
        flywheelPower += ((flywheelVelocityGoal + flywheel1.getVelocity()) / 1000);

        flywheel1.setPower(flywheelPower);
        flywheel2.setPower(flywheelPower);
        ActiveOpMode.telemetry().addData("debug", flywheel1.getVelocity());
        ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
    }

    public Command turnOn()
    {
        return new ParallelGroup(
                new SetPower(flywheel1, 0.9),
                new SetPower(flywheel2, 0.9)
        );
    }

    public Command turnOff()
    {
        return new ParallelGroup(
                new SetPower(flywheel1, 0),
                new SetPower(flywheel2, 0)
        );
    }

    public Command setCustomPower(double power)
    {
        return new ParallelGroup(
                new SetPower(flywheel1, power),
                new SetPower(flywheel2, power)
        ).requires(this);
    }
}
