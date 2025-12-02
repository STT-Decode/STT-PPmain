package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;

@Autonomous(name = "auton")
public class Auton extends rootOpMode
{


    Pose farScoringZone = new Pose(80, 8, Math.PI * 0.5);

    Pose closeArtifacts = new Pose(90, 35, 0);

    boolean isRed = true;

    double alianceXfactor;

    AlignWithAprilTag alignWithAprilTag;

    SequentialGroup load = new SequentialGroup(
            Overtake.INSTANCE.turnOn(),
            new Delay(0.7),
            Overtake.INSTANCE.turnOff());

    SequentialGroup shootThree = new SequentialGroup(
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOn),
            Feeder.INSTANCE.fire(),
            load,
            Feeder.INSTANCE.fire(),
            load,
            Feeder.INSTANCE.fire(),
            new LambdaCommand().setStart(Flywheel.INSTANCE::turnOff)
    );

    private Command autonomousRoutine() {
        PathBuilder builder = new PathBuilder(PedroComponent.follower());
        int id = isRed ? 24 : 20;

        alignWithAprilTag = new AlignWithAprilTag(hardwareMap, id, backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);

        return new SequentialGroup(
                alignWithAprilTag,
                shootThree,

                //Drive to close artifacts
                new FollowPath(builder.addPath(
                                new BezierCurve(PedroComponent.follower().getPose(), closeArtifacts))
                        .setLinearHeadingInterpolation(PedroComponent.follower().getHeading(), closeArtifacts.getHeading())
                        .build(), false, 0.5)

                //Take close artifacts in
                /*
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
                 */
                );
    }

    @Override
    public void onStartButtonPressed() {
        alianceXfactor = isRed ? 1 : -1;
        PedroComponent.follower().setPose(farScoringZone);
        autonomousRoutine().schedule();
    }

    @Override
    public void onInit()
    {
        ActiveOpMode.telemetry().addData("Aliance", "Red");
        ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
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
                return false;
            }
        });
    }
}