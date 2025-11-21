package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
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
                Gamepads.gamepad1().rightStickX().negate()
        );

        driverControlled.schedule();
        BindingManager.update();

        /**Toggles the flywheels between on and off.*/
        Gamepads.gamepad1().b().toggleOnBecomesTrue()
                .whenBecomesTrue(Flywheel.INSTANCE.turnOn())
                .whenBecomesFalse(Flywheel.INSTANCE.turnOff());

        /**Toggles the intake*/
        Gamepads.gamepad1().a().toggleOnBecomesTrue()
                .whenBecomesTrue(Intake.INSTANCE.turnOn())
                .whenBecomesFalse(Intake.INSTANCE.turnOff());

        /**Toggles the overtake*/
        Gamepads.gamepad1().x().toggleOnBecomesTrue()
                .whenBecomesTrue(Overtake.INSTANCE.turnOn())
                .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, -1, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
        Gamepads.gamepad1().a().whenBecomesTrue(alignWithAprilTag);
    }

}