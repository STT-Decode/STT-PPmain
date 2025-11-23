package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

import dev.nextftc.core.commands.Command;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;

//@Autonomous(name = "auton")
public class Auton extends rootOpMode
{
    //public static PathBuilder builder = new PathBuilder(PedroComponent.follower());
    public static PathChain paths;
    Pose startPose = new Pose(105, 8, 90);

    private PathChain buildPaths(PathBuilder builder)
    {
        paths = builder.addPath(
                        // Line 1
                        new BezierCurve(startPose, new Pose(105, 83, 90))
                )
                .setTangentHeadingInterpolation()
                .build();
        return paths;
    }

    private Command autonomousRoutine() {
        PathBuilder builder = new PathBuilder(PedroComponent.follower());
        buildPaths(builder);
        return new FollowPath(paths, false, 0.5);
    }

    @Override
    public void onStartButtonPressed() {
        PedroComponent.follower().setPose(startPose);
        autonomousRoutine().schedule();
    }
}