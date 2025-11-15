package org.firstinspires.ftc.teamcode.Opmodes;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.Subsystems.Overtake_1;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake_2;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake_3;
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
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class rootOpMode extends NextFTCOpMode
{
    public rootOpMode()
    {
        addComponents(
            new SubsystemComponent(Flywheel.INSTANCE, Feeder.INSTANCE, Intake.INSTANCE, Overtake_1.INSTANCE, Overtake_2.INSTANCE, Overtake_3.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    protected final MotorEx frontLeftMotor = new MotorEx("left_front");
    protected final MotorEx frontRightMotor = new MotorEx("right_front").reversed();
    protected final MotorEx backLeftMotor = new MotorEx("left_back");
    protected final MotorEx backRightMotor = new MotorEx("right_back").reversed();



}