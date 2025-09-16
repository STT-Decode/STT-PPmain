package org.firstinspires.ftc.teamcode.Opmodes;

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
        double flywheelFeederPos = 0;
        double flyWheelSpeed = 0;
        boolean flyWheelToggle = true;

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            flyWheelSpeed = gamepad1.left_stick_y;
            allParts.setLfPower(flyWheelSpeed);
            if (gamepad1.a){flywheelFeederPos=0;}
            if (gamepad1.b){flywheelFeederPos=0.5;}
            allParts.setServo1pos(flywheelFeederPos);
            telemetry.addData("pos", flywheelFeederPos);
            telemetry.addData("flyWheelSpeed", flyWheelSpeed);
            telemetry.update();
        }


    }
}