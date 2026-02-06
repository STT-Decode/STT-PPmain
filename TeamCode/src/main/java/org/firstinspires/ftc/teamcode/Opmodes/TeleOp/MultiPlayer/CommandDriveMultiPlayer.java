package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MultiPlayer;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.teamcode.Subsystems.imuTest;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.bindings.BindingManager;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;

@TeleOp(name = "DriveMulti")
public class CommandDriveMultiPlayer extends rootOpMode
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
                Gamepads.gamepad1().leftStickY().deadZone(0.15),
                Gamepads.gamepad1().leftStickX().negate().deadZone(0.15),
                Gamepads.gamepad1().rightStickX().negate().deadZone(0.15).mapToRange(value -> value * 0.8)
        );

        BindingManager.update();
        driverControlled.schedule();
        BindingManager.update();

        int id = isRed ? 24 : 20;

        Command alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor, aprilTag, camSize, true);
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(new SequentialGroup(setFarScoringZone(true), alignWithAprilTag.requires(alignWithAprilTag)));
        Gamepads.gamepad1().leftBumper().whenBecomesTrue(new SequentialGroup(setFarScoringZone(false), alignWithAprilTag.requires(alignWithAprilTag)));

        //Flywheels
        Gamepads.gamepad2().rightTrigger().greaterThan(0.3)
                .whenBecomesTrue(Flywheel.INSTANCE::turnOn2)
                .whenBecomesFalse(Flywheel.INSTANCE::turnOff);

        Gamepads.gamepad2().leftBumper().whenBecomesTrue(Flywheel.INSTANCE.changeFlywheelPower(-0.02));
        Gamepads.gamepad2().rightBumper().whenBecomesTrue(Flywheel.INSTANCE.changeFlywheelPower(0.02));

        Gamepads.gamepad2().x()
                .whenBecomesTrue(Flywheel.INSTANCE.bumpFlywheelSpeed())
                .whenBecomesFalse(Flywheel.INSTANCE.unbumpFlywheelSpeed());

        //Intake
        Gamepads.gamepad2().leftTrigger().greaterThan(-1).whenTrue(() -> Intake.INSTANCE.setCustomPower(Gamepads.gamepad2().leftTrigger().get()).schedule());

        //Overtake
        Gamepads.gamepad2().a().whenBecomesTrue(Overtake.INSTANCE.turnOn())
                .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Gamepads.gamepad2().dpadLeft().whenBecomesTrue(Overtake.INSTANCE.reverse())
                                        .whenBecomesFalse(Overtake.INSTANCE.turnOff());

        Gamepads.gamepad2().dpadLeft().whenBecomesTrue(Intake.INSTANCE.reverse())
                                        .whenBecomesFalse(Intake.INSTANCE.turnOff());

        Gamepads.gamepad1().dpadUp().whenBecomesTrue(imuTest.INSTANCE.getimu());
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