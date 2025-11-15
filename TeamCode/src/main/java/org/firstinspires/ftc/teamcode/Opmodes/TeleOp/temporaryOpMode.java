package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotParts.Drivetrain;

//the name is how this Opmode will show up on the driver-hub
@TeleOp(name = "temporaryOpMode", group = "TeleOp")
public class temporaryOpMode extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() throws InterruptedException
    {
        drivetrain.init(hardwareMap);
        double y;
        double x;
        double rotate;
        double flywheelFeederPos = 0;
        double flyWheelSpeed_2 = 0.9;
        boolean speedChangeAllowed = true;
        double servo1speed=0;
        double servo2speed=0;
        boolean toggleOvertake2
        boolean toggleOvertake3
                double overtakePosition

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            if (gamepad1.right_bumper && speedChangeAllowed)
            {
                flyWheelSpeed_2 += 0.02;
                speedChangeAllowed = false;
            }
            if (gamepad1.left_bumper && speedChangeAllowed)
            {
                flyWheelSpeed_2 -= 0.02;
                speedChangeAllowed = false;
            }
            if (!gamepad1.left_bumper && !gamepad1.right_bumper)
            {
                speedChangeAllowed = true;
            }

            if (gamepad1.dpad_up)
            {
                drivetrain.flywheels(flyWheelSpeed_2);
            }
            else
            {
                drivetrain.flywheels(0);
            }
            if (gamepad1.dpad_down)
            {
                drivetrain.intake(-1);
            }
            else
            {
                drivetrain.intake(0);
            }

            if (gamepad1.x){servo1speed=1;} else{servo1speed=0;}
            //if (gamepad1.b){servo2speed=-1;} else{servo2speed=0;}
            drivetrain.setServo1(servo1speed);
            drivetrain.setServo2(servo2speed);
            drivetrain.drive0(y, x, rotate, 1);
            telemetry.addData("flyWheelSpeed", flyWheelSpeed_2);
            telemetry.update();
        }

    }
}