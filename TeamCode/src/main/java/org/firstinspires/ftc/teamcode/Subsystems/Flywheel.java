package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.powerable.SetPower;

public class Flywheel implements Subsystem
{
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() { }

    private MotorEx flywheel1 = new MotorEx("flywheel1");
    private MotorEx flywheel2 = new MotorEx("flywheel2");

    public Command turnOn()
    {
        return new ParallelGroup(
                new SetPower(flywheel1, 0.8),
                new SetPower(flywheel2, 0.8)
        ).requires(this);
    }

    public Command turnOff()
    {
        return new ParallelGroup(
                new SetPower(flywheel1, 0),
                new SetPower(flywheel2, 0)
        ).requires(this);
    }

    public Command setCustomPower(double power)
    {
        return new ParallelGroup(
                new SetPower(flywheel1, power),
                new SetPower(flywheel2, power)
        ).requires(this);
    }
}
