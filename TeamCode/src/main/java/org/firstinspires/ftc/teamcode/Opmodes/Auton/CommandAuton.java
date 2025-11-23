package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

public class CommandAuton extends rootOpMode
{
    @Override
    public void onStartButtonPressed()
    {
        double startTime = runtime.milliseconds();

        while (opModeIsActive())
        {
            double time = runtime.milliseconds() - startTime;

            if (time < 700)
            {
                backLeftMotor.setPower(-0.6);
                frontLeftMotor.setPower(-0.6);
                frontRightMotor.setPower(0.6);
                backRightMotor.setPower(0.6);
            } else
            {
                backRightMotor.setPower(0);
                frontRightMotor.setPower(0);
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
            }
        }
    }
}
