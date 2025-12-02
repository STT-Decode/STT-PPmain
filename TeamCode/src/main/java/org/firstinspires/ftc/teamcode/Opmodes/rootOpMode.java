package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.MotorEx;

public class rootOpMode extends NextFTCOpMode
{
    public rootOpMode()
    {
        addComponents(
            new SubsystemComponent(Flywheel.INSTANCE, Feeder.INSTANCE, Intake.INSTANCE, Overtake.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    protected final MotorEx frontLeftMotor = new MotorEx("left_front").reversed().brakeMode();
    protected final MotorEx frontRightMotor = new MotorEx("right_front").reversed().brakeMode();
    protected final MotorEx backLeftMotor = new MotorEx("left_back").reversed().brakeMode();
    protected final MotorEx backRightMotor = new MotorEx("right_back").reversed().brakeMode();

    protected ElapsedTime runtime = new ElapsedTime();

}