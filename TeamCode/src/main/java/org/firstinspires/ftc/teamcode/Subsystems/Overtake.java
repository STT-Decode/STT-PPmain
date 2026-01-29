package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Overtake implements Subsystem
{
    public static final Overtake INSTANCE = new Overtake();
    private Overtake() { }

    private MotorEx overtakeMotor = new MotorEx("overtake").reversed().brakeMode();

    public Command turnOn()
    {
        return new SetPower(overtakeMotor, 1).requires(this);
    }
    public Command reverse()
    {
        return new SetPower(overtakeMotor, -1).requires(this);
    }

    public Command turnOff()
    {
        return new SetPower(overtakeMotor, 0).requires(this);
    }

    public Command setCustomPower(double Power)
    {
        return new SetPower(overtakeMotor, -Power).requires(this);
    }

}
