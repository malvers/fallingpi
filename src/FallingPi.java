import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FallingPi extends JButton implements KeyListener {

    ArrayList<MyLine> allSticks = new ArrayList(100000);
    private static int areaHeight = 800;
    private static int areaWidth = 1200;
    private int numLines = 8;
    private int width = areaWidth / numLines;
    ArrayList<Double> lines = new ArrayList<>();
    private int numCrossings = 0;
    private JFrame frame;
    private int numTrials = 0;
    private long increase = (long) Math.pow(10, 0);

    FallingPi(JFrame f) {

        frame = f;
        addKeyListener(this);

        double pos = 0.0d;
        lines.add(pos);
        for (int i = 0; i < numLines; i++) {
            pos += width;
            lines.add(pos);
        }
        init();
    }

    private void init() {

        String sStick = " stick";
        if (increase > 1) {
            sStick = " sticks";
        }
        frame.setTitle("Hit the space bar to start!      increase per step:   " + increase + sStick);

        allSticks = new ArrayList(100000);

        numTrials = 0;
        numCrossings = 0;
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.RED.darker());
        for (int i = 0; i < lines.size(); i++) {
            Double pos = lines.get(i);
            g2d.draw(new Line2D.Double(pos, 0.0, pos, getHeight()));
        }

        g2d.setColor(Color.GREEN);

        for (int i = 0; i < allSticks.size(); i++) {
            MyLine stick = allSticks.get(i);
            g2d.setColor(stick.etColor());
            g2d.draw(stick);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            for (int i = 0; i < increase; i++) {
                generateStick();

                if ((i + 1) % Math.pow(10, 7) == 0 || i == 0) {
                    setTitle();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            init();
        } else if (e.getKeyCode() == KeyEvent.VK_0) {
            increase = (long) Math.pow(10, 0);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            increase = (long) Math.pow(10, 1);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            increase = (long) Math.pow(10, 2);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            increase = (long) Math.pow(10, 3);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            increase = (long) Math.pow(10, 4);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_5) {
            increase = (long) Math.pow(10, 5);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            increase = (long) Math.pow(10, 6);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            increase = (long) Math.pow(10, 7);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_7) {
            increase = (long) Math.pow(10, 8);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_8) {
            increase = (long) Math.pow(10, 9);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_8) {
            increase = (long) Math.pow(10, 10);
            setTitle();
        } else if (e.getKeyCode() == KeyEvent.VK_9) {
            increase = (long) Math.pow(10, 11);
            setTitle();
        }
        repaint();
    }

    private void setTitle() {
        double myPi = (2 * width * (double) numTrials) / ((double) width * (double) numCrossings);
        double ratio = (double) numTrials / (double) numCrossings;
        frame.setTitle("     trials: " + String.format("%,d", numTrials) +
                "     crossings: " + String.format("%,d", numCrossings) +
                "     ratio (trails / crossings): " + d2S(ratio, 6, 4) +
                "     our PI: " + d2S(myPi, 6, 4) +
                "     (increase per step: " + String.format("%,d", increase) + ")");
    }

    private void generateStick() {

        double xPos = (width / 2.0) + Math.random() * (getWidth() - width);
        double yPos = (width / 2.0) + Math.random() * (getHeight() - width);
        Point2D.Double pos = new Point2D.Double(xPos, yPos);

        double angle = Math.random() * Math.PI;

        double x1 = Math.sin(angle) * width / 2.0;
        double y1 = Math.cos(angle) * width / 2.0;

        Point2D p1 = new Point2D.Double(xPos + x1, yPos + y1);
        Point2D p2 = new Point2D.Double(xPos - x1, yPos - y1);

        MyLine stick = new MyLine(p1, p2);

        checkIfStickCrosses(stick);
        numTrials++;
        if (numTrials < 100000) {
            Color mc = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            stick.setColor(mc);
            allSticks.add(stick);
        }
    }

    private void checkIfStickCrosses(Line2D.Double stick) {

        for (int i = 0; i < lines.size(); i++) {

            Double pos = lines.get(i);

            if (stick.x2 > stick.x1) {
                if (stick.x1 < pos && stick.x2 > pos) {
                    numCrossings++;
                    break;
                }
            } else {
                if (stick.x2 < pos && stick.x1 > pos) {
                    numCrossings++;
                    break;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public String d2S(double x, int w, int d) {

        DecimalFormat fmt = new DecimalFormat();
        fmt.setMaximumFractionDigits(d);
        fmt.setMinimumFractionDigits(d);
        fmt.setGroupingUsed(false);
        String s = fmt.format(x);
        while (s.length() < w) {
            s = " " + s;
        }
        return s;
    }

    public static void main(String[] args) {

        JFrame f = new JFrame();
        f.setBounds(300, 100, areaWidth, areaHeight);
        f.add(new FallingPi(f));

        f.setVisible(true);
    }
}
