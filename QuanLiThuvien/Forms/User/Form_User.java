package QuanLiThuvien.Forms.User;


import java.awt.*;
import java.util.*;
import java.util.Queue;

import javax.swing.*;

import QuanLiThuvien.brain.SetIcon;
import QuanLiThuvien.brain.TimeUpdateData;

public class Form_User {
    public static int ID;
    static JPanel panelToolbar;
    static JPanel shopCart;
    static JPanel panelMenu;
    static JPanel panelProduct;
    static JPanel panelUser;
    static JFrame frame;
    public static Boolean checkLogin = false;

    protected static Queue<Thread> queue = new LinkedList<>();

    public Form_User() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Error LookAndFeel");
        }
        frame = new JFrame();
        frame.setTitle("BOOK SHOP OF BEDROOD");
        frame.setSize(1000, 600);
        frame.setName("frame");
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setLayout(new BorderLayout());
        // Set icon cho application
        SetIcon.setIcon(frame, "Icon/Shoppingcart.png", 40, 40);
        frame.add(new JLabel("<html> <p style='color:red;'>Admin chào bạn!</p>Hôm nay là ngày: " +TimeUpdateData.timeUpdateData(1)+ " </html>", JLabel.CENTER){
            {
                setForeground(new Color(210, 0, 98));
                setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
            }
        }, BorderLayout.CENTER);
        frame.validate();
        frame.repaint();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Set thanh công cụ
        long bd = System.currentTimeMillis();
        panelToolbar = PanelToolBar.panelToolBar();
        // Set menu
        panelMenu = PanelMenu.panelMenu();
        // Set màn hình chính
        panelProduct = PanelContent.panelCenter();
        // UI of shopCart
        shopCart = ShopCart.shopCart();
        // UI of PanelUser
        if (checkLogin) {
           panelUser = PanelUser.panelUser();
        }
        long kt = System.currentTimeMillis();
        System.out.println("Time set: " + (kt-bd));
        frame.add(panelToolbar, BorderLayout.NORTH);
        //System.out.println(panelUser.getPreferredSize());
        hideCenter(panelProduct); // Hiện JPanel sản phẩm
        frame.add(panelMenu, BorderLayout.WEST);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Thread thread = new Thread(new function(frame, panelProduct, queue, panelUser));
        thread.start(); // Thực hiện upload ảnh

    }

    public static void hideCenter(JPanel panelhide) {
        frame.getContentPane().removeAll();
        if (panelhide.getName().equals("panelMainCenter")) {
            frame.add(panelMenu, BorderLayout.WEST);
        }
        frame.add(panelToolbar, BorderLayout.NORTH);
        frame.add(panelhide, BorderLayout.CENTER); //Vị trí trung tâm
        frame.validate(); // update ngay khi vừa add vào
        frame.repaint();
        panelhide.setVisible(true);
    }

    public static void main(String[] args) {
        new Form_User();
    }

}

class function implements Runnable {
    JFrame frame;
    JPanel panelUser;
    Queue<Thread> queue;
    JPanel panel;

    public function(JFrame frame, JPanel panel, Queue<Thread> queue, JPanel panelUser) {
        this.frame = frame;
        this.panel = panel;
        this.queue = queue;
        this.panelUser = panelUser;
    }

    @Override
    public void run() {
        while (queue.peek() != null) {
            //System.out.println("Number request in Queue: " + queue.size());
            Thread test = queue.poll();
            test.run();
        }
    }
}

class UpdateUserInterface implements Runnable {
    JPanel panelToolbar;
    JPanel panelMenu;
    JPanel panelProduct;
    JPanel panelUser;
    public UpdateUserInterface(JPanel panelToolbar, JPanel panelMenu, JPanel panelProduct, JPanel panelUser) {
        this.panelToolbar = panelToolbar;
        this.panelMenu = panelMenu;
        this.panelProduct = panelProduct;
        this.panelUser = panelUser;
    }

    @Override
    public void run() {
        // Set thanh công cụ
        panelToolbar = PanelToolBar.panelToolBar();
        // Set menu
        panelMenu = PanelMenu.panelMenu();
        // Set màn hình chính
        panelProduct = PanelContent.panelCenter();
        // UI of PanelUser
        panelUser = PanelUser.panelUser();
    }
}