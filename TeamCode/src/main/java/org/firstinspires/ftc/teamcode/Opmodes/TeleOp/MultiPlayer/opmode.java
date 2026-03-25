package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MultiPlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotParts.All_Parts;
import org.firstinspires.ftc.teamcode.RobotParts.Drivetrain;
import org.firstinspires.ftc.teamcode.Opmodes.TeleOp.Tests.Datalogger;

//the name is how this Opmode will show up on the driver-hub
@TeleOp(name = "PIDflywheelTuning", group = "TeleOp")
public class opmode extends LinearOpMode {
    Datalog datalog;
    private ElapsedTime runtime = new ElapsedTime();
    Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain.init(hardwareMap);
        // Initialize the datalog
        datalog = new Datalog("datalog_01");
        double error = 0;
        double derror = 0;
        double errordx = 0;
        double power = 0;
        double Kd = 0.001;
        double Kp = 0.001;
        double Ki = 0.001;
        double velocitygoal = 2700;
        double velocity = 0;
        int selectedK = 0;
        int loops=0;
        boolean toggleUp = false;
        datalog.opModeStatus.set("RUNNING");
        runtime.reset();
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            loops++;
            if (gamepad1.right_trigger > 0.3) {
                velocity = drivetrain.getvelocity1();
                derror = (velocitygoal - velocity - error) / runtime.seconds();
                error = velocitygoal - velocity;
                errordx += error * runtime.seconds();
                power = (derror * Kd) + (error * Kp) + (errordx * Ki);
                drivetrain.flywheels(power);
            } else {
                drivetrain.flywheels(0);
            }
            runtime.reset();
            if (gamepad1.dpad_left) {
                error = 0;
                derror = 0;
                errordx = 0;
            }
            if (gamepad1.dpad_up) {
                if (!toggleUp) {
                    selectedK = (selectedK + 1) % 3;
                }
                toggleUp = true;
            } else {
                toggleUp = false;
            }
            if (gamepad1.x) {
                if (selectedK == 0) {
                    Kp += 0.00001;
                } else if (selectedK == 1) {
                    Ki += 0.00001;
                } else if (selectedK == 2) {
                    Kd += 0.00001;
                }
            }
            if (gamepad1.b) {
                if (selectedK == 0) {
                    Kp -= 0.00001;
                } else if (selectedK == 1) {
                    Ki -= 0.00001;
                } else if (selectedK == 2) {
                    Kd -= 0.00001;
                }
            }
            datalog.loopCounter.set(loops);
            datalog.velocity.set(velocity);
            datalog.targetvelocity.set(velocitygoal);
            datalog.Kp.set(Kp);
            datalog.Ki.set(Ki);
            datalog.Kd.set(Kd);
            datalog.writeLine();
            telemetry.addData("selK", selectedK);
            telemetry.addData("Goalvelocity", velocitygoal);
            telemetry.addData("velocity", velocity);
            telemetry.addData("power", power);
            telemetry.addData("P", error * Kp);
            telemetry.addData("I", errordx * Ki);
            telemetry.addData("D", derror * Kd);
            telemetry.addData("KP", Kp);
            telemetry.addData("KI", Ki);
            telemetry.addData("KD", Kd);
            telemetry.addData("error", error);
            telemetry.addData("errordx", errordx);
            telemetry.addData("derror", derror);
            telemetry.update();


        }

    }

    /*
     * The datalog is automatically closed and flushed to disk after
     * the OpMode ends - no need to do that manually :')
     */


    /*
     * This class encapsulates all the fields that will go into the datalog.
     */
    public static class Datalog {
        // The underlying datalogger object - it cares only about an array of loggable fields
        private final Datalogger datalogger;

        // These are all of the fields that we want in the datalog.
        // Note that order here is NOT important. The order is important in the setFields() call below
        public Datalogger.GenericField opModeStatus = new Datalogger.GenericField("OpModeStatus");
        public Datalogger.GenericField loopCounter = new Datalogger.GenericField("Loop Counter");

        public Datalogger.GenericField velocity = new Datalogger.GenericField("Velocity");
        public Datalogger.GenericField targetvelocity = new Datalogger.GenericField("Velocity goal");
        public Datalogger.GenericField Kp = new Datalogger.GenericField("Kp");
        public Datalogger.GenericField Ki = new Datalogger.GenericField("Ki");
        public Datalogger.GenericField Kd = new Datalogger.GenericField("Kd");

        public Datalog(String name) {
            // Build the underlying datalog object
            datalogger = new Datalogger.Builder()

                    // Pass through the filename
                    .setFilename(name)

                    // Request an automatic timestamp field
                    .setAutoTimestamp(Datalogger.AutoTimestamp.DECIMAL_SECONDS)

                    // Tell it about the fields we care to log.
                    // Note that order *IS* important here! The order in which we list
                    // the fields is the order in which they will appear in the log.
                    .setFields(
                            opModeStatus,
                            loopCounter,
                            velocity,
                            targetvelocity,
                            Kp,
                            Ki,
                            Kd

                    )
                    .build();
        }

        // Tell the datalogger to gather the values of the fields
        // and write a new line in the log.
        public void writeLine() {
            datalogger.writeLine();
        }
    }
}