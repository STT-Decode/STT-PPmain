package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.RobotParts.Drivetrain;

@Autonomous(name = "autonomous")
public class ScrimmageAutonomous extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain Drivetrain = new Drivetrain();
        Drivetrain.init(hardwareMap);
        double passedTime = 0;

        waitForStart();
        double startuptime = runtime.milliseconds();
        while (opModeIsActive())
        {
            passedTime = runtime.milliseconds() - startuptime;
            if (passedTime > 4000 && passedTime < 4500)
            {
                Drivetrain.drive0(1,0,0,1);
            }
            else
            {
                Drivetrain.drive0(0,0,0,1);
            }
            if (passedTime < 2500)
            {
                //Drivetrain.flywheels(0.96);
            }
            else
            {
                //Drivetrain.flywheels(0);
            }
            if (passedTime < 2000 && passedTime > 500)
            {
                Drivetrain.feeder(1);
                Drivetrain.overtake(-1);
                Drivetrain.intake(1);
            }
            else if (passedTime < 4000)
            {
                Drivetrain.feeder(1);
                Drivetrain.overtake(-1);
                Drivetrain.intake(0);
            }
            else
            {
                Drivetrain.feeder(0);
                Drivetrain.overtake(0);
                Drivetrain.intake(0);
            }
        }
    }
}