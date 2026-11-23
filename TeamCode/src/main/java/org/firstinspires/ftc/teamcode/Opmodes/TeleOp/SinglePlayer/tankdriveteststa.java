package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Tankdrivetest")

public class tankdriveteststa extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight= hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");



        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //  rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        while (opModeIsActive()) {

            double yL = gamepad1.left_stick_y;
            double yR = gamepad1.right_stick_y;


            double fLPwr = yL;
            double fRPwr = yR;
            double rLPwr = yL;
            double rRPwr = yR;


            frontLeft.setPower(fLPwr);
            frontRight.setPower(fRPwr);
            rearLeft.setPower(rLPwr);
            rearRight.setPower(rRPwr);
        }
    }
}


