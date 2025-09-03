package test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import subsystems.VerticalSlides;


@TeleOp
public class PIDTest extends OpMode {
    private VerticalSlides slides;
    @Override
    public void init() {
        slides = new VerticalSlides(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        slides.update();
    }
}