package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Overtake implements Subsystem
{
    public static final Overtake INSTANCE = new Overtake();
    private Overtake() { }

    private CRServoEx overtake_1 = new CRServoEx("overtake_1");

    public Command turnOn()
    {
        return new SetPower(overtake_1, -1);
    }

    public Command turnOff()
    {
        return new SetPower(overtake_1, 0);
    }

}
