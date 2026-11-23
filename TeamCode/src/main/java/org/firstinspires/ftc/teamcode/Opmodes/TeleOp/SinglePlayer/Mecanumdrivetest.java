package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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

@TeleOp(name = "Mecanumdrivetest")

public class Mecanumdrivetest extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    DcMotor intake;
    DcMotor launchmotor2R;
    DcMotor launchmotor3L;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight= hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launchmotor2R = hardwareMap.get(DcMotor.class, "launchmotor2R");
        launchmotor3L = hardwareMap.get(DcMotor.class, "launchmotor3L");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launchmotor2R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launchmotor3L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double rt2 = gamepad1.right_trigger;
            double lt2 = gamepad1.left_trigger;
            double rb1 = gamepad1.left_bumper ? 1.0 : 0.0;

            double fLPwr = Math.min(y+x+rx, 1);
            double fRPwr = Math.min(y-x-rx, 1);
            double rLPwr = Math.min(y-x+rx, 1);
            double rRPwr = Math.min(y+x-rx, 1);
            double intakepower = lt2;
            double launchpower = rt2;

            if (lt2 == 0) {
                intakepower = rb1 * -0.5;
            }

            frontLeft.setPower(fLPwr);
            frontRight.setPower(fRPwr);
            rearLeft.setPower(rLPwr);
            rearRight.setPower(rRPwr);
            intake.setPower(intakepower);
            launchmotor2R.setPower(launchpower);
            launchmotor3L.setPower(launchpower);
        }
    }
}
