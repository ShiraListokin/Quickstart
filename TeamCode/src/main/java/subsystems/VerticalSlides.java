package subsystems;

import com.pedropathing.util.CustomPIDFCoefficients;
import com.pedropathing.util.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class VerticalSlides extends Subsystem{

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private PIDFController PIDB, PIDS;
    private CustomPIDFCoefficients PIDCoB, PIDCoS;
    private DcMotorEx leftSlide, rightSlide;
    private double idealPos;
    private double currentPos;
    private double power;
    private double PIDPower;

    public VerticalSlides(HardwareMap h, Telemetry t){
        hardwareMap = h;
        telemetry = t;

        PIDCoB = new CustomPIDFCoefficients(0.01, 0, 0, 0.2);
        PIDB = new PIDFController(PIDCoB);

        PIDCoS = new CustomPIDFCoefficients(0.03, 0, 0, 0.2);
        PIDS = new PIDFController(PIDCoS);

        leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide"); //already correct direction
        rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide"); //already correct direction

        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void update(){
        //Update positions

        currentPos = leftSlide.getCurrentPosition();

        PIDB.setTargetPosition(idealPos);
        PIDB.updatePosition(currentPos);
        PIDS.setTargetPosition(idealPos);
        PIDS.updatePosition(currentPos);

        //Update PIDS
        if(Math.abs(idealPos-currentPos) > 30){
            PIDPower = PIDB.runPIDF();
            telemetry.addData("Type","BIG");
        }
        else{
            PIDPower = PIDS.runPIDF();
            telemetry.addData("Type","SMALL");
        }

        if(PIDPower > 1){
            PIDPower = 1;
        }
        else if(PIDPower < -1){
            PIDPower = -1;
        }

        //update Power
        setPower(PIDPower);

        //telemetry
        telemetry.addData("PIDPower", PIDPower);
        telemetry.addData("power", power);

        telemetry.addData("currentPos", currentPos);
        telemetry.addData("idealPos", idealPos);

        telemetry.addData("rigthCurrent", rightSlide.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("leftCurrent", leftSlide.getCurrent(CurrentUnit.AMPS));
    }

    //Direction testing
    public void directionTestLeft(){
        leftSlide.setPower(0.1);
        rightSlide.setPower(0);
    }
    public void directionTestRigth(){
        rightSlide.setPower(0.1);
        leftSlide.setPower(0);
    }

    //Acessors and mutators.
    private void setPower(double p){
        power = p;
        leftSlide.setPower(power);
        rightSlide.setPower(power);
    }

    public double getPower(){
        return power;
    }

    public void setIdealPos(double pos){
        idealPos = pos;
    }

    public double getIdealPos(){
        return idealPos;
    }

    public double getCurrentPos(){
        return currentPos;
    }
}
