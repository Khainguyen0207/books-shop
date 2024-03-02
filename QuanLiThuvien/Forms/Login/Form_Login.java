package QuanLiThuvien.Forms.Login;


import java.awt.*;
import java.awt.event.*;
import java.net.URI;

import javax.swing.*;

import QuanLiThuvien.Forms.Admin.Admin;
import QuanLiThuvien.UserAndPass.CsdlAccount;
import QuanLiThuvien.brain.AllComponent;

/**
 * Form_Login
 */
public class Form_Login implements ActionListener {
    public static JFrame frame = new JFrame("LOGIN");
    static JPanel panelLogin = new JPanel();
    static JPanel panelRegister = new JPanel();
    static JPanel panelForget = new JPanel();
    public static void form_login() {
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("QuanLiThuvien/Icon/iconLogin.png"));
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Error LookAndFeel");
        }
        frame.addWindowListener(new WindowAdapter() {
            
        });
        CsdlAccount.updateAccount();
        frame.setSize(654*2, 678);
        Login.setPanelLogin();
        frame.add(panelLogin, BorderLayout.CENTER);
        frame.add(new JLabel(){
            {
                setName("imageLogin");
                setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("QuanLiThuvien/Icon/background-login.png")));
            }
        }, BorderLayout.WEST);
        frame.add(new JLabel(){
            {
                setName("profileDesigner");
                setBackground(Color.BLACK);
                setText("Designer: khainguyen0207");
                setHorizontalAlignment(RIGHT);
                setFont(new Font("", Font.BOLD, 15));
                setCursor(new Cursor(12));
                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String linkProfile = "https://www.facebook.com/ntkhai2005";
                            Desktop.getDesktop().browse(URI.create(linkProfile));
                        } catch (Exception l) {
                            System.out.println(l);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setForeground(Color.GREEN);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setForeground(Color.BLACK);
                    }
                    
                });
            }
        }, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }

    public static void function(String rule) {
        if (rule.equals("admin")) {
            Admin.admin();
            frame.setVisible(false);
        } else if (rule.equals("user")) {
            System.out.println("updating...");
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Tài khoản của bạn đã bị khóa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void resetText() {
        ((JTextField) AllComponent.getPanel(panelLogin, "txtUserLogin")).setText("");
        ((JTextField) AllComponent.getPanel(panelLogin, "txtPassLogin")).setText("");
    } 
}