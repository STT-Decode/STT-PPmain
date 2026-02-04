package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MultiPlayer;

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
        double power1=0;
        double power2=0;
        double y;
        double x;
        double rotate;
        double flywheelVelocity = 1950;
        boolean speedChangeAllowed = true;
        boolean shootingsequence=false;
        boolean shoottoggle=false;
        double feederPosition = 0;
        boolean toggleServoPos = true;
        double CPWR = 1;
        double ms=0;
        double starttime=0;
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()){
            ms=runtime.milliseconds();
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
            if (abs(x)+abs(y)<0.1){
                x=0;
                y=0;
            }
            shootingsequence=gamepad2.dpad_down;
            if(!shootingsequence)
            {
                if (gamepad1.right_trigger > 0.2 || gamepad2.left_trigger > 0.2)
                {
                    drivetrain.intake(-1);
                } else
                {
                    drivetrain.intake(0);
                }

                if (gamepad2.dpad_right && !gamepad2.x)
                {
                    drivetrain.overtake(-0.8);
                } else if (gamepad2.dpad_left && !gamepad2.x)
                {
                    drivetrain.overtake(1);
                    drivetrain.intake(1);
                } else
                {
                    drivetrain.overtake(0);
                }

                if (gamepad2.right_bumper && speedChangeAllowed)
                {
                    flywheelVelocity += 50;
                    speedChangeAllowed = false;
                }
                if (gamepad2.left_bumper && speedChangeAllowed)
                {
                    flywheelVelocity -= 50;
                    speedChangeAllowed = false;
                }
                if (!gamepad2.left_bumper && !gamepad2.right_bumper)
                {
                    speedChangeAllowed = true;
                }

                if (gamepad2.right_trigger > 0.2)
                {
                    drivetrain.flywheels(flywheelVelocity/2500);
                } else
                {
                    drivetrain.flywheels(0);
                }

                shoottoggle=false;
                }else{
                if(!shoottoggle){
                    starttime=ms;
                }
                shoottoggle=true;
                if(ms-starttime<1500){
                drivetrain.backkupflywheels(flywheelVelocity);
                power1=drivetrain.getPower1();
                power2=drivetrain.getPower2();
            }

            if(ms-starttime>=1500){
                drivetrain.flywheels((power2-power1)/2);
                drivetrain.overtake(-0.8);
                drivetrain.intake(-1);
            }else if(ms-starttime<500)
            {drivetrain.overtake(1);
            } else{drivetrain.overtake(0);}}




            drivetrain.drive0(y, x, rotate, 1);
            telemetry.addData("ms-starttinme",ms-starttime);
            telemetry.addData("flywheelVelocity", flywheelVelocity/2500);
            telemetry.addData("feederPosition", feederPosition);
            telemetry.addData("flywheel1velocity",drivetrain.getvelocity1());
            telemetry.addData("flywheel2velocity",drivetrain.getvelocity2());
            telemetry.addData("additionalpower1",drivetrain.getPower1());
            telemetry.addData("additionalpower2",drivetrain.getPower2());
            telemetry.update();
        }

    }
}