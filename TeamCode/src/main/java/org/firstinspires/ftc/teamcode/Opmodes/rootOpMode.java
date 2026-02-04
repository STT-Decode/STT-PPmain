package org.firstinspires.ftc.teamcode.Opmodes;

import android.util.Size;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.AlignWithAprilTag;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.imuTest;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

public class rootOpMode extends NextFTCOpMode
{
    public rootOpMode()
    {
        addComponents(
            new SubsystemComponent(Flywheel.INSTANCE, Feeder.INSTANCE, Intake.INSTANCE, Overtake.INSTANCE, Drivetrain.INSTANCE, imuTest.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    public static final MotorEx frontLeftMotor = new MotorEx("left_front").reversed().brakeMode();
    public static final MotorEx frontRightMotor = new MotorEx("right_front").reversed().brakeMode();
    public static final MotorEx backLeftMotor = new MotorEx("left_back").reversed().brakeMode();
    public static final MotorEx backRightMotor = new MotorEx("right_back").reversed().brakeMode();

    public static DcMotorEx flywheel1;
    public static DcMotorEx flywheel2;

    protected ElapsedTime runtime = new ElapsedTime();

    public static IMUEx imu;

    // Create the AprilTag processor the easy way.
    public static AprilTagProcessor aprilTag;

    public static SparkFunOTOS OTOS;


    // Create the vision portal the easy way.

    public Size camSize = new Size(640, 480);

    public VisionPortal visionPortal;

    public Command updateTelemetry(){
        return new Command() {
            @Override
            public boolean isDone() {
                ActiveOpMode.updateTelemetry(ActiveOpMode.telemetry());
                return true;
            }
        };
    }

    public Command setFarScoringZone(boolean far)
    {
        return new SequentialGroup(AlignWithAprilTag.setOffset(far ? 65 : 0), Flywheel.INSTANCE.setFarZone(far));
    }
}