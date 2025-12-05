package org.firstinspires.ftc.teamcode.RobotParts;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

;

public class All_Parts
{
    private DcMotorEx lf;
    private DcMotorEx rf;
    private DcMotorEx lb;
    private DcMotorEx rb;
    private DcMotorEx flywheel1;
    private DcMotorEx flywheel2;
    private CRServo overtake_1;
    private Servo overtake_2;
    private Servo overtake_3;

    public void init(HardwareMap map)
    {
        overtake_2 = map.get(Servo.class, "feeder");
        /*overtake_2 = map.get(Servo.class, "overtake_2");
        overtake_3 = map.get(Servo.class, "overtake_3");*/
        lf = map.get(DcMotorEx.class, "left_front");
        rf = map.get(DcMotorEx.class, "right_front");
        lb = map.get(DcMotorEx.class, "left_back");
        rb = map.get(DcMotorEx.class, "right_back");
        flywheel1 = map.get(DcMotorEx.class, "flywheel1");
        flywheel2 = map.get(DcMotorEx.class, "flywheel2");

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
    public double getflyvel(){
        return flywheel1.getVelocity();
    }
    public void setLfPower(double power)
    {
        flywheel1.setPower(power);
        flywheel2.setPower(-power);
    }
    public void ninjitsu(double power){flywheel1.setPower(power);
        flywheel2.setPower(power);}
    public void setServo1pos(double power){
        overtake_1.setPower(power);
    }
    public void setServo2pos(double pos){
        overtake_2.setPosition(pos);
    }

    public void setServo3pos(double pos){
        overtake_3.setPosition(pos);
    }


}