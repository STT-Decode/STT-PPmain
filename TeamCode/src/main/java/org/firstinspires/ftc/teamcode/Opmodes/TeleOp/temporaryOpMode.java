package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import static java.lang.Math.abs;

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
        double flywheelVelocity = 10;
        boolean speedChangeAllowed = true;
        double servo1speed=0;
        double servo2speed=0;
        boolean toggleOvertake2 = true;
        boolean toggleOvertake3 = true;
        double overtakePosition2 = 0;
        double overtakePosition3 = 0.6;


        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            if (gamepad1.right_bumper)
            {
                y = -gamepad1.left_stick_y * 0.5;
                x = gamepad1.left_stick_x * 0.5;
                rotate = -gamepad1.right_stick_x * 0.5;
            }
            else
            {
                y = -gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                rotate = -gamepad1.right_stick_x * 0.7;
            }

            if (gamepad2.right_bumper && speedChangeAllowed)
            {
                flywheelVelocity += 1;
                speedChangeAllowed = false;
            }
            if (gamepad2.left_bumper && speedChangeAllowed)
            {
                flywheelVelocity -= 1;
                speedChangeAllowed = false;
            }
            if (!gamepad2.left_bumper && !gamepad2.right_bumper)
            {
                speedChangeAllowed = true;
            }

            if (gamepad2.dpad_up)
            {
                drivetrain.flywheels(flywheelVelocity);
            }
            else
            {
                drivetrain.flywheels(0);
            }
            if (gamepad2.dpad_down)
            {
                drivetrain.intake(1);
            }
            else
            {
                drivetrain.intake(0);
            }
            if (gamepad2.x){servo1speed=1;} else{servo1speed=0;}
            if (gamepad2.y && toggleOvertake2)
            {
                overtakePosition2 = -overtakePosition2 + 0.4;
                toggleOvertake2 = false;
            }
            if (!gamepad2.y)
            {
                toggleOvertake2 = true;
            }
            if (gamepad2.b && toggleOvertake3)
            {
                overtakePosition3 = -overtakePosition3 + 0.6;
                toggleOvertake3 = false;
            }
            if (!gamepad2.b)
            {
                toggleOvertake3 = true;
            }
            drivetrain.setServo1(servo1speed);
            drivetrain.setServo2pos(overtakePosition2);
            drivetrain.setServo3pos(overtakePosition3);
            drivetrain.drive0(y, x, rotate, 1);
            telemetry.addData("flywheelVelocity in RPS", flywheelVelocity);
            telemetry.addData("flywheelVelocity in RPM", flywheelVelocity * 60);
            telemetry.addData("overtakePosition2", overtakePosition2);
            telemetry.addData("overtakePosition3", overtakePosition3);
            telemetry.update();
        }

    }
}