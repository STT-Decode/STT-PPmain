package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Turryturret")

public class TeleTurret extends LinearOpMode {

    DcMotor flywheel1;
    DcMotor flywheel2;
    Servo yaw;
    CRServo feeder;


    @Override
    public void runOpMode() throws InterruptedException {
        flywheel1 = hardwareMap.get(DcMotor.class, "frontLeft");
        flywheel2 = hardwareMap.get(DcMotor.class, "frontRight");
        yaw = hardwareMap.get(Servo.class,"yaw");
        feeder = hardwareMap.get(CRServo.class,"feeder");
//als de voorkant de andere kant is verwissel reverse met forward en andersom


        waitForStart();

        while (opModeIsActive()) {
            feeder.setPower(gamepad1.right_trigger);
            if(gamepad1.a){
                yaw.setPosition(0);
            }
            if (gamepad1.b){
                yaw.setPosition(1);
            }
            flywheel1.setPower(gamepad1.left_trigger);
            flywheel2.setPower(gamepad1.left_trigger);

        }
    }
}