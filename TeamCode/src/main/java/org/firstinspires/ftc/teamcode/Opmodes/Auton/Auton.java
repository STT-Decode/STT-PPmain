package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathBuilder;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.TurnTo;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.powerable.SetPower;

@Autonomous(name = "autono")
public class Auton extends rootOpMode
{
    Pose farScoringZone = new Pose(80, 8, 90);

    Pose closeArtifacts = new Pose(100, 36, 180);

    boolean isRed = true;

    AlignWithAprilTag alignWithAprilTag;

    SequentialGroup load = new SequentialGroup(
            Overtake.INSTANCE.turnOn(),
            new Delay(0.7),
            Overtake.INSTANCE.turnOff());

    SequentialGroup shootAll = new SequentialGroup(
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOn),
            new Delay(2),
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

        return new SequentialGroup(
                alignWithAprilTag,
                shootAll,
                Drivetrain.INSTANCE.turn(-12, 0.3),
                Drivetrain.INSTANCE.drive(10, 0.7),
                Drivetrain.INSTANCE.turn(-70, 0.5),
                Intake.INSTANCE.turnOn(),
                Overtake.INSTANCE.turnOn(),
                Drivetrain.INSTANCE.drive(-20, 0.4),
                Intake.INSTANCE.turnOff(),
                Overtake.INSTANCE.turnOff(),
                Drivetrain.INSTANCE.drive(23, 0.4),
                Drivetrain.INSTANCE.turn(70, 0.5),
                Drivetrain.INSTANCE.drive(-10, 0.6),
                new Delay(0.5),
                Drivetrain.INSTANCE.turn(10, 0.3),
                new Delay(0.5),
                alignWithAprilTag,
                shootAll
        );
    }

    @Override
    public void onStartButtonPressed() {
        PedroComponent.follower().setPose(farScoringZone);
        int id = isRed ? 24 : 20;
        alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor, aprilTag, camSize, false);
        autonomousRoutine().schedule();
    }

    @Override
    public void onInit()
    {
        OTOS = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
        Drivetrain.INSTANCE.configureOtos();

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


        imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();
    }
}