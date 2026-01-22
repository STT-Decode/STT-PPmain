package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.vision.VisionPortal;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "motortest")
public class MotorTest extends rootOpMode
{
    boolean isRed = true;


    @Override
    public void onStartButtonPressed()
    {
        BindingManager.update();
        frontLeftMotor.setPower(1);
        sleep(1000);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(1);
        sleep(1000);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(1);
        sleep(1000);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(1);
        sleep(1000);
        backRightMotor.setPower(0);

    }

}