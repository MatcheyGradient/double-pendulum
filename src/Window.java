import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class Window extends JPanel implements ActionListener {

    public static List<Pendulum> pendulumList = new ArrayList<>();

    Timer timer;
    public static final int boardSizeX = 600;
    public static final int boardSizeY = 600;

    public Window(){
        this.setPreferredSize(new Dimension(boardSizeX, boardSizeY));
        this.setBackground(new Color(0, 0, 0, 255));
        this.setFocusable(true);
        timer = new Timer(0, this);
        start();
    }

    public void start() {
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(white);

        pendulumList.forEach(p -> p.draw(g));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}