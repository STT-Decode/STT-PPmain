package org.firstinspires.ftc.teamcode.Opmodes;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.bindings.BindingManager;
import dev.nextftc.bindings.Range;


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
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );

        AlignWithAprilTag alignWithAprilTag = new AlignWithAprilTag(hardwareMap, -1, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
        driverControlled.schedule();
        BindingManager.setLayer("Close scoring zone");


        /**Toggles the state, used for choosing the flywheel speed*/
        Gamepads.gamepad1().a().toggleOnBecomesTrue()
                .whenBecomesTrue(() -> BindingManager.setLayer("Close scoring zone"))
                .whenBecomesFalse(() -> BindingManager.setLayer("Far scoring zone"));

        Gamepads.gamepad1().b().whenBecomesTrue(alignWithAprilTag.requires(driverControlled));



        /**Toggles the flywheels between on and whatever value the right trigger gives.
         * inLayer() bases the flywheel speed based on our distance to the goal.*/
        Gamepads.gamepad1().rightBumper().toggleOnBecomesTrue()
                .inLayer("Close scoring zone")
                .whenBecomesTrue((Flywheel.INSTANCE.setCustomPower(0.5)))
                .inLayer("Far scoring zone")
                .whenBecomesTrue(Flywheel.INSTANCE.setCustomPower(1))
                .global()
                .whenFalse(() -> Flywheel.INSTANCE.setCustomPower(Gamepads.gamepad1().rightTrigger().get()).schedule());

        Gamepads.gamepad1().x().toggleOnBecomesTrue()
                .whenBecomesTrue((Flywheel.INSTANCE.turnOn()))
                .whenBecomesFalse(Intake.INSTANCE.turnOff());

        /**Toggles the servo*/
        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(Feeder.INSTANCE.fire())
                .whenBecomesFalse(Feeder.INSTANCE.open());


    }


}