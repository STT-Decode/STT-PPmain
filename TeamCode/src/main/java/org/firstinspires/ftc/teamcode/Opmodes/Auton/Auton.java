package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;

@Autonomous(name = "autono")
public class Auton extends rootOpMode
{
    Pose farScoringZone = new Pose(80, 8, 90);

    Pose closeArtifacts = new Pose(100, 36, 180);

    boolean isRed = true;
    double blueFactor;

    AlignWithAprilTag alignWithAprilTag;

    SequentialGroup load = new SequentialGroup(
            Overtake.INSTANCE.turnOn(),
            new Delay(0.7),
            Overtake.INSTANCE.turnOff());

    SequentialGroup shootAll = new SequentialGroup(
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOn),
            new Delay(2.2),
            Overtake.INSTANCE.turnOn(),
            new Delay(0.3),
            Overtake.INSTANCE.turnOff(),
            new Delay(1),
            Overtake.INSTANCE.turnOn(),
            new Delay(1),
            Intake.INSTANCE.turnOn(),
            new Delay(2),
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOff),
            Overtake.INSTANCE.turnOff(),
            Intake.INSTANCE.turnOff()
    );


    private Command autonomousRoutine() {

        double batteryFactor = 1 / 1.3;

        return new SequentialGroup(
                Drivetrain.INSTANCE.drive(3, 0.3),
                Drivetrain.INSTANCE.turn(12 * blueFactor, 0.3 * batteryFactor),
                new Delay(1.6),
                alignWithAprilTag,
                shootAll,
                Drivetrain.INSTANCE.turn(-12 * blueFactor, 0.3 * batteryFactor),
                Drivetrain.INSTANCE.drive(14, 0.7 * batteryFactor),
                Drivetrain.INSTANCE.turn(-70 * blueFactor, 0.5 * batteryFactor),
                Intake.INSTANCE.turnOn(),
                Overtake.INSTANCE.turnOn(),
                Drivetrain.INSTANCE.drive(-58, 0.4 * batteryFactor),
                Intake.INSTANCE.turnOff(),
                Overtake.INSTANCE.turnOff(),
                Drivetrain.INSTANCE.drive(48, 0.4 * batteryFactor),
                Drivetrain.INSTANCE.turn(70 * blueFactor, 0.5 * batteryFactor),
                Drivetrain.INSTANCE.drive(-12, 0.6 * batteryFactor),
                new Delay(0.5),
                Drivetrain.INSTANCE.turn(10 * blueFactor, 0.3 * batteryFactor),
                new Delay(0.5),
                Overtake.INSTANCE.reverse(),
                new Delay(0.2),
                Overtake.INSTANCE.turnOff(),
                new Delay(0.7),
                alignWithAprilTag,
                shootAll,
                Drivetrain.INSTANCE.drive(15, 0.5 * batteryFactor)
        );
    }

    @Override
    public void onStartButtonPressed() {
        PedroComponent.follower().setPose(farScoringZone);
        int id = isRed ? 24 : 20;
        blueFactor = isRed ? 1 : -1;
        alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor, aprilTag, camSize, false);
        autonomousRoutine().schedule();
    }

    @Override
    public void onInit()
    {
        OTOS = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
        Drivetrain.INSTANCE.configureOtos();

        updateTelemetry().schedule();
        ActiveOpMode.telemetry().addData("Alliance", isRed ? "Red" : "Blue");
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