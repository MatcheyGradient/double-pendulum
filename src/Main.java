import javax.swing.*;

public class Main extends JFrame {

    public Main(){
        this.add(new Window());
        this.setTitle("pendulum");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){

        Window.pendulumList.add(new Pendulum(Math.PI, Math.PI - .5, true));
        Window.pendulumList.forEach(Pendulum::init); // initialize tracer

        new Main();
    }

}
