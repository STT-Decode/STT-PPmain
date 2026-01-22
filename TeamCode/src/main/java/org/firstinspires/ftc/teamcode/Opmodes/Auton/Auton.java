package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.vision.VisionPortal;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.TurnTo;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;

@Autonomous(name = "PedroAuton")
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

    SequentialGroup shoot = new SequentialGroup(
            Feeder.INSTANCE.fire(),
            new Delay(0.2)
    );

    SequentialGroup shootThree = new SequentialGroup(
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOn),
            new Delay(3),
            shoot,
            load,
            shoot,
            Intake.INSTANCE.turnOn(),
            new Delay(2),
            load,
            shoot,
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOff)
    );

    private Command autonomousRoutine() {
        PathBuilder builder = new PathBuilder(PedroComponent.follower());
        int id = isRed ? 24 : 20;

        alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor, visionPortal);

        return new SequentialGroup(
                alignWithAprilTag,
                /*,
                new Command()
                {
                    @Override
                    public boolean isDone()
                    {
                        PedroComponent.follower().setPose(farScoringZone.getPose().minus(new Pose(0,0, 12)));
                        return true;
                    }
                }*/
                shootThree,
                //Drive to close artifacts
                new FollowPath(builder.addPath(
                                new BezierCurve(PedroComponent.follower().getPose(),
                                        new Pose(85, 39),
                                        closeArtifacts))
                        .setLinearHeadingInterpolation(PedroComponent.follower().getHeading(), closeArtifacts.getHeading())
                        .build(), false, 0.5),

                //Take close artifacts in
                Intake.INSTANCE.turnOn(),
                new FollowPath(builder.addPath(
                                new BezierCurve(PedroComponent.follower().getPose(), PedroComponent.follower().getPose().plus(new Pose(40, 0))))
                        .setConstantHeadingInterpolation(180)
                        .build(), false, 0.5),
                Intake.INSTANCE.turnOff(),

                //Drive back to far shooting zone
                new FollowPath(builder.addPath(
                                new BezierCurve(PedroComponent.follower().getPose(), farScoringZone))
                        .setLinearHeadingInterpolation(PedroComponent.follower().getHeading(), farScoringZone.getHeading())
                        .build(), false, 0.5),


                alignWithAprilTag,

                shootThree
        );
    }

    @Override
    public void onStartButtonPressed() {
        PedroComponent.follower().setPose(farScoringZone);
        autonomousRoutine().schedule();
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
                farScoringZone = farScoringZone.mirror();
                closeArtifacts = closeArtifacts.mirror();
                return true;
            }
        });

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .setCameraResolution(camSize)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();
    }
}