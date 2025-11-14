package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Overtake implements Subsystem
{
    public static final Overtake INSTANCE = new Overtake();
    private Overtake() { }

    private CRServoEx overtake = new CRServoEx("overtake");

    public Command turnOn()
    {
        return new SetPower(overtake, -1);
    }

    public Command turnOff()
    {
        return new SetPower(overtake, 0);
    }

}
