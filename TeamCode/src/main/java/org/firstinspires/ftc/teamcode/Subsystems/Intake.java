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

    private MotorEx intake;

    @Override
    public void initialize()
    {
        intake = new MotorEx("intake").reversed().brakeMode();
        intake.setPower(0);
    }

    public Command turnOn()
    {
        return new SetPower(intake, 1).requires(this);
    }

    public Command turnOff()
    {
        return new SetPower(intake, 0).requires(this);
    }

    public Command setCustomPower(double power)
    {
        return new SetPower(intake, power).requires(this);
    }

    public Command reverse()
    {
        return new SetPower(intake, -1).requires(this);
    }
}
