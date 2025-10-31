package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotParts.OutreachBotsDrivetrain;

@TeleOp(name = "OutreachBot", group = "TeleOp")
//Naam van project
public class OutreachBots extends LinearOpMode {
    OutreachBotsDrivetrain drivetrain = new OutreachBotsDrivetrain();

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.init(hardwareMap);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double rotate = gamepad1.right_stick_x;
            drivetrain.drive1(y, rotate);
        }
    }
}
