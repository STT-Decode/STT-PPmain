package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

@TeleOp(name = "DriveSingle")
public class CommandDriveSinglePlayer extends rootOpMode
{
    boolean isRed = true;

    @Override
    public void onStartButtonPressed()
    {
        BindingManager.update();
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().deadZone(0.05),
                Gamepads.gamepad1().leftStickX().negate().deadZone(0.05),
                Gamepads.gamepad1().rightStickX().negate().deadZone(0.05).mapToRange(value -> value * 0.8)
        );


        BindingManager.update();
        driverControlled.schedule();
        BindingManager.update();

        int id = isRed ? 24 : 20;
        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor, aprilTag, camSize, true);
        Gamepads.gamepad1().dpadUp().whenBecomesTrue(alignWithAprilTag);

        //Flywheels
        Gamepads.gamepad1().rightTrigger().greaterThan(0.3)
                .whenBecomesTrue(Flywheel.INSTANCE::turnOn)
                .whenBecomesFalse(Flywheel.INSTANCE::turnOff);

        Gamepads.gamepad2().leftBumper().whenBecomesTrue(Flywheel.INSTANCE.changeFlywheelPower(-0.02));
        Gamepads.gamepad2().rightBumper().whenBecomesTrue(Flywheel.INSTANCE.changeFlywheelPower(0.02));

        //Intake
        Gamepads.gamepad1().leftTrigger().greaterThan(0.1).whenTrue(() -> Intake.INSTANCE.setCustomPower(Gamepads.gamepad1().leftTrigger().get()).schedule())
                .whenFalse(Intake.INSTANCE.setCustomPower(Gamepads.gamepad1().dpadLeft().get() ? -1 : 0));

        //Overtake
        Gamepads.gamepad1().a().whenBecomesTrue(Overtake.INSTANCE.turnOn())
                .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Gamepads.gamepad1().dpadLeft().whenBecomesTrue(Overtake.INSTANCE.reverse())
                                        .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Gamepads.gamepad1().dpadLeft().whenBecomesTrue(Intake.INSTANCE.reverse())
                                        .whenBecomesFalse(Intake.INSTANCE.turnOff());


        Gamepads.gamepad1().x()
                .whenBecomesTrue(Feeder.INSTANCE.fire());


        Gamepads.gamepad1().dpadRight()
                .whenBecomesTrue(Flywheel.INSTANCE.autoSetFlywheelSpeed(-1));

    }

    @Override
    public void onInit()
    {
        updateTelemetry().schedule();
        ActiveOpMode.telemetry().addData("Aliance", isRed ? "Red" : "Blue");
        Gamepads.gamepad1().b().whenBecomesTrue(new Command()
        {
            @Override
            public boolean isDone()
            {
                isRed = false;
                return true;
            }
        });

        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .setCameraResolution(camSize)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();

        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");
    }

}