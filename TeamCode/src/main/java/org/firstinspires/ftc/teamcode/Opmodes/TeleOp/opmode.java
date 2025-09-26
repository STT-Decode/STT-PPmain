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
        double intakepos=0;
        double flywheelFeederPos = 0;
        double flyWheelSpeed = 0;
        double flyWheelSpeed_2 = 0.9;
        boolean flyWheelToggle = true;
        boolean speedChangeAllowed = true;
        double flyWheelFactor=1;
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive())
        {
            flyWheelSpeed = gamepad1.left_stick_y;
            if (gamepad1.right_bumper && speedChangeAllowed){
                flyWheelSpeed_2 += 0.02;
                speedChangeAllowed = false;
            }
            if (gamepad1.left_bumper && speedChangeAllowed){
                flyWheelSpeed_2 -= 0.02;
                speedChangeAllowed = false;
            }
            if (!gamepad1.left_bumper && !gamepad1.right_bumper){speedChangeAllowed = true;}

            //allParts.setLfPower(flyWheelSpeed*flyWheelFactor);
            if (gamepad1.dpad_up){allParts.setLfPower(flyWheelSpeed_2);}
            else {allParts.setLfPower(0);}
            if (gamepad1.a){flywheelFeederPos=0;}
            if (gamepad1.b){flywheelFeederPos=0.35;}
            if (gamepad1.x){intakepos=0;}
            if (gamepad1.y){intakepos=0.5;}

//            if (gamepad1.a){
//                flywheelFeederPos=0;
//                intakepos=0.5;
//                flywheelTime = getRuntime();
//            }
//            if (getRuntime() - 0.8 > flywheelTime){
//                flywheelFeederPos=0.32;
//                intakepos=0;;
//            }
//
//            if (gamepad1.y){
//                intakepos=0.5;
//                intakeTime = getRuntime();
//            }
//            if (getRuntime() - 1 > intakeTime){
//                intakepos=0;
//            }


            allParts.setServo1pos(flywheelFeederPos);
            allParts.setServo2pos(intakepos);
            telemetry.addData("pos", flywheelFeederPos);
            telemetry.addData("flyWheelSpeed", flyWheelSpeed_2);
            //telemetry.addData("flyWheelSpeed", flyWheelSpeed);
            telemetry.update();
        }


    }
}