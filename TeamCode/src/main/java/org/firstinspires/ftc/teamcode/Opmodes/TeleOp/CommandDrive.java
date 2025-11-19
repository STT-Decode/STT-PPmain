package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake_1;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake_2;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake_3;

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
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );

        driverControlled.schedule();;
        BindingManager.update();

        /**Toggles the flywheels between on and whatever value the right trigger gives.*/

        Gamepads.gamepad1().dpadDown().toggleOnBecomesTrue()
                .whenBecomesTrue((Flywheel.INSTANCE.setCustomPower(1)))
                .whenBecomesFalse(() -> Flywheel.INSTANCE.setCustomPower(Gamepads.gamepad1().rightTrigger().get()));

        /**Toggles the intake*/
        Gamepads.gamepad1().dpadRight().toggleOnBecomesTrue()
                .whenBecomesTrue(Intake.INSTANCE.turnOn())
                .whenBecomesFalse(Intake.INSTANCE.turnOff());

        //TODO: Rename overtake instances (or combine into one big instance)

        /**Toggles the flywheels overtake*/
        Gamepads.gamepad1().x().toggleOnBecomesTrue()
                .whenBecomesTrue(Overtake_1.INSTANCE.turnOn())
                .whenBecomesFalse(Overtake_1.INSTANCE.turnOff());

        /**Toggles the overtake_2*/
        Gamepads.gamepad1().y().toggleOnBecomesTrue()
                .whenBecomesTrue(Overtake_2.INSTANCE.feed())
                .whenBecomesFalse(Overtake_2.INSTANCE.open());

        /**Toggles the overtake_3*/
        Gamepads.gamepad1().b().toggleOnBecomesTrue()
                .whenBecomesTrue(Overtake_3.INSTANCE.open())
                .whenBecomesFalse(Overtake_3.INSTANCE.feed());


        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, -1, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
        Gamepads.gamepad1().a().whenBecomesTrue(alignWithAprilTag);
    }

}