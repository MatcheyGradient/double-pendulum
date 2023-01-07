import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pendulum {

    public double angle1;
    public double angle2;

    public double angleVel1 = 0;
    public double angleVel2 = 0;

    public boolean trace;
    public List<Line> tracer = new ArrayList<>();
    public int storedX1, storedY1;

    public double mass1 = 1;
    public double mass2 = 1;
    public double gravity = .002;
    public double bar1length = 125;
    public double bar2length = 125;

    public int n = 0;

    public Pendulum(double angle1, double angle2, boolean trace){
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.trace = trace;
    }

    public void draw(Graphics g){
        g.fillOval(295, 295, 10, 10);
        g.drawLine(300, 300, (int) (300 + Math.sin(angle1) * bar1length), (int) (300 + Math.cos(angle1) * bar1length));
        g.fillOval((int) (300 + Math.sin(angle1) * bar1length) - 5, (int) (300 + Math.cos(angle1) * bar1length) - 5, 10, 10);
        g.drawLine((int) (300 + Math.sin(angle1) * bar1length), (int) (300 + Math.cos(angle1) * bar1length), (int) (300 + Math.sin(angle1) * bar1length + Math.sin(angle2) * bar2length), (int) (300 + Math.cos(angle1) * bar1length + Math.cos(angle2) * bar2length));
        g.fillOval((int) (300 + Math.sin(angle1) * bar1length + Math.sin(angle2) * bar2length) - 5, (int) (300 + Math.cos(angle1) * bar1length + Math.cos(angle2) * bar2length) - 5, 10, 10);

        updateAngles();
        if(trace){
            n++;
            if(n % 20 == 0){ // the modulo helps reduce lag in making the likes a bit longer. the smaller the number, the more detailed the line is.
                addLine();
            }
            renderLines(g);
        }
    }

    public void updateAngles(){
        double term1 = -gravity * (2 * mass1 + mass2) * Math.sin(angle1) + -mass2 * gravity * Math.sin(angle1 - 2 * angle2);
        double term2 = -2 * Math.sin(angle1 - angle2) * mass2 * (angleVel2 * angleVel2 * bar2length + angleVel1 * angleVel1 * bar1length * Math.cos(angle1 - angle2));
        double term3 = bar1length * (2 * mass1 + mass2 - mass2 * Math.cos(2 * angle1 - 2 * angle2));
        double angle1_a = (term1  + term2) / term3;

        term2 = angleVel2 * angleVel2 * bar2length * mass2 * Math.cos(angle1 - angle2);
        term1 = 2 * Math.sin(angle1 - angle2) * (angleVel1 * angleVel1 * bar1length * (mass1 + mass2) + gravity * (mass1 + mass2) * Math.cos(angle1) + term2);
        term3 = bar2length * (2 * mass1 + mass2 - mass2 * Math.cos(2 * angle1 - 2 * angle2));
        double angle2_a = term1 / term3;

        angleVel1 += angle1_a;
        angleVel2 += angle2_a;

        angle1 += angleVel1;
        angle2 += angleVel2;
    }

    // Everything below this point is used for drawing the tracers if you decide to

    public void init(){
        double mass2x = 300 + Math.sin(angle1) * bar1length + Math.sin(angle2) * bar2length;
        double mass2y = 300 + Math.cos(angle1) * bar1length + Math.cos(angle2) * bar2length;

        storedX1 = (int) mass2x;
        storedY1 = (int) mass2y;
    }

    public void addLine(){
        double mass2x = 300 + Math.sin(angle1) * bar1length + Math.sin(angle2) * bar2length;
        double mass2y = 300 + Math.cos(angle1) * bar1length + Math.cos(angle2) * bar2length;
        tracer.add(new Line(storedX1, storedY1, (int) mass2x, (int) mass2y));
        storedX1 = (int) mass2x;
        storedY1 = (int) mass2y;
    }

    public void renderLines(Graphics g){
        tracer.forEach(line -> line.render(g));

        if(tracer.size() > 30) tracer.remove(0); // deletes the previous lines. the bigger the number, the longer the line lasts
    }
}
