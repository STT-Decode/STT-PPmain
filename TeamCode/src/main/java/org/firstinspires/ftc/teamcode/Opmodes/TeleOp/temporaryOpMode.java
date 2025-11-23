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
        double flywheelVelocity = 0.96;
        boolean speedChangeAllowed = true;
        double overtakeSpeed = 0;
        double overtakeServoSpeed;
        double rightTriggertime = 0;


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

            if (gamepad1.right_bumper && speedChangeAllowed)
            {
                flywheelVelocity += 0.02;
                speedChangeAllowed = false;
            }
            if (gamepad1.left_bumper && speedChangeAllowed)
            {
                flywheelVelocity -= 0.02;
                speedChangeAllowed = false;
            }
            if (!gamepad1.left_bumper && !gamepad1.right_bumper)
            {
                speedChangeAllowed = true;
            }

            if (gamepad1.dpad_up)
            {
                drivetrain.flywheels(flywheelVelocity);
            }
            else
            {
                drivetrain.flywheels(0);
            }
            if (gamepad1.right_trigger>0.3)
            {
                drivetrain.intake(1);
            }
            else
            {
                drivetrain.intake(0);
            }
            if (gamepad1.x){
                overtakeServoSpeed = 1;
            }
            else{
                overtakeServoSpeed = 0;
            }
            if (gamepad1.y)
            {
                overtakeSpeed=-1;
                overtakeServoSpeed = 1;

            } else
            {
                overtakeSpeed=0;
            }
            if (gamepad1.left_trigger > 0.1)
            {
                if (rightTriggertime == 0)
                {
                    rightTriggertime = runtime.milliseconds();
                }
                if (rightTriggertime + 1000 < runtime.milliseconds())
                {
                    overtakeSpeed = -1;
                    overtakeServoSpeed = 1;
                }
                drivetrain.flywheels(flywheelVelocity);
            }
            if (gamepad1.left_trigger < 0.1 && !gamepad1.y && !gamepad1.x && !gamepad1.dpad_up)
            {
                overtakeSpeed = 0;
                overtakeServoSpeed = 0;
                drivetrain.flywheels(flywheelVelocity);
                rightTriggertime = 0;
            }
            drivetrain.overtake(overtakeSpeed);
            drivetrain.servoOvertake(overtakeServoSpeed);
            drivetrain.drive0(y, x, rotate, 1);
            telemetry.addData("flywheelVelocity", flywheelVelocity);
            telemetry.update();
        }

    }
}