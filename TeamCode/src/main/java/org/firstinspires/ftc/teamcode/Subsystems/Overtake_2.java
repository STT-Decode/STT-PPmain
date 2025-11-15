package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Overtake_2 implements Subsystem
{
    public static final Overtake_2 INSTANCE = new Overtake_2();
    private Overtake_2() { }

    private ServoEx overtake_2 = new ServoEx("overtake_2");

    /**Opens the feeder to a position where artifacts can get loaded*/
    public Command open()
    {
        return new SetPosition(overtake_2, 0);
    }

    /**Turns the feeder give artifacts to the flywheels to fire them*/
    public Command feed()
    {
        return new SetPosition(overtake_2, 1);
    }

}
