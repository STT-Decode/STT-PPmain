package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

@TeleOp(name = "RWD_outreachbots")

public class RWD_outreachbots extends LinearOpMode {

    DcMotor Left;
    DcMotor Right;

    @Override
    public void runOpMode() throws InterruptedException {
        Left = hardwareMap.get(DcMotor.class, "Left");
        Right = hardwareMap.get(DcMotor.class, "Right");

        //frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        //rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //rearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double y =gamepad1.left_stick_x;
            double x =-gamepad1.left_stick_y;

            double LPwr = (-y/2)-x;
            double RPwr = (y/2)-x;
            Left.setPower(LPwr);
            Right.setPower(RPwr);
        }
    }
}