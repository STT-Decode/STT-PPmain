package org.firstinspires.ftc.teamcode.Opmodes.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutonomousSTA")

public class AutonomousSTA extends LinearOpMode {

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
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launchmotor2R = hardwareMap.get(DcMotor.class, "launchmotor2R");
        launchmotor3L = hardwareMap.get(DcMotor.class, "launchmotor3L");

        // frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launchmotor2R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launchmotor3L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
// motor koppelen aan de configuratie
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");
    }

    private void strafeLeft(double power, long time) {

        setDrivePower(
                -power, power,
                power, -power
        );

        sleep(time);
        stopDrive();
    }

    private void driveForward(double power, long time) {

        setDrivePower(
                power, power,
                power, power
        );

    }

    private void setDrivePower(
            double fl, double fr,
            double rl, double rr) {

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        rearLeft.setPower(rl);
        rearRight.setPower(rr);
    }

    private void stopDrive() {

        setDrivePower(0, 0, 0, 0);
        waitForStart();
        if (opModeIsActive()) {

        }

        if (opModeIsActive()) {

// Eerst links strafen eerste getal is power tweede is hoelang
            strafeLeft(1, 1000);

// Daarna vooruit rijden
            driveForward(1, 2000);
        }

        stopDrive();
    }
}