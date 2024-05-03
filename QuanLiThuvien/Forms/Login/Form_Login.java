package QuanLiThuvien.Forms.Login;


import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.Map;

import javax.swing.*;

import QuanLiThuvien.Forms.Admin.Admin;
import QuanLiThuvien.Forms.User.Form_User;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SetIcon;
/**
 * Form_Login
 */
public class Form_Login implements ActionListener {
    static final Font font = new Font("JetBrains Mono", Font.BOLD, 15);
    public static JFrame frame = new JFrame("LOGIN");
    public static JPanel panelLogin;
    static JPanel panelRegister;
    static JPanel panelForget;
    public static void form_login() {
        long st = System.currentTimeMillis();
        SetIcon.setIcon(frame, "Icon/formLogin.png", 40, 40);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Error LookAndFeel");
        }
        frame.setSize(654*2, 678);

        panelLogin = Login.setPanelLogin();
        panelForget = ForgetPassword.forgetPassword();
        panelRegister = Register.setPanelRegister();
        hideCenter(panelLogin);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        long kt = System.currentTimeMillis();
        System.out.printf("Time set Login: %d", (kt - st));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }

    private static JLabel imageLogin() {
        JLabel label = new JLabel();
        label.setName("imageLogin");
        label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Icon/background-login.png")));
        return label;
    }

    public static JPanel nameDesign() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(204,255,255));
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.add(new JLabel(){
            {
                setName("profileDesigner");
                setBackground(Color.BLACK);
                setText("Designer: khainguyen0207");
                setFont(font);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String linkProfile = "https://www.facebook.com/khainguyen0207/";
                            Desktop.getDesktop().browse(URI.create(linkProfile));
                        } catch (Exception l) {
                            System.out.println(l.getMessage());
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
        });
        return panel;
    }

    public static void function(String role, int ID) {
        if (role.equals("admin")) {
            frame.setVisible(false);
            Admin.admin();
            frame.dispose();
        } else if (role.equals("user")) {
            frame.setVisible(false);
            Form_User.ID = ID;
            new Form_User();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Tài khoản của bạn đã bị khóa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void resetText() {
        AllComponent.getPanel(panelLogin, "btnLogin").setEnabled(true);
        ((JTextField) AllComponent.getPanel(panelLogin, "txtUserLogin")).setText("");
        ((JTextField) AllComponent.getPanel(panelLogin, "txtPassLogin")).setText("");

    }

    protected static void hideCenter(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.add(imageLogin(), BorderLayout.WEST);
        frame.validate();
        frame.repaint();
    }
}