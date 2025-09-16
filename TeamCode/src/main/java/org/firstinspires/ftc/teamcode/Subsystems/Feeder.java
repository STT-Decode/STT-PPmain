package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

import dev.nextftc.hardware.impl.ServoEx;

public class Feeder implements Subsystem
{
    public static final Feeder INSTANCE = new Feeder();
    private Feeder() { }

    private ServoEx feeder = new ServoEx("feeder");

    /**Opens the feeder to a position where artifacts can get loaded*/
    public Command open()
    {
        return new SetPosition(feeder, 0);
    }

    /**Turns the feeder give artifacts to the flywheels to fire them*/
    public Command fire()
    {
        return new SetPosition(feeder, 0.5);
    }

}
