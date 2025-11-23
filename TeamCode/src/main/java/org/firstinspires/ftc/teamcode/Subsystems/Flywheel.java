package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.control.ControlSystem;
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

    private Flywheel()
    {
    }

    private final MotorEx flywheel1 = new MotorEx("flywheel1");
    private final MotorEx flywheel2 = new MotorEx("flywheel2").reversed();

    public Command turnOn()
    {
        return new ParallelGroup(
                new SetPower(flywheel1, -0.965).requires(this),
                new SetPower(flywheel2, -0.965).requires(this));
    }


    public Command turnOff()
    {
        return new ParallelGroup(
                new SetPower(flywheel1, 0).requires(this),
                new SetPower(flywheel2, 0).requires(this));
    }

    public Command setCustomPower(double power)
    {
        return new ParallelGroup(
                new SetPower(flywheel1, power),
                new SetPower(flywheel2, power)
        ).requires(this);
    }
}
