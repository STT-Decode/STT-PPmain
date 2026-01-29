package org.firstinspires.ftc.teamcode.Opmodes;

import android.util.Size;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Overtake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "test")
public class rootOpMode extends NextFTCOpMode
{
    public rootOpMode()
    {
        addComponents(
            new SubsystemComponent(Flywheel.INSTANCE, Feeder.INSTANCE, Intake.INSTANCE, Overtake.INSTANCE, Drivetrain.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    public static final MotorEx frontLeftMotor = new MotorEx("left_front").brakeMode();
    public static final MotorEx frontRightMotor = new MotorEx("right_front").reversed().brakeMode();
    public static final MotorEx backLeftMotor = new MotorEx("left_back").reversed().brakeMode();
    public static final MotorEx backRightMotor = new MotorEx("right_back").reversed().brakeMode();

    protected ElapsedTime runtime = new ElapsedTime();

    public static IMUEx imu;

    // Create the AprilTag processor the easy way.
    public static AprilTagProcessor aprilTag;


    // Create the vision portal the easy way.

    public Size camSize = new Size(640, 480);

    public VisionPortal visionPortal;
}