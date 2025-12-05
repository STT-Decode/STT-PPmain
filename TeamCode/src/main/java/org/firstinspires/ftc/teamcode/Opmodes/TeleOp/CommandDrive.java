package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.bindings.BindingManager;

@TeleOp(name = "Drive")
public class CommandDrive extends rootOpMode
{
    @Override
    public void onStartButtonPressed()
    {
        BindingManager.update();
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX().negate(),
                Gamepads.gamepad1().rightStickX().negate().mapToRange(value -> value * 0.8)
        );


        BindingManager.update();
        driverControlled.schedule();
        BindingManager.update();

        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, -1, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(alignWithAprilTag);

        //Flywheels
        Gamepads.gamepad2().rightTrigger().greaterThan(0.3)
                .whenBecomesTrue(Flywheel.INSTANCE::turnOn)
                .whenBecomesFalse(Flywheel.INSTANCE::turnOff);

        //Intake
        Gamepads.gamepad2().leftTrigger().greaterThan(-1).whenTrue(() -> Intake.INSTANCE.setCustomPower(Gamepads.gamepad2().leftTrigger().get()).schedule());

        //Intake
        Gamepads.gamepad1().leftTrigger().greaterThan(-1).whenTrue(() -> Intake.INSTANCE.setCustomPower(Gamepads.gamepad1().leftTrigger().get()).schedule());

        //Overtake
        Gamepads.gamepad2().leftTrigger().greaterThan(-1).whenTrue(() -> Overtake.INSTANCE.setCustomPower(Gamepads.gamepad2().leftTrigger().get()).schedule());

        Gamepads.gamepad2().x().toggleOnBecomesTrue()
                .whenBecomesTrue(Feeder.INSTANCE.fire())
                .whenBecomesFalse(Feeder.INSTANCE.open());

        Gamepads.gamepad2().rightBumper()
                .whenBecomesTrue(Flywheel.INSTANCE.increaseFlywheelSpeed(20));

        Gamepads.gamepad2().leftBumper()
                .whenBecomesTrue(Flywheel.INSTANCE.increaseFlywheelSpeed(-20));

    }

}