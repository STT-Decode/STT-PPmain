package org.firstinspires.ftc.teamcode.RobotParts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;


public class All_Parts
{
    private DcMotorEx lf;
    private DcMotorEx rf;
    private DcMotorEx lb;
    private DcMotorEx rb;
    private DcMotorEx flyWheel;
    private CRServo servo;


    public void init(HardwareMap map)
    {

        lf = map.get(DcMotorEx.class, "left_front");
        rf = map.get(DcMotorEx.class, "right_front");
        lb = map.get(DcMotorEx.class, "left_back");
        rb = map.get(DcMotorEx.class, "right_back");
        flyWheel = map.get(DcMotorEx.class, "flywheel");
        servo = map.get(CRServo.class, "servo");

        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }



    public void drive0(double forward, double right, double rotate, double power)
    {
        double leftFrontPower = (-forward - right + rotate) * power;
        double rightFrontPower = (-forward + right - rotate) * power;
        double rightRearPower = (-forward - right - rotate) * power;
        double leftRearPower = (-forward + right + rotate) * power;

        lf.setPower(leftFrontPower);
        rf.setPower(-rightFrontPower);
        rb.setPower(-rightRearPower);
        lb.setPower(leftRearPower);
    }

    public void setLfPower(double power)
    {
        lf.setPower(power);
    }

    public void setFlyWheelPower(double power){
        flyWheel.setPower(power);
    }

    public void setServoPower(double power){
        servo.setPower(power);
    }

}