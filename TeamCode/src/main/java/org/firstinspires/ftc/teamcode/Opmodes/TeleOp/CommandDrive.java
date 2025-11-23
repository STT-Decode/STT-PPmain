package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;

import java.util.Map;

import dev.nextftc.core.commands.Command;
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
                Gamepads.gamepad1().rightStickX().negate().mapToRange(value -> value * 0.7)
        );

        driverControlled.schedule();
        BindingManager.update();

        //Toggles the flywheels
        Gamepads.gamepad1().b().toggleOnBecomesTrue()
                .whenBecomesTrue(Flywheel.INSTANCE.turnOn())
                .whenBecomesFalse(Flywheel.INSTANCE.turnOff());

        //Toggles the intake
        Gamepads.gamepad1().a().toggleOnBecomesTrue()
                .whenBecomesTrue(Intake.INSTANCE.turnOn())
                .whenBecomesFalse(Intake.INSTANCE.turnOff());

        //Toggles the Overtake
        Gamepads.gamepad1().x().toggleOnBecomesTrue()
                .whenBecomesTrue(Overtake.INSTANCE.turnOn())
                .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        //Used for tuning the flywheelVelocity
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(Flywheel.INSTANCE.ChangeFlyWheelVelocity(50));
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(Flywheel.INSTANCE.ChangeFlyWheelVelocity(-50));

        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, -1, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
        Gamepads.gamepad1().y().whenBecomesTrue(alignWithAprilTag);
    }

}