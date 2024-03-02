import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Content implements Runnable{
    public static void main(String[] args) {
        // Content content = new Content();
        // content.read();

        luong1 luong1 = new luong1();
        luong2 luong2 = new luong2();

        luong1.start();
        luong2.start();
        
    }

    public void read() {
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel();
        label.setText("wait");
        JButton btn1 = new JButton();
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        btn1.setCursor(new Cursor(12));
        btn1.setText("Click");
        btn1.setFocusPainted(false);
        frame.add(label);
        frame.add(btn1);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    @Override
    public void run() {
        System.out.println("OK");
    }
}

class luong1 extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}


class luong2 extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}
