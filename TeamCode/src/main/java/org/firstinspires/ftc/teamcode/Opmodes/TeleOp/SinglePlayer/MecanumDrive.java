package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTagTest;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.FlywheelTest;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.teamcode.Subsystems.imuTest;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

@TeleOp(name = "MecanumDriveSTA")

public class MecanumDrive extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    DcMotor intake;
    DcMotor launchmotor;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight= hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launchmotor = hardwareMap.get(DcMotor.class, "launchmotor");

        waitForStart();

        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double rb2 = gamepad1.right_trigger;
            double lb2 = gamepad1.left_trigger;

            double fLPwr = y + x + rx;
            double fRPwr = y - x - rx;
            double rLPwr = y - x + rx;
            double rRPwr = y + x - rx;
            double intakepower = lb2;
            double launchpower = rb2;

            double max = Math.max(
                    1.0,
                    Math.max(
                            Math.abs(fLPwr),
                            Math.max(
                                    Math.abs(fRPwr),
                                    Math.max(Math.abs(rLPwr), Math.abs(rRPwr))
                            )
                    )
            );

            frontLeft.setPower(fLPwr / max);
            frontRight.setPower(fRPwr / max);
            rearLeft.setPower(rLPwr / max);
            rearRight.setPower(rRPwr / max);
            intake.setPower(intakepower / 1.5);
            launchmotor.setPower(launchpower);
        }
    }
}
