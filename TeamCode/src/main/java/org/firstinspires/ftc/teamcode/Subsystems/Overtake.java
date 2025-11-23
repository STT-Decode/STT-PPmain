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

    private MotorEx overtakeMotor = new MotorEx("overtake_motor");
    private CRServoEx overtakeServo = new CRServoEx("overtake_servo");

    public Command turnOn()
    {
        return new ParallelGroup(
                new SetPower(overtakeMotor, -1),
                new SetPower(overtakeServo, -1));
    }

    public Command turnOff()
    {
        return new ParallelGroup(
                new SetPower(overtakeMotor, 0),
                new SetPower(overtakeServo, 0));
    }

}
