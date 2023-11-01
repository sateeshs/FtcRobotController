package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

/**
 * We want to look only at a small part, specifically rectangular.
 * That means we want to create a subset of the image to look at.
 * We also can draw this on the camera image.
 */

public class SamplePipeline extends OpenCvPipeline {

    Mat YCrCb = new Mat();
    Mat Y = new Mat();
    Mat RectA_Y = new Mat();
    int avg;
    int avgA;
    static final int STREAM_WIDTH = 1920; // modify for your camera
    static final int STREAM_HEIGHT = 1080; // modify for your camera


    static final int WidthRectA = 130;
    static final int HeightRectA = 110;


    static final Point RectATopLeftAnchor = new Point((STREAM_WIDTH - WidthRectA) / 2 + 300, ((STREAM_HEIGHT - HeightRectA) / 2) - 100);
    Point RectATLCorner = new Point(
            RectATopLeftAnchor.x,
            RectATopLeftAnchor.y);
    Point RectABRCorner = new Point(
            RectATopLeftAnchor.x + WidthRectA,
            RectATopLeftAnchor.y + HeightRectA);




    /*
     * This function takes the RGB frame, converts to YCrCb,
     * and extracts the Y channel to the 'Y' variable
     */
    void inputToY(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        ArrayList<Mat> yCrCbChannels = new ArrayList<Mat>(3);
        Core.split(YCrCb, yCrCbChannels);
        Y = yCrCbChannels.get(0);

    }

    @Override
    public void init(Mat firstFrame) {
        inputToY(firstFrame);
        RectA_Y = Y.submat(new Rect(RectATLCorner, RectABRCorner));
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToY(input);
        System.out.println("processing requested");
        avg = (int) Core.mean(Y).val[0];
        avgA = (int) Core.mean(RectA_Y).val[0];
        YCrCb.release(); // don't leak memory!
        Y.release(); // don't leak memory!


        Imgproc.rectangle( // rings
                input, // Buffer to draw on
                RectATLCorner, // First point which defines the rectangle
                RectABRCorner, // Second point which defines the rectangle
                new Scalar(0,0,255), // The color the rectangle is drawn in
                2); // Thickness of the rectangle lines

        return input;
    }

    public int getAnalysis() {
        return avg;
    }
    public int getRectA_Analysis() {
        return avgA;
    }

}

