package autos.pathsAndConstants;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;

public class Positions {
    static public final Pose startPose = new Pose(11, 62, 0);

    static public final Pose beginPose = new Pose(30, 45, 0);

    static public final Pose upPose1 = new Pose(70, 45, 0);

    static public final Pose upPose2 = new Pose(70, 35, 0);

    static public Point[] points = {new Point(startPose), new Point(beginPose), new Point(upPose1), new Point(upPose2)};
}
