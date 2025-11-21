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
        double flywheelVelocity = 2400;
        boolean speedChangeAllowed = true;
        double overtakePosition2 = 0;
        double overtakePosition3 = 0.6;
        double overtakeSpeed = 0;


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
                flywheelVelocity += 100;
                speedChangeAllowed = false;
            }
            if (gamepad1.left_bumper && speedChangeAllowed)
            {
                flywheelVelocity -= 100;
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
            if (gamepad1.dpad_down)
            {
                drivetrain.intake(1);
            }
            else
            {
                drivetrain.intake(0);
            }
            if (gamepad1.x){overtakeSpeed=1;} else{overtakeSpeed=0;}
            drivetrain.overtake(overtakeSpeed);
            drivetrain.drive0(y, x, rotate, 1);
            telemetry.addData("flywheelVelocity", flywheelVelocity);

            telemetry.update();
        }

    }
}