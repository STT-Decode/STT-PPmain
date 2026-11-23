package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MultiPlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MultiSTA")

public class MultiSTA extends LinearOpMode {

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

     //   frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
     //   frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
     //   rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
     //   rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launchmotor2R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launchmotor3L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double rb2 = gamepad2.right_trigger;
            double lb2 = gamepad2.left_trigger;
            double intakecontrol = gamepad2.right_bumper ? 1.0 : 0.0;
            double intakecontrolreverse = gamepad2.left_bumper ? -1.0 : 0.0;

            double fLPwr = -y +x + rx;
            double fRPwr = -y -x - rx;
            double rLPwr = y +x - rx;
            double rRPwr = y -x + rx;
            double launchpower3L = lb2;
            double launchpower2R = rb2;
            double intakepower = 0;

            if (intakecontrol == 0) {
                intakepower = intakecontrolreverse / 2; // IntakeBack is 2x as slow to control which balls to let out more carefully
            } else {
                intakepower = intakecontrol;
            }

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
            launchmotor3L.setPower(launchpower3L);
            launchmotor2R.setPower(launchpower2R);
            intake.setPower(intakepower);

        }
    }
}