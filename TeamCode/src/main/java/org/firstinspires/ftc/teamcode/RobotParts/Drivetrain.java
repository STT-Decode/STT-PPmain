package org.firstinspires.ftc.teamcode.RobotParts;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivetrain
{
    private DcMotorEx lf;
    private DcMotorEx rf;
    private DcMotorEx lb;
    private DcMotorEx rb;
    private DcMotorEx flywheel1;
    private DcMotorEx flywheel2;
    private DcMotorEx intake;
    private DcMotorEx overtake;

    double additionalPower1=0;
    double additionalPower2=0;

    public void init(HardwareMap map)
    {
        lf = map.get(DcMotorEx.class, "left_front");
        rf = map.get(DcMotorEx.class, "right_front");
        lb = map.get(DcMotorEx.class, "left_back");
        rb = map.get(DcMotorEx.class, "right_back");
        flywheel1 = map.get(DcMotorEx.class, "flywheel1");
        flywheel2 = map.get(DcMotorEx.class, "flywheel2");
        intake = map.get(DcMotorEx.class, "intake");
        overtake = map.get(DcMotorEx.class, "overtake");


        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        overtake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive0(double forward, double right, double rotate, double power)
    {
        double leftFrontPower = (-forward - right + rotate) * power;
        double rightFrontPower = (-forward + right - rotate) * power;
        double rightRearPower = (-forward - right - rotate) * power;
        double leftRearPower = (-forward + right + rotate) * power;

        lf.setPower(-leftFrontPower);
        rf.setPower(-rightFrontPower);
        rb.setPower(-rightRearPower);
        lb.setPower(leftRearPower);
    }

    public void flywheels(double velocity,double f1v,double f2v)
    {
        if(velocity==0){
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }
        else{
            if(velocity<f1v-50){
                additionalPower1 -=0.01;
            } else if (velocity>=f1v+50&&additionalPower1 < 0.4) {
                additionalPower1 +=0.01;
            }
            if(velocity<f2v-50 ){
                additionalPower2-=0.01;
            } else if (velocity>=f2v-50 && additionalPower2 < 0.4) {
                additionalPower2+=0.01;
            }
            flywheel1.setPower(0.6+additionalPower1);
            flywheel2.setPower(-(0.6+additionalPower2));
        }
    }
    public  void backkupflywheels(double power){
        flywheel2.setPower(-power);
        flywheel1.setPower(-power);
    }
    public double getAdditionalPower1(){
        return additionalPower1;
    }
    public double getAdditionalPower2(){
        return additionalPower2;
    }
    public void intake(double power)
    {
        intake.setPower(power);
    }
    public void overtake(double power){overtake.setPower(power);}


    public double getvelocity1(){
        return flywheel1.getVelocity();
    }
    public double getvelocity2(){
        return flywheel2.getVelocity();
    }




}