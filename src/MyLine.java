import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MyLine extends Line2D.Double {

    public Point2D.Double p1;
    public Point2D.Double p2;
    private Color color;

    public MyLine(Point2D p1, Point2D p2) {
        super(p1, p2);
    }

    public void setColor(Color mc) {
        color = mc;
    }

    public Color etColor() {
        return color;
    }
}
