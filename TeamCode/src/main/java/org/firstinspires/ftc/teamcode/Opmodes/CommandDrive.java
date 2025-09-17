package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
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
import dev.nextftc.bindings.Range;


@TeleOp(name = "Drive")
public class CommandDrive extends rootOpMode
{


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
        BindingManager.setLayer("Close scoring zone");

        /**Toggles the state, used for choosing the flywheel speed*/
        Gamepads.gamepad1().a().toggleOnBecomesTrue()
                .whenBecomesTrue(() -> BindingManager.setLayer("Close scoring zone"))
                .whenBecomesFalse(() -> BindingManager.setLayer("Far scoring zone"));

        /**Toggles the flywheels between on and whatever value the right trigger gives.
         * inLayer() bases the flywheel speed based on our distance to the goal.*/
        Gamepads.gamepad1().rightBumper().toggleOnBecomesTrue()
                .inLayer("Close scoring zone")
                .whenBecomesTrue((Flywheel.INSTANCE.setCustomPower(0.7)))
                .inLayer("Far scoring zone")
                .whenBecomesTrue(Flywheel.INSTANCE.setCustomPower(1))
                .global()
                .whenFalse(() -> Flywheel.INSTANCE.setCustomPower(Gamepads.gamepad1().rightTrigger().get()).schedule());


        /**Toggles the servo*/
        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(Feeder.INSTANCE.fire())
                .whenBecomesFalse(Feeder.INSTANCE.open());

    }

}