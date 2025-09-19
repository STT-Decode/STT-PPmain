package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem
{
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    private MotorEx intake = new MotorEx("intake");

    public Command turnOn()
    {
        return new ParallelGroup(
                new SetPower(intake, 1)
        ).requires(this);
    }

    public Command turnOff()
    {
        return new ParallelGroup(
                new SetPower(intake, 0)
        ).requires(this);
    }

    public Command setCustomPower(double power)
    {
        return new ParallelGroup(
                new SetPower(intake, power)
        ).requires(this);
    }
}
