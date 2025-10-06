package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.core.commands.Command;
import com.pedropathing.paths.Path;
import com.pedropathing.follower.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "HOLYSHITWILLITWORKCHAT??")
public class PPtest extends rootOpMode
{
    //public static PathBuilder builder = new PathBuilder(PedroComponent.follower());
    public static PathChain paths;

    private PathChain buildPaths(PathBuilder builder)
    {
        paths = builder.addPath(
                        // Line 1
                        new BezierLine(new Pose(50.000, 40.000), new Pose(50.000, 80.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
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
        autonomousRoutine().schedule();
    }
}