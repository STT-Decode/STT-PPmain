package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Opmodes.rootOpMode.*;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import kotlin.text.CharDirectionality;

public class Drivetrain implements Subsystem
{
    public static final Drivetrain INSTANCE = new Drivetrain();
    private Drivetrain() {
    }

    public Command drive(double goalDistance, double power)
    {
        return new Command()
        {
            private SparkFunOTOS.Pose2D startPos;
            private SparkFunOTOS.Pose2D currentPos;
            private double distanceMoved;

            @Override
            public void start()
            {
                startPos = new SparkFunOTOS.Pose2D(0, backLeftMotor.getCurrentPosition(), 0);
                frontLeftMotor.setPower(power);
                backLeftMotor.setPower(power);
                frontRightMotor.setPower(power);
                backRightMotor.setPower(power);
            }

            @Override
            public boolean isDone()
            {
                return (Math.abs(distanceMoved) < Math.abs(goalDistance));
            }

            @Override
            public void update()
            {
                currentPos = new SparkFunOTOS.Pose2D(0, backRightMotor.getCurrentPosition(), 0);
                distanceMoved = currentPos.y - startPos.y;
            }

            @Override
            public void stop(boolean interrupted)
            {
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }

        };
    }

    public Command turn(double goalDistance, double power)
    {
        return new Command()
        {
            private SparkFunOTOS.Pose2D startPos;
            private SparkFunOTOS.Pose2D currentPos;
            private double distanceMoved;

            @Override
            public void start()
            {
                startPos = new SparkFunOTOS.Pose2D(0, 0, imu.get().inDeg);
                frontLeftMotor.setPower(power);
                backLeftMotor.setPower(power);
                frontRightMotor.setPower(-power);
                backRightMotor.setPower(-power);
            }

            @Override
            public boolean isDone()
            {
                return (Math.abs(distanceMoved) < Math.abs(goalDistance));
            }

            @Override
            public void update()
            {
                currentPos = new SparkFunOTOS.Pose2D(0, 0, imu.get().inDeg);
                distanceMoved = currentPos.h - startPos.h;
            }

            @Override
            public void stop(boolean interrupted)
            {
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }

        };
    }

}
