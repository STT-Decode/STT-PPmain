package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Overtake_3 implements Subsystem
{
    public static final Overtake_3 INSTANCE = new Overtake_3();
    private Overtake_3() { }

    private ServoEx overtake_3 = new ServoEx("overtake_3");

    /**Opens the feeder to a position where artifacts can get loaded*/
    public Command open()
    {
        return new SetPosition(overtake_3, 0.6);
    }

    /**Turns the feeder give artifacts to the flywheels to fire them*/
    public Command feed()
    {
        return new SetPosition(overtake_3, 0);
    }

}
