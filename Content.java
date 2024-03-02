import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import QuanLiThuvien.brain.Csdl;
import QuanLiThuvien.brain.SendMail;

public class Content implements Runnable{
    public static void main(String[] args) {
        Content content = new Content();
        content.read();
    }

    public void read() {

        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton btn1 = new JButton();
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long start = System.currentTimeMillis();
                luong1 luong1 = new luong1(btn1);
                luong2 luong2 = new luong2();
                luong2.start();
                luong1.start();
                long finish = System.currentTimeMillis();
                System.out.println("Time: " + (finish - start));
            }
        });
        btn1.setCursor(new Cursor(12));
        btn1.setText("Click 1");
        JButton btn2 = new JButton();
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long start = System.currentTimeMillis();
                luong1 luong1 = new luong1(btn2);
                luong2 luong2 = new luong2();
                luong2.start();
                luong1.start();
                long finish = System.currentTimeMillis();
                System.out.println("Time: " + (finish - start));
            }
        });

        btn2.setCursor(new Cursor(12));
        btn2.setText("Click 2");
        btn2.setFocusPainted(false);

        frame.add(btn1);
        frame.add(btn2);
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
    private JButton button;
    private Boolean status;
    luong1(JButton btn) {
        this.button = btn;
    }

    public void run() {
        button.setEnabled(false);
        for( int i = 13; i > 0; i--) {
            button.setText("Khả dụng sau: " + i + "s");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                
            }
        }
        button.setText("Click");
        button.setEnabled(true);
    }
}


class luong2 extends Thread {
    public void run() {
        SendMail.sendMail("tkhai12386@gmail.com");
    }
}
