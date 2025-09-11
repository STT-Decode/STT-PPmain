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
        double y;
        double x;
        double rotate;
        double speed;
        double servoSpeed;
        double flyWheelSpeed = 0;
        boolean flyWheelToggle = true;

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            rotate = -0.3 * gamepad1.right_stick_x;
            speed = 0.5 + gamepad1.right_trigger * 0.5;
            servoSpeed = 0.3 * ((gamepad1.left_bumper ? -1 : 0) + (gamepad1.right_bumper ? 1 : 0));

            if (gamepad1.a && flyWheelToggle) {
                flyWheelSpeed = -flyWheelSpeed + 0.5;
                flyWheelToggle = false;
            } else if (!gamepad1.a){
                flyWheelToggle = true;
                }

            allParts.setServoPower(servoSpeed);
            allParts.setFlyWheelPower(flyWheelSpeed);
            allParts.drive0(y, x, rotate, speed);
        }


    }
}