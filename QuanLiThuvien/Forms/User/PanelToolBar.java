package QuanLiThuvien.Forms.User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import QuanLiThuvien.Forms.Login.Form_Login;
import QuanLiThuvien.brain.SetIcon;

public class PanelToolBar extends Form_User{
    static Timer timer;
    static boolean TestMax = true;

    public static JPanel panelToolBar() {
        JPanel panelToolbar = new JPanel();
        panelToolbar.setName("panelToolbar");
        panelToolbar.setLayout(new BorderLayout());
        panelToolbar.setBackground(new Color(1430019));
        panelToolbar.setPreferredSize(new Dimension(1100, 70));
        //Setting ben trai
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(panelToolbar.getBackground());
        panelLeft.setName("panelLeft");
        panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
        panelLeft.add(setLabel("<html>Trang<br>chủ</html>", "trangchu", "Icon/home.png"));
        panelLeft.add(setLabel("<html>Danh<br>mục</html>","danhmuc", "Icon/danhmuc.png"));
        
        //setting ben phai
        JPanel panelRight = new JPanel();
        panelRight.setName("panelRight");
        panelRight.setBackground(panelToolbar.getBackground());
        panelRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 10));
        panelRight.add(setLabel("<html>Giỏ<br>hàng</html>","giohang", "Icon/shopcart.png"));
        panelRight.add(new JButton(){
            {
                setName("btnLogin");
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setFont(new Font("", Font.PLAIN, 14));
                setHorizontalAlignment(JLabel.CENTER);
                if (checkLogin) {
                    setIcon(new ImageIcon(SetIcon.imageProcess("Icon/user-info.png", 24, 32), "login"));
                    setText("<html>Người<br>dùng</html>");
                } else {
                    setIcon(new ImageIcon(SetIcon.imageProcess("Icon/user-login.png", 24, 32), "login"));
                    setText("<html>Đăng<br>nhập</html>");
                }
                setBackground(new Color(24, 119, 242));
                setForeground(Color.WHITE);
                setBorderPainted(false);
                setFocusPainted(false);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (checkLogin) {
                            hideCenter(panelUser);
                        } else {
                            frame.setVisible(false);
                            Form_Login.form_login();
                            Form_Login.resetText();
                            frame.dispose();
                        }
                    }
                });
            }

            
        });
        JPanel panelfind = new JPanel();
        panelfind.add(panelFind());
        panelToolbar.add(panelLeft, BorderLayout.WEST);
        panelToolbar.add(panelRight, BorderLayout.EAST);
        return panelToolbar;
    }

    private static JPanel panelFind() {
        JPanel panel = new JPanel();
        panel.setName("");
        panel.setBackground(Color.WHITE);
        panel.setBorder(null);
        JTextField find = new JTextField();
        find.setName("panelFind");
        find.setFont(new Font("", Font.PLAIN, 20));
        find.setBorder(null);
        find.setPreferredSize(new Dimension(300, 30));
        panel.add(find);
        panel.add(new JLabel(){
            {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //System.out.println("Search data: " + find.getText());
                    }
                });
                setName("labelFind");
                setIcon(new ImageIcon(SetIcon.imageProcess("Icon/magnifier_64673.png", 20, 20)));
            }
        });
        return panel;
    }

    private static JButton setLabel(String text, String name, String urlImage) {
        JButton button = new JButton();
        button.setName("bg-button");
        button.setEnabled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        JLabel label = new JLabel();
        label.setText(text);
        label.setName(name);
        label.setIcon(new ImageIcon(SetIcon.imageProcess(urlImage, 24, 24)));
        label.setFont(new Font("", Font.PLAIN, 14));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (label.getName().equals("danhmuc")) {
                    label.setVisible(false);
                    setTimer(panelMenu, label);
                    timer.start(); 
                } else if(label.getName().equals("trangchu")) {
                    hideCenter(panelProduct);
                } else if(label.getName().equals("giohang") && checkLogin) {
                    hideCenter(shopCart);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập để mua hàng");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setText(text);
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setForeground(new Color(26, 13, 171));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText(text);
                label.setCursor(new Cursor(0));
                label.setForeground(Color.BLACK);
            }
        });
        button.add(label);
        return button;
    }

    private static void setTimer(JPanel panel, JComponent btn_1) {
        //Point point = new Point(panel.getLocation());
        Dimension dimension = new Dimension(panel.getPreferredSize());
        int height = (int) dimension.getHeight();
        int width = (int) dimension.getWidth();
        if (width == 0) {
            TestMax = true;
        }
        timer = new Timer(0, new ActionListener() {
            private int width = (int) dimension.getWidth();

            @Override
            public void actionPerformed(ActionEvent e) {                
                if (width < 200 && TestMax) {
                    width += 10;
                    panel.setPreferredSize(new Dimension( width, height));
                    panel.setLocation(0, 0);
                    panel.updateUI();
                } else if (width > 0 && !TestMax) {
                    width -= 10;
                    panel.setPreferredSize(new Dimension( width, height));
                    panel.setLocation(0, 0);
                    panel.updateUI();
                } else {
                    timer.stop();
                    TestMax = !TestMax;
                    btn_1.setVisible(true);
                }
            }
        });   
    }

   
}