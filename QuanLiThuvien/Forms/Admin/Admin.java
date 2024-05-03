package QuanLiThuvien.Forms.Admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import QuanLiThuvien.Forms.Login.Form_Login;
import QuanLiThuvien.Forms.User.Form_User;
import QuanLiThuvien.brain.SetIcon;


public class Admin implements ActionListener  {
    static JButton namelate = new JButton();
    
    static Color color = Color.GREEN;
    // JLabel lb = new JLabel("Login");
    static JFrame frame = new JFrame("QUẢN LÍ CỬA HÀNG SÁCH");
    static public JPanel panelCenterAdminShopBook = new JPanel();
    static public JPanel panelCenterQLKH = new JPanel();
    static public JPanel panelCenterAdminDSKH = new JPanel();
    
    public static void admin() {
        long bd = System.currentTimeMillis();
        frame.getContentPane().removeAll();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                frame.dispose();
            }

        });

        frame.setSize(800, 500);
        SetIcon.setIcon(frame, "Icon/admin.png", 40,40);
        frame.setLocationRelativeTo(null);
        //Panel Menu Setting
        JPanel panelMenu = new JPanel();
        panelMenu.setName("panelMenu");
        panelMenu.setSize(30, 0);
        panelMenu.setBackground(Color.GRAY);
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        //Panel Center Setting
        //panelCenterAdminShopBook.setBackground(Color.lightGray);
        //Add Button
        try {
            addButton(panelMenu, "QLKH", Color.lightGray, "Icon/QLKH.png");
            addButton(panelMenu, "KHÁCH HÀNG", Color.BLUE, "Icon/dskh.png");
            addButton(panelMenu, "SHOPBOOK", new Color(255,153,0), "Icon/book.png");
            addButton(panelMenu, "CỬA HÀNG", new Color(255,153,0), "Icon/Shoppingcart.png");
            addButton(panelMenu, "LOGOUT", Color.YELLOW, "Icon/exit.png");
        } catch (Exception e) {}
        //panelAdminQLKH
        AdminQLKH.panelAdminQLKH();
        AdminQLKH.updateQLKH();

        //panelAdminShopBook
        AdminShopBook.panelAdminShopBook();

        //panelAdminsetting
        AdminDSKH.panelAdminDSKH();
        
        //Setting JFrame
        frame.setLayout(new BorderLayout());
        frame.add(panelMenu, BorderLayout.WEST);
        frame.add(panelCenterAdminDSKH, BorderLayout.CENTER);
        frame.add(panelCenterAdminShopBook, BorderLayout.CENTER);
        frame.add(panelCenterQLKH, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.validate();
        frame.repaint();
        frame.setVisible(true);
        long kt = System.currentTimeMillis();
        System.out.println("Admin: " + (kt - bd));
    }

    public static void setPanelHideCenter(JPanel panel) {
        panelCenterAdminDSKH.setVisible(false);
        panelCenterAdminShopBook.setVisible(false);
        panelCenterQLKH.setVisible(false);
        panel.setVisible(true);
        frame.add(panel);

    }

    private static void addButton(JPanel panelMenu, String name, Color color, String imageIcon) {       
        JButton button = new JButton();
        button.setText(name);
        button.setName(name);
        button.setIcon(new ImageIcon(SetIcon.imageProcess(imageIcon, 25, 25)));
        button.setBackground(color);
        button.setMaximumSize(new Dimension(150, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        if (namelate.getText().equals("")) {
            setColorMenuClicked(button);
            
        }
        button.addActionListener(new Admin());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelMenu.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton name = (JButton)e.getSource();
        String i = name.getName();
        if (i.equals("QLKH")) {
            AdminQLKH.updateQLKH();
            setColorMenuClicked(name);
            color = Color.GREEN;
            setPanelHideCenter(panelCenterQLKH);
            
        } else if (i.equals("KHÁCH HÀNG")) { 
            setColorMenuClicked(name);
            color = Color.BLUE;
            setPanelHideCenter(panelCenterAdminDSKH);

        } else if (i.equals("SHOPBOOK")) {
            setColorMenuClicked(name);
            color = new Color(255,153,0);
            setPanelHideCenter(panelCenterAdminShopBook);
        } else if (i.equals("CỬA HÀNG")) {
            frame.setVisible(false);
            Form_User formUser = new Form_User();
            frame.dispose();
        } else if (i.equals("LOGOUT")) {      
            int result = JOptionPane.showConfirmDialog(null, "Bạn muốn thoát?", "Thông báo", 0 , 1);
            if (result == JOptionPane.YES_OPTION) {
                frame.setVisible(false);
                Form_Login.resetText();
                Form_Login.form_login();
                System.out.println("The application is exiting...");
                frame.dispose();
            } else {
                System.out.println("You just refused to exit the application!");
            }

        }
        
    }

    public static void setColorMenuClicked(JButton button) {
        if (namelate.getText().equals("")) {
            button.setBackground(Color.lightGray);
        }

        if (!namelate.equals(null) && namelate != button) {
            button.setBackground(Color.lightGray);
            namelate.setBackground(color);
        } 
        namelate = button;

    }

    public static void resetForm() {
        
        namelate = new JButton();
        color = Color.GREEN;
        setPanelHideCenter(panelCenterQLKH);
        frame.getContentPane().removeAll();
        frame.add(panelCenterAdminDSKH, BorderLayout.CENTER);
        frame.add(panelCenterAdminShopBook, BorderLayout.CENTER);
        AdminShopBook.updatePanelHideInfomationSetting("panelHideInfomationSetting");
        frame.add(panelCenterQLKH, BorderLayout.CENTER);
        frame.validate();
        frame.repaint();
    }
}
