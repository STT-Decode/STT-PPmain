package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.Tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Opmodes.rootOpMode;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "OTOStest")
public class OTOStest extends NextFTCOpMode
{
    public SparkFunOTOS OTOS;

    @Override
    public void onStartButtonPressed()
    {
       OTOS = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
       SparkFunOTOS.Pose2D x = OTOS.getPosition();
    }

}