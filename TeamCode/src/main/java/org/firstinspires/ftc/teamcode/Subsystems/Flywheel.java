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
    private final ControlSystem controlSystem = ControlSystem.builder().build();
    double FlyWheelVelocity = 2400;

    @Override
    public void periodic()
    {
        flywheel1.setPower(controlSystem.calculate(flywheel1.getState()));
        flywheel2.setPower(controlSystem.calculate(flywheel2.getState()));
        ActiveOpMode.telemetry().addData("FlyWheelVelocity", FlyWheelVelocity);
    }

    public Command turnOn()
    {
        return new RunToVelocity(controlSystem, FlyWheelVelocity)
                .requires(this);
    }

    public Command ChangeFlyWheelVelocity(double Change)
    {
        return new LambdaCommand()
                .setStart (() -> FlyWheelVelocity += Change
        );
    }

    public Command turnOff()
    {
        return new RunToVelocity(controlSystem, 0)
                .requires(this);
    }

    public Command setCustomPower(double power)
    {
        return new ParallelGroup(
                new SetPower(flywheel1, power),
                new SetPower(flywheel2, power)
        ).requires(this);
    }
}
