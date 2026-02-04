package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.imu;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;

public class imuTest implements Subsystem
{
    public static final imuTest INSTANCE = new imuTest();
    private imuTest() {}

    @Override
    public void initialize()
    {
        imu = new IMUEx("imu", Direction.BACKWARD, Direction.UP).zeroed();
    }

    public Command getimu()
    {
        return new Command()
        {
            @Override
            public boolean isDone()
            {
                ActiveOpMode.telemetry().addData("imu", imu.get().inDeg);
                ActiveOpMode.telemetry().update();
                return true;
            }
        };
    }
}
