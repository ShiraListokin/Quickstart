package test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import subsystems.VerticalSlides;


@TeleOp
public class Shooter extends OpMode {

    DcMotorEx shooter;
    public void init() {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }

    @Override
    public void loop() {
        shooter.setPower(gamepad1.right_trigger);
    }
}