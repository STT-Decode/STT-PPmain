package org.firstinspires.ftc.teamcode.RobotParts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OutreachBotsDrivetrain {
    private DcMotorEx lf;
    private DcMotorEx rf;

    public void init(HardwareMap map) {
        lf = map.get(DcMotorEx.class, "left");
        rf = map.get(DcMotorEx.class, "right");

        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void drive1(double forward, double rotate) {
        lf.setPower(forward + rotate);
        rf.setPower(-forward + rotate);
    }
}