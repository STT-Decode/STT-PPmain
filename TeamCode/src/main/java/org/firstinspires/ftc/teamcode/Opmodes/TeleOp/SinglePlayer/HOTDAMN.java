package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotParts.All_Parts;
import org.firstinspires.ftc.teamcode.RobotParts.Drivetrain;

//the name is how this Opmode will show up on the driver-hub
@TeleOp(name = "HOTDAMN", group = "TeleOp")
public class HOTDAMN extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    All_Parts All_Parts = new All_Parts();

    @Override
    public void runOpMode() throws InterruptedException {
        //   All_Parts.init(hardwareMap);
        double power;
        double y;
        double x;
        double rotate;
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {


            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;


//            drivetrain.feeder(feederPosition);
            All_Parts.drive0(y, x, rotate, 1);
//
        }

    }
}
