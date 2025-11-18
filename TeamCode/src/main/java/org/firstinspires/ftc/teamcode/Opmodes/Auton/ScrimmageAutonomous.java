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
            if (passedTime > 3000 && passedTime < 3500)
            {
                Drivetrain.drive0(1,0,0,1);
            }
            else
            {
                Drivetrain.drive0(0,0,0,1);
            }
            if (passedTime < 2500)
            {
                Drivetrain.flywheels(0.98);
            }
            else
            {
                Drivetrain.flywheels(0);
            }
            if (passedTime < 1250 && passedTime > 500)
            {
                Drivetrain.setServo3pos(0);
            }
            else if (passedTime < 2000)
            {
                Drivetrain.setServo2pos(0.4);
            }
            else if (passedTime < 3750)
            {
                Drivetrain.setServo3pos(0.6);
                Drivetrain.setServo2pos(0);
            }
        }
    }
}