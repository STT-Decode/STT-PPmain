package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.SinglePlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "SingleSTA")

public class SingleSTA extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    DcMotor intake;
    DcMotor launchmotor2R;
    DcMotor launchmotor3L;
    DcMotor turretupdownservo;

    boolean lastup = false;
    boolean lastdown = false;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight= hardwareMap.get(DcMotor.class, "frontRight");
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

        waitForStart();

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double rb2 = gamepad1.right_trigger;
            double lb2 = gamepad1.left_trigger;
            double intakecontrol = gamepad1.right_bumper ? 1.0 : 0.0;
            double intakecontrolreverse = gamepad1.left_bumper ? -1.0 : 0.0;

            double fLPwr = Math.min(y+x+rx, 1);
            double fRPwr = Math.min(y-x-rx, 1);
            double rLPwr = Math.min(y-x+rx, 1);
            double rRPwr = Math.min(y+x-rx, 1);
            double launchpower3L = lb2;
            double launchpower2R = rb2;
            double intakepower = 0;

            if (intakecontrol == 0) {
                intakepower = intakecontrolreverse / 2; // IntakeBack is 2x as slow to control which balls to let out more carefully
            } else {
                intakepower = intakecontrol;
            }

            //Turret omhoog en omlaag door een servo: door lastup en up te gebruiken gaat deze telkens maar
            //1 stapje omhoog als je hem opnieuw indrukt ipv 10 keer een stapje omdat je niet meteen loslaat
            boolean turret_up = gamepad1.dpad_up;
            boolean turret_down = gamepad1.dpad_down;

            //Stapgrootte voor servo up/down
            double updown_step = 0.05;
            //startpositie turret: pas hem vooral aan want de turret bestond op moment van programmeren nog niet :)
            double turretupdownpos = 0.5;

            //&& betekent AND, ! is een NOT, dus if = true als turret_up = true AND lastup = false (zodat 1 keer indrukker 1 keer omhoog/laag betekent
            if (turret_up && !lastup) {
                turretupdownpos += updown_step;
            }

            if (turret_down && !lastdown) {
                turretupdownpos -= updown_step;
            }

            turretupdownpos = Math.max(0, Math.min(1, turretupdownpos));

            lastup = turret_up;
            lastdown = turret_down;

//            turretupdownservo.setPosition(turretupdownpos);


            //Motorsnelheden toewijzen
            frontLeft.setPower(fLPwr);
            frontRight.setPower(fRPwr);
            rearLeft.setPower(rLPwr);
            rearRight.setPower(rRPwr);
            launchmotor3L.setPower(launchpower3L);
            launchmotor2R.setPower(launchpower2R);
            intake.setPower(intakepower);

        }
    }
}