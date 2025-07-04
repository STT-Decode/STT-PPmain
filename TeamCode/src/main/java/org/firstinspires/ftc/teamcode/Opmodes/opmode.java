package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotParts.All_Parts;


//the name is how this Opmode will show up on the driver-hub
@TeleOp(name = "Opmode", group = "TeleOp")
public class opmode extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    All_Parts allParts = new All_Parts();

    @Override
    public void runOpMode() throws InterruptedException
    {
        allParts.init(hardwareMap);
        double clawPos = 0;
        double rotationPos = 0;
        boolean canToggleClaw = true;
        boolean canToggleRotation = true;

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rotate = -0.3 * gamepad1.right_stick_x;
            double speed = 0.5 + gamepad1.right_trigger * 0.5;
            double armPower = -0.7 * gamepad2.right_stick_y;

            if (canToggleClaw && gamepad2.left_bumper)
            {
                clawPos = Math.abs(clawPos - 1);
                canToggleClaw = false;
            } else if (!gamepad2.left_bumper)
            {
                canToggleClaw = true;
            }

            if (canToggleRotation && gamepad2.right_bumper)
            {
                rotationPos = Math.abs(rotationPos - 1);
                canToggleRotation = false;
            } else if (!gamepad2.right_bumper)
            {
                canToggleRotation = true;
            }

            allParts.drive0(y, x, rotate, speed);
            allParts.setArmPower(armPower);
            allParts.setClawPos(clawPos);
            allParts.setRotationPos(rotationPos);
        }


    }
}