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
        double flywheelVelocity = 10;
        boolean speedChangeAllowed = true;
        double feederPosition = 0.5;
        boolean toggleServoPos = true;
        double CPWR = 1;
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

            if (gamepad1.right_trigger > 0.2 || gamepad2.left_trigger > 0.2)
            {
                drivetrain.intake(1);
            }
            else
            {
                drivetrain.intake(0);
            }

            if (gamepad2.right_trigger > 0.2)
            {
                drivetrain.overtake(1);
            }
            else
            {
                drivetrain.overtake(0);
            }

            if (gamepad1.left_trigger>0.1 && toggleServoPos)
            {
                feederPosition = 0;

            }
            if (!(gamepad1.left_trigger>0.1))
            {

                feederPosition=0.32;
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
                drivetrain.flywheels(flywheelVelocity*100,drivetrain.getvelocity1(),-drivetrain.getvelocity2());
            }
            else
            {
                drivetrain.flywheels(0,drivetrain.getvelocity1(),-drivetrain.getvelocity2());
            }

            drivetrain.feeder(feederPosition);
            drivetrain.drive0(y, x, rotate, 1);
            telemetry.addData("flywheelVelocity", flywheelVelocity);
            telemetry.addData("feederPosition", feederPosition);
            telemetry.addData("flywheel1velocity",drivetrain.getvelocity1());
            telemetry.addData("flywheel2velocity",drivetrain.getvelocity2());
            telemetry.update();
        }

    }
}