package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.bindings.BindingManager;


@TeleOp(name = "Drive")
public class CommandDrive extends NextFTCOpMode
{
    public CommandDrive()
    {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final MotorEx frontLeftMotor = new MotorEx("left_front");
    private final MotorEx frontRightMotor = new MotorEx("right_front").reversed();
    private final MotorEx backLeftMotor = new MotorEx("left_back");
    private final MotorEx backRightMotor = new MotorEx("right_back").reversed();

    @Override
    public void onStartButtonPressed()
    {
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );
        driverControlled.schedule();
        BindingManager.update();

        //Toggles the flywheels between on and whatever value the right trigger gives
        Gamepads.gamepad1().a().toggleOnBecomesTrue()
                .whenBecomesTrue(Flywheel.INSTANCE.turnOn())
                .whenFalse(Flywheel.INSTANCE.setCustomPower(Gamepads.gamepad1().rightTrigger().get()));
    }

}