package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.core.commands.Command;
import com.pedropathing.paths.Path;

@Autonomous(name = "HOLYSHITWILLITWORKCHAT??")
public class PPtest extends rootOpMode
{

    Pose startPose = new Pose(0, 0, 0);
    Pose endPose = new Pose(0, 2, 0);
    Path path = new Path(new BezierCurve(startPose, endPose));

    private Command autonomousRoutine() {
        return new SequentialGroup(
            new FollowPath(path, true, 0.5)
        );
    }

    @Override
    public void onStartButtonPressed() {

        autonomousRoutine().schedule();
    }
}