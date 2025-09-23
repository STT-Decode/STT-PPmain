package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotParts.All_Parts;


//the name is how this Opmode will show up on the driver-hub
@TeleOp(name = "motor", group = "TeleOp")
public class motor extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    All_Parts allParts = new All_Parts();

    @Override
    public void runOpMode() throws InterruptedException
    {
        allParts.init(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {

            double armPower = gamepad1.right_stick_y;

            allParts.setLfPower(armPower);
        }


    }
}