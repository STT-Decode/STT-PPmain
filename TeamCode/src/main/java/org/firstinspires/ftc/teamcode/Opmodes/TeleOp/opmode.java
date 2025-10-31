package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotParts.All_Parts;

//the name is how this Opmode will show up on the driver-hub
@TeleOp(name = "opmode", group = "TeleOp")
public class opmode extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    All_Parts allParts = new All_Parts();

    @Override
    public void runOpMode() throws InterruptedException
    {
        allParts.init(hardwareMap);
        double y;
        double x;
        double rotate;
        double speed;
        double intakeTime = 0;
        double flywheelTime = 0;
        double intakepos = 0;
        double flywheelFeederPos = 0;
        double flyWheelSpeed = 0;
        double flyWheelSpeed_2 = 0.9;
        boolean flyWheelToggle = true;
        boolean speedChangeAllowed = true;
        double flyWheelFactor = 1;

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
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
                allParts.setLfPower(flyWheelSpeed_2);
            } else if (gamepad1.dpad_down)
            {
                allParts.ninjitsu(flyWheelSpeed_2);
            } else
            {
                allParts.setLfPower(0);
            }

            telemetry.addData("pos", flywheelFeederPos);
            telemetry.addData("flyWheelSpeed", flyWheelSpeed_2);
            //telemetry.addData("flyWheelSpeed", flyWheelSpeed);
            telemetry.update();
        }

    }
}