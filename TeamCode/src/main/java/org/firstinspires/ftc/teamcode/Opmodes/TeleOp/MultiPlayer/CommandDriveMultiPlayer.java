package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MultiPlayer;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.bindings.BindingManager;

@TeleOp(name = "DriveMulti")
public class CommandDriveMultiPlayer extends rootOpMode
{
    boolean isRed = true;
    boolean shootingClose = false;

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
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(new Command(){
            @Override
            public boolean isDone()
            {
                shootingClose = true;
                return true;
            }
        });
        Gamepads.gamepad1().leftBumper().whenBecomesTrue(new Command(){
            @Override
            public boolean isDone()
            {
                shootingClose = false;
                return true;
            }
        });
        double offset = shootingClose ? 0 : 50;

        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor, aprilTag, camSize, offset);
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(alignWithAprilTag);
        Gamepads.gamepad1().leftBumper().whenBecomesTrue(alignWithAprilTag);

        //Flywheels
        Gamepads.gamepad2().rightTrigger().greaterThan(0.3)
                .whenBecomesTrue(Flywheel.INSTANCE::turnOn)
                .whenBecomesFalse(Flywheel.INSTANCE::turnOff);

        //Intake
        Gamepads.gamepad2().leftTrigger().greaterThan(-1).whenTrue(() -> Intake.INSTANCE.setCustomPower(Gamepads.gamepad2().leftTrigger().get()).schedule());

        //Overtake
        Gamepads.gamepad2().a().whenBecomesTrue(Overtake.INSTANCE.turnOn())
                .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Gamepads.gamepad2().dpadLeft().whenBecomesTrue(Overtake.INSTANCE.reverse())
                                        .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Gamepads.gamepad2().dpadLeft().whenBecomesTrue(Intake.INSTANCE.reverse())
                                        .whenBecomesFalse(Intake.INSTANCE.turnOff());

        Gamepads.gamepad2().rightBumper()
                .whenBecomesTrue(Flywheel.INSTANCE.increaseFlywheelSpeed(50));

        Gamepads.gamepad2().leftBumper()
                .whenBecomesTrue(Flywheel.INSTANCE.increaseFlywheelSpeed(-50));

    }

    @Override
    public void onInit()
    {
        ActiveOpMode.telemetry().addData("Aliance", "Red");
        Gamepads.gamepad1().b().whenBecomesTrue(new Command()
        {
            @Override
            public boolean isDone()
            {
                ActiveOpMode.telemetry().addData("Aliance", "Blue");
                ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
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
    }

}