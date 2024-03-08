import javax.swing.*;
import java.awt.*;

public class ScrollToVisible {
    static JPanel panel1;
    static JPanel panel2;

    static JPanel panel(JPanel panel) {
        panel = new JPanel();
        panel.add(new JLabel("Label 1"){
            {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return panel;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setLayout(new FlowLayout());
        Thread thread = new Thread(new panel1(panel1));
        Thread thread1 = new Thread(new panel2(panel2));
        thread.start();
        thread1.start();
        while (panel1 == null || panel2 == null) {
            if (!thread.isAlive() && !thread1.isAlive()) {
                break;
            }
        }
        frame.add(panel1);
        frame.add(panel2);
        System.out.println("Đã xong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class panel1 implements Runnable{
    JPanel panel;
    public panel1(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        panel = new JPanel();
        panel.add(new JLabel("Label 1"){
            {
                try {
                    Thread.sleep(5000);
                    panel.setName("panel1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Xong 1");
    }
}

class panel2 implements Runnable{
    JPanel panel;
    public panel2(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        panel = new JPanel();
        panel.add(new JLabel("Label 2"){
            {
                try {
                    Thread.sleep(1000);
                    panel.setName("panel2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Xong 2");
    }
}