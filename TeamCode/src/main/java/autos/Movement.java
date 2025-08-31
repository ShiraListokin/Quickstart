package autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

/**
 * This is an example auto that showcases movement and control of two servos autonomously.
 * It is a 0+4 (Specimen + Sample) bucket auto. It scores a neutral preload and then pickups 3 samples from the ground and scores them before parking.
 * There are examples of different ways to build paths.
 * A path progression method has been created and can advance based on time, position, or other factors.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 11/28/2024
 */

@Autonomous(name = "Test!!!")
public class Movement extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    //start
    private final Pose startPose = new Pose(11, 62, 0);

    /** Scoring Pose of our robot. It is facing the submersible at a -45 degree (315 degree) angle. */
    private final Pose beginPose = new Pose(30, 45, 0);

    private final Pose upPose1 = new Pose(70, 45, 0);

    private final Pose upPose2 = new Pose(70, 35, 0);
    Point[] points = {new Point(startPose), new Point(beginPose), new Point(upPose1), new Point(upPose2)};

    private PathChain p1;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    public void buildPaths() {

        p1 = follower.pathBuilder()
                .addPath(new BezierCurve(points))
                .build();
        /*
        p2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(beginPose), new Point(upPose1)))
                .setLinearHeadingInterpolation(beginPose.getHeading(), upPose1.getHeading())
                .build();
        p3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(upPose1), new Point(upPose2)))
                .setLinearHeadingInterpolation(upPose1.getHeading(), upPose2.getHeading())
                .build();

         */

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(p1);
                setPathState(1);
                break;
            case 1:
                telemetry.addData("code is running", "case1");
                if(!follower.isBusy()) {
                    telemetry.addData("code is running", "postcase1");
                }

                /*
            case 1:
                if(!follower.isBusy()) {
                    follower.followPath(p2,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(p3,true);
                    setPathState(-1);
                }
                break;

                 */
        }
        telemetry.update();
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {

        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {
    }
}

