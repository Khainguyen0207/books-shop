package QuanLiThuvien.Forms.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import QuanLiThuvien.Forms.Login.ErrorList;
import QuanLiThuvien.Forms.Login.Form_Login;
import QuanLiThuvien.Models.Model;
import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SendMail;
import QuanLiThuvien.brain.SetIcon;

public class PanelUser extends Form_User {
    public static Map<String, String> map;
    static String mail = null;
    static Boolean editInfomation = false;
    public static Boolean statusSendMail = false;

    static final Color color = new Color(238, 238, 238);
    static final Font font = new Font("JetBrains Mono", Font.PLAIN, 15);
    public static JPanel panelUser() {
        for (Map<String, String> data : UserModel.getDataByID("users", ID)) {
            mail = data.get("email");
            System.out.println("ID: " + ID);
            map = data;
        }
        long st = System.currentTimeMillis();
        JPanel panelUser = new JPanel();
        panelUser.setLayout(new BorderLayout());
        panelUser.setBackground(new Color(225, 240, 218));
        panelUser.setName("panelUser");
        int borderPanelUser = 3;
        panelUser.setBorder(BorderFactory.createEmptyBorder(borderPanelUser, borderPanelUser, borderPanelUser, borderPanelUser));

        panelUser.add(westInfo(), BorderLayout.WEST);
        panelUser.add(centerInfo(), BorderLayout.CENTER);
        
        long kt = System.currentTimeMillis();
        System.out.println("Time: " + (kt - st));
        return panelUser;
    }

    public static void setHidePanel(JPanel panel) {
        panelUser.removeAll();
        panelUser.add(westInfo(), BorderLayout.WEST);
        panelUser.add(panel, BorderLayout.CENTER);
        panelUser.updateUI();
    }

    private static JPanel changePassword() {
        JPanel changePass = new JPanel();
        AllComponent.getPanel(westInfo(), "changepass").setVisible(false);
        changePass.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        changePass.setBackground(color);
        changePass.setName("changePassword");
        changePass.setLayout(new BorderLayout());
        changePass.add(new JLabel(){
            {
                setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
                setForeground(new Color(135, 169, 34));
                setText("<html> <p style='margin-top: 20px;'>Thay đổi mật khẩu</p> </html>");
                setHorizontalAlignment(JLabel.CENTER);
            }
        }, BorderLayout.NORTH);
        changePass.add(formChangePassword(), BorderLayout.CENTER);
        return changePass;
    }

    private static JPanel formChangePassword() {
        JPanel formChangePassword = new JPanel();
        formChangePassword.setBackground(color);
        formChangePassword.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        int sizeColmn = 30;
        formChangePassword.add(new JLabel("Mật khẩu hiện tại"){
            {
                setFont(font);
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formChangePassword.add(new JTextField(sizeColmn){
            {
                setFont(font);
                setName("oldpassword");
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formChangePassword.add(new JLabel("Mật khẩu mới"){
            {
                setFont(font);
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formChangePassword.add(new JPasswordField(sizeColmn){
            {
                setFont(font);
                setName("newpassword");
                setEchoChar('•');
            }
        }, gbc);

        gbc.gridx = 1;
        formChangePassword.add(new JCheckBox(){
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (isSelected()) {
                            ((JPasswordField) AllComponent.getPanel(formChangePassword, "newpassword")).setEchoChar((char) 0);
                        } else {
                            ((JPasswordField) AllComponent.getPanel(formChangePassword, "newpassword")).setEchoChar('•');
                        }
                    }
                });
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        formChangePassword.add(new JLabel("Xác nhận mật khẩu mới"){
            {
                setFont(font);
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        formChangePassword.add(new JPasswordField(sizeColmn){
            {

                setEchoChar('•');
                setFont(font);
                setName("confirmpassword");
            }
        }, gbc);
        gbc.gridx = 1;
        formChangePassword.add(new JCheckBox(){
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (isSelected()) {
                            ((JPasswordField) AllComponent.getPanel(formChangePassword, "confirmpassword")).setEchoChar((char) 0);
                        } else {
                            ((JPasswordField) AllComponent.getPanel(formChangePassword, "confirmpassword")).setEchoChar('•');
                        }
                    }
                });
            }
        }, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy++;
        formChangePassword.add(new JButton("Thay đổi"){
            {
                setCursor(new Cursor(12));
                setBorderPainted(false);
                setFocusPainted(false);
                setBackground(new Color(22, 255, 14, 1));
                setFont(font);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String oldpassword = getTextComponent(formChangePassword, "oldpassword");
                        String newpassword = getTextComponent(formChangePassword, "newpassword");
                        String confirmpassword = getTextComponent(formChangePassword, "confirmpassword");
                        if (ErrorList.checkPasswordOld(newpassword) || !newpassword.equals(confirmpassword)) {
                            if (newpassword.equals(confirmpassword)) {
                                JOptionPane.showMessageDialog(null, ErrorList.errors.values().toString(), "Thông báo", JOptionPane.ERROR_MESSAGE);
                                ErrorList.errors.clear();
                            } else {
                                JOptionPane.showMessageDialog(null, "Mật khẩu không trùng nhau", "Thông báo",JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            Map<String, String> data = new HashMap<>();
                            data.put("password", newpassword);
                            UserModel.updateData("users", data, ID);
                            setHidePanel(changePassword());
                            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
                        }
                    }
                });
            }
        }, gbc);

        return formChangePassword;
    }

    private static String getTextComponent(Component component, String nameComponent) {
        String data = ((JTextField) AllComponent.getPanel(component, nameComponent)).getText();
        return data;
    }

    private static JPanel westInfo() {
        JPanel panelWestInfo = new JPanel();
        panelWestInfo.setLayout(new BorderLayout());
        panelWestInfo.setBackground(new Color(225, 240, 218));
        panelWestInfo.add(imgUser(), BorderLayout.NORTH);
        panelWestInfo.add(new JPanel(){
            {
                setLayout(new BoxLayout(this, 1));
                setBackground(panelWestInfo.getBackground());
                setName("labelwest");
                add(new JLabel(){
                    {
                        setText("<html><p style='margin-top:10px;'>Đổi mật khẩu </p></html>");
                        setName("changepass");
                        setForeground(new Color(0, 225, 22, 255));
                        setFont(font);
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                editInfomation = true;
                                setHidePanel(changePassword());
                                System.out.println("Đổi Mật Khẩu");
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                setText("<html><p style='margin:10px; border-bottom:solid 2px; padding-bottom: 2px;'>Đổi mật khẩu </p></html>");
                                setCursor(new Cursor(12));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setText("<html><p style='margin-top:10px;'>Đổi mật khẩu </p></html>");
                                setCursor(new Cursor(0));
                            }
                        });
                    }
                });

                add(new JLabel(){
                    {
                        setText("<html><p style='margin-top:10px;'>Quên mật khẩu </p></html>");
                        setName("forgetpass");
                        setForeground(new Color(0, 225, 22, 255));
                        setFont(font);
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                editInfomation = true;
                                setHidePanel(forgetPassword());
                                System.out.println("Quên Mật Khẩu");
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                setText("<html><p style='margin:10px; border-bottom:solid 2px; padding-bottom: 2px;'>Quên mật khẩu </p></html>");
                                setCursor(new Cursor(12));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setText("<html><p style='margin-top:10px;'>Quên mật khẩu </p></html>");
                                setCursor(new Cursor(0));
                            }
                        });
                    }
                });

                add(new JLabel(){
                    {
                        setText("<html><p style='margin-top:10px;'>Thanh toán</p></html>");
                        setName("forgetpass");
                        setForeground(new Color(0, 225, 22, 255));
                        setFont(font);
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                editInfomation = true;
                                setHidePanel(panelBuyProduct());
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                setText("<html><p style='margin:10px; border-bottom:solid 2px; padding-bottom: 2px;'>Thanh toán </p></html>");
                                setCursor(new Cursor(12));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setText("<html><p style='margin-top:10px;'>Thanh toán </p></html>");
                                setCursor(new Cursor(0));
                            }
                        });
                    }
                });

            }
        });
        panelWestInfo.add(new JButton(){
            {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
                setHorizontalAlignment(JLabel.CENTER);
                setFocusPainted(false);
                setBorderPainted(false);
                setForeground(new Color(0, 224, 59));
                setBackground(new Color(247, 245, 205));
                if (!editInfomation) {
                    setText("Đăng xuất");
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            int result = JOptionPane.showConfirmDialog(null, "Bạn muốn đăng xuất?", "Thông báo", 0 , 1);
                            if (result == JOptionPane.YES_OPTION) {
                                System.out.println("The application is exiting...");
                                Form_Login.resetText();
                                Form_Login.form_login();
                                Form_User.checkLogin = false;
                                frame.dispose();
                            } else {
                                System.out.println("You just refused to exit the application!");
                            }
                        }
                    });
                } else {
                    setText("Quay lại");
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            editInfomation = false;
                            setHidePanel(centerInfo());
                            System.out.println("Quay lại");
                        }
                    });
                }
            }
        }, BorderLayout.SOUTH);
        return panelWestInfo;
    }

    private static JPanel forgetPassword() {
        JPanel changePass = new JPanel();
        AllComponent.getPanel(westInfo(), "changepass").setVisible(false);
        changePass.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        changePass.setBackground(color);
        changePass.setName("forgetPassword");
        changePass.setLayout(new BorderLayout());
        changePass.add(new JLabel(){
            {
                setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
                setForeground(new Color(135, 169, 34));
                setText("<html> <p style='margin-top: 20px;'>Quên mật khẩu</p> </html>");
                setHorizontalAlignment(JLabel.CENTER);
            }
        }, BorderLayout.NORTH);
        changePass.add(formForgetPassword(), BorderLayout.CENTER);
        return changePass;

    }

    private static JPanel formForgetPassword() {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setName("formForgetPassword");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0,0,10,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel(){
            {
                setFont(font);
                setText("Nhập mã được gửi qua mail của bạn");
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JTextField(30){
            {

                setFont(font);
                setName("code");
            }
        }, gbc);
        gbc.gridx = 1;
        panel.add(new JButton(){
            {
                setCursor(new Cursor(12));
                setBorderPainted(false);
                setFocusPainted(false);
                setBackground(new Color(22, 255, 14, 1));
                setFont(font);
                setName("sendmail");
                setText("Gửi mã");
                if (!statusSendMail) {
                    setEnabled(true);
                } else {
                    setText("Đã gửi");
                    setEnabled(false);
                }
                Thread time = new Thread(new eventButton(this));
                Thread sendmail = new Thread(new btnSendMail(mail)); //điền mail cần gửi
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        time.start();
                        if (statusSendMail) {
                            System.out.println("Dừng gửi mail");
                        } else  {
                            sendmail.start();
                        }

                    }
                });
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(){
            {
                setName("labelpassnew");
                setFont(font);
                setText("Mật khẩu mới");
                setVisible(false);
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JPasswordField(30){
            {
                setEchoChar('•');
                setFont(font);
                setVisible(false);
                setName("newpassword");
            }
        }, gbc);
        gbc.gridx = 1;
        panel.add(new JCheckBox(){
            {
                setName("checkboxnewpassword");
                setVisible(false);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (isSelected()) {
                            ((JPasswordField) AllComponent.getPanel(panel, "newpassword")).setEchoChar((char) 0);
                        } else {
                            ((JPasswordField) AllComponent.getPanel(panel, "newpassword")).setEchoChar('•');
                        }
                    }
                });
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(){
            {
                setName("labelconfirmpassword");
                setFont(font);
                setVisible(false);
                setText("Nhập lại mật khẩu mới");
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JPasswordField(30){
            {
                setEchoChar('•');
                setFont(font);
                setVisible(false);
                setName("confirmpassword");
            }
        }, gbc);
        gbc.gridx = 1;
        panel.add(new JCheckBox(){
            {
                setName("checkboxconfirmpassword");
                setVisible(false);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (isSelected()) {
                            ((JPasswordField) AllComponent.getPanel(panel, "confirmpassword")).setEchoChar((char) 0);
                        } else {
                            ((JPasswordField) AllComponent.getPanel(panel, "confirmpassword")).setEchoChar('•');
                        }
                    }
                });
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panel.add(new JLabel(){
            {
                setForeground(Color.red);
                setFont(font);
                setName("notification");
            }
        }, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JButton("Xác nhận"){
            {
                setCursor(new Cursor(12));
                setBorderPainted(false);
                setFocusPainted(false);
                setBackground(new Color(22, 255, 14, 1));
                setFont(font);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String code = getTextComponent(panel, "code");
                        int a = SendMail.code;
                        String data = String.valueOf(a);
                        if (code.length() != 4 || !data.equals(code)) {
                            ((JLabel) AllComponent.getPanel(panel, "notification")).setText("Sai mã");
                        }
                        if (code.length() == 4 && data.length() == 4) {
                            if (data.equals(code)) {
                                ((JLabel) AllComponent.getPanel(panel, "notification")).setText("");
                                AllComponent.getPanel(panel, "sendmail").setVisible(false);
                                AllComponent.getPanel(panel, "code").setEnabled(false);
                                setVisible(false);
                                hideComponent(panel, "labelpassnew");
                                hideComponent(panel, "labelconfirmpassword");
                                hideComponent(panel, "checkboxnewpassword");
                                hideComponent(panel, "newpassword");
                                hideComponent(panel, "confirmpassword");
                                hideComponent(panel, "checkboxconfirmpassword");
                                hideComponent(panel, "ChangeForget");
                            }
                        }
                    }
                });
            }
        }, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JButton("Đổi mật khẩu"){
            {
                setName("ChangeForget");
                setCursor(new Cursor(12));
                setBorderPainted(false);
                setFocusPainted(false);
                setBackground(new Color(22, 255, 14, 1));
                setFont(font);
                setVisible(false);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String newpassword = getTextComponent(panel, "newpassword");
                        String confirmpassword = getTextComponent(panel, "confirmpassword");
                        if (ErrorList.checkPasswordOld(newpassword) || !newpassword.equals(confirmpassword)) {
                            if (newpassword.equals(confirmpassword)) {
                                JOptionPane.showMessageDialog(null, ErrorList.errors.toString(), "Thông báo", JOptionPane.ERROR_MESSAGE);
                                ErrorList.errors.clear();
                            } else {
                                JOptionPane.showMessageDialog(null, "Mật khẩu không trùng nhau", "Thông báo",JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            Map<String, String> data = new HashMap<>();
                            data.put("password", newpassword);
                            UserModel.updateData("users", data, ID);
                            setHidePanel(forgetPassword());
                        }
                    }
                });
            }
        }, gbc);
        return panel;
    }

    private static void hideComponent(JPanel panel, String name) {
        AllComponent.getPanel(panel, name).setVisible(true);
    }

    private static JLabel setCreateLabel(String text, String name) {
        JLabel labelwest = new JLabel();
        labelwest.setHorizontalAlignment(JLabel.CENTER);
        labelwest.setText(text);
        labelwest.setName(name);
        labelwest.setFont(font);
        return labelwest;
    }

    private static JPanel centerInfo() {
        JPanel panelCenterInfo = new JPanel();
        AllComponent.getPanel(westInfo(), "changepass").setVisible(true);
        panelCenterInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        panelCenterInfo.setBackground(color);
        panelCenterInfo.setLayout(new BorderLayout());
        panelCenterInfo.setName("panelCenterInfo");
        panelCenterInfo.add(infomationAndSecurity(), BorderLayout.NORTH);
        panelCenterInfo.add(rankAndTransport("Thân Thiết"), BorderLayout.WEST);
        return panelCenterInfo;
    }

    private static JPanel rankAndTransport(String rank) {
        JPanel rankAndTransport = new JPanel();
        rankAndTransport.setBackground(color);
        rankAndTransport.setBorder(null);
        rankAndTransport.setLayout(new BorderLayout());
        rankAndTransport.add(new JPanel(){
            {
                setBackground(color);
                setLayout(new BoxLayout(this, 1));
                add(new JLabel(){
                    {
                        setText("<html> <p style='margin-left: 5px;'>" + "Hạng thành viên: " + rank +"</p> </html>");
                        
                        setFont(font);
                    }
                });
                add(new JLabel(){
                    {
                        setText("<html> <p style='margin-left: 5px;'>" + "Trạng thái giao hàng" + "</p> </html>");
                        setFont(font);
                    }
                });
            }
        }, BorderLayout.NORTH);
        int number = 13;
        rankAndTransport.add(new JPanel(){
            {
                setBackground(color);
                setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
                add(setBtnTransport("<html> Giỏ<br>hàng ("+ ShopCart.numberProductInShopcart +")</html>", "giohang", "Icon/shopcart.png"));
                add(setBtnTransport("<html> Đang<br>giao ("+ number +")</html>", "danggiao", "Icon/danggiao.png"));
                add(setBtnTransport("<html> Đã<br>mua ("+ number +")</html>", "damua", "Icon/danhan.png"));
            }
        }, BorderLayout.CENTER);
        rankAndTransport.add(new JPanel(){
            {
                setBackground(color);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                add(new JLabel(){
                    {
                        setText("<html> <p style='margin-left: 5px; border-bottom: solid 2px; padding-bottom: 2px;'>" + "Liên hệ hỗ trợ: " +"</p> </html>");
                        setFont(font);
                    }
                });
                add(new JLabel(){
                    {
                        setText("<html> <p style='margin-left: 8px;'>" + 
                        "Email: shopbedrood@book.vn<br>" +
                        "SĐT: 0234 567 999" + 
                        "</p> </html>");
                        setFont(font);
                    }
                });
                add(new JLabel(){
                    {
                        setForeground(Color.black);
                        setText("<html> <p style='margin-left: 8px;'>" + 
                        "Facebook: Nguyễn Trọng Khải <br>" +
                        "</p> </html>");
                        setFont(font);
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                String linkProfile = "https://www.facebook.com/khainguyen0207/";
                                Desktop.getDesktop().browse(URI.create(linkProfile));
                                } catch (Exception l) {
                                    System.out.println(l);
                                }
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                                setForeground(Color.red);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                setForeground(Color.black);
                            }
                        });


                    }
                });

                add(new JLabel(){
                    {
                        setForeground(Color.black);
                        setText("<html> <p style='margin-left: 8px;'>" + 
                        "Github: Khainguyen0207" + 
                        "</p> </html>");
                        setFont(font);
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                String linkProfile = "https://github.com/Khainguyen0207/";
                                Desktop.getDesktop().browse(URI.create(linkProfile));
                                } catch (Exception l) {
                                    System.out.println(l);
                                }
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                                setForeground(Color.red);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                setForeground(Color.black);
                            }
                        });
                    }
                });

                add(new JLabel(){
                    {
                        setText("<html> <p style='margin: 10px 10px 0px 10px;color:gray; '>" + 
                        "2024© Copyright SHOP BEDROOD" + 
                        "</p> </html>");
                    }
                });
            }
        }, BorderLayout.SOUTH);
        return rankAndTransport;
    }

    private static JButton setBtnTransport(String text, String name, String img) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(SetIcon.imageProcess(img, 25, 25)));
        button.setText(text);
        button.setName(name);
        button.setBackground(new Color(247, 245, 205));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 60));
        button.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionButtonTransport(button);
            }
        });
        return button;
    }

    private static void actionButtonTransport(JButton button) {
        if (button.getName().equals("giohang")) {
            hideCenter(shopCart);
        }
    }

    private static JPanel infomationAndSecurity() {
        JPanel infomationAndSecurity = new JPanel();
        infomationAndSecurity.setBorder(null);
        infomationAndSecurity.setBackground(color);
        infomationAndSecurity.setName("infomationAndSecurity");
        infomationAndSecurity.setLayout(new BorderLayout());
        infomationAndSecurity.add(infomationUser(), BorderLayout.WEST);
        infomationAndSecurity.add(security(), BorderLayout.CENTER);
        infomationAndSecurity.add(new JLabel(){
            String text = "Thay đổi thông tin";
            {
                setForeground(Color.red);
                setHorizontalAlignment(JLabel.RIGHT);
                setText("<html> <p style='margin: 0px 5px 5px 0px;  border-bottom: solid 2px; padding-bottom-5px;'> "+ text + "</p> </html>");
                setFont(new Font("JetBrains Mono", Font.PLAIN, 15));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        editInfomation = !editInfomation;
                        for (String string : map.keySet()) {
                            if (string.equals("username") || string.equals("create_day") || string.equals("ip")) {
                                continue;
                            } else {
                                setStatus(infomationAndSecurity, string);
                            }
                        }
                        // Câu lệnh check điều kiện
                        if (editInfomation == false) {
                            int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn thay đổi thông tin?", "Thông báo", JOptionPane.OK_CANCEL_OPTION);
                            System.out.println(result);
                            if( result == 0) {
                                String[] key = {"name", "sex", "birthday", "email", "phone"};
                                JOptionPane.showMessageDialog(null, "Đã thay đổi");
                                Map<String, String> change = new HashMap<>();
                                for (String getKey : key) {
                                    String data = ((JTextField) AllComponent.getPanel(panelUser, getKey)).getText();
                                    change.put(getKey, data);
                                }
                                UserModel.updateData("users", change, ID);
                            } else {
                                JOptionPane.showMessageDialog(null, "Đã hủy thay đổi");
                            }
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setForeground(Color.BLUE);
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setForeground(Color.red);
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });
            }
        }, BorderLayout.SOUTH);
        return infomationAndSecurity;
    }

    private static JPanel infomationUser() {
        JPanel infomationsUser = new JPanel(); //JPanel thông tin
        infomationsUser.setBackground(color);
        infomationsUser.setLayout(new BorderLayout());
        // Gộp lại 1 JPanel - start
        infomationsUser.add(new JLabel(){
            String text = "Thông tin";
            {
                setForeground(Color.red);
                setText("<html> <p style='margin-left: 2px; border-bottom: solid 2px; padding-bottom: 2px'>"+ text + "</p> </html>");
                setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
            }
        }, BorderLayout.NORTH);
        infomationsUser.add(setInfomationUser(), BorderLayout.CENTER);
        // Gộp lại 1 JPanel - end
        return infomationsUser;
    }

    private static JPanel security() {
        JPanel security = new JPanel();
        security.setLayout(new BorderLayout());
        security.setBackground(color);
        security.setName("security");
        security.add(new JLabel(){
            String text = "Bảo mật";
            {
                setForeground(Color.red);
                setText("<html> <p style='border-bottom: solid 2px; padding-bottom: 2px'>"+ text + "</p> </html>");
                setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
            }
        }, BorderLayout.NORTH);
        security.add(setSecurityUser(), BorderLayout.CENTER);
        return security;
    }

    private static JPanel imgUser() {
        JPanel imageUser = new JPanel();  //JPanel hình ảnh người dùng
        imageUser.setBackground(new Color(225, 240, 218));
        imageUser.add(new JLabel(){
            {
                setVerticalAlignment(JLabel.TOP);
                setName("img-userlogin");
                setIcon(new ImageIcon(SetIcon.imageProcess("Icon/img-user-default.png", 200, 200)));
            }
        });
        return imageUser;
    }
   
    private static JPanel setInfomationUser() {
        JPanel infoUser = new JPanel(){
            // @Override
            // protected void paintComponent(Graphics g) {
            //     super.paintComponent(g);
            //     g.setColor(Color.BLACK);
            //     g.drawRoundRect(0, 0, getPreferredSize().width - 5, getPreferredSize().height - 5, 20, 20);
            // }
        }; //set thông tin người dùng
        infoUser.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoUser.setBackground(color); //new Color(238, 238, 238)
        infoUser.setBorder(BorderFactory.createEmptyBorder(5,2,5,15));
        String key[] = {"name", "sex", "birthday", "create_day"}; //Set key
        String[] dataInfo = {"Tên Người Dùng  ", "Giới Tính  ", "Ngày Sinh  ", "Ngày Tạo  "}; 
        //Cài đặt thông tin người dùng
        //Tương tác thông tin
        infoUser.add(setLabelInfoUser(map, dataInfo, key));
        
        return infoUser;
    }
    
    private static JPanel setSecurityUser() {
        JPanel infoUser = new JPanel();
        infoUser.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoUser.setBackground(new Color(238, 238, 238)); //new Color(238, 238, 238)
        infoUser.setBorder(BorderFactory.createEmptyBorder(5,2,5,15));
        String key[] = {"username", "email", "phone", "ip"}; //Set key
        String[] dataInfo = {"ID Người Dùng  ", "Email  ", "SĐT  ", "IP  "}; 
        //Cài đặt thông tin người dùng
        //Tương tác thông tin
        infoUser.add(setLabelInfoUser(map, dataInfo, key));
        return infoUser;
    }

    private static void setStatus(JPanel infoUser, String nameComponent) {
        int b = 5;
        if(editInfomation) {
            ((JTextField) AllComponent.getPanel(infoUser, nameComponent)).setBackground(Color.WHITE);
            ((JTextField) AllComponent.getPanel(infoUser, nameComponent)).setBorder(BorderFactory.createEmptyBorder(b,b,b,b));
        } else {

            ((JTextField) AllComponent.getPanel(infoUser, nameComponent)).setBackground(Color.LIGHT_GRAY);
            ((JTextField) AllComponent.getPanel(infoUser, nameComponent)).setBorder(BorderFactory.createEmptyBorder(b,b,b,b));
        }
        ((JTextField) AllComponent.getPanel(infoUser, nameComponent)).setEditable(editInfomation);
    }

    private static Component setLabelInfoUser(Map<String , String> map, String[] dataInfo, String[] keys) { // Thiết lập thông tin uset với các thuộc tính
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setLayout(new GridBagLayout()); // 1: Y, 2: X -> Dạng BoxLayout
        panel.setName("infomation");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        int i = 0, col = 0, row = 0;
        for (String key : keys) {
            for (Entry<String, String> value : map.entrySet()) {
                if (value.getKey().equals(key)) {
                    gbc.gridx = col;
                    gbc.gridy = row;
                    col++;
                    panel.add(new JLabel(dataInfo[i]){
                        {
                            setFont(font);
                        }
                    }, gbc);
                    gbc.gridx = col;
                    gbc.gridy = row;
                    panel.add(new JTextField(checkValueEmpty(value.getValue())){
                        {
                            setMinimumSize(new Dimension(100, 20));
                            setName(key);
                            setEditable(false);
                            setFont(font);
                            setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                            setBackground(Color.LIGHT_GRAY);
                        }
                    }, gbc);
                    col = 0;
                    row++;
                    i++;
                }
            }
        }
        return panel;
    }

    private static String checkValueEmpty(String getValue) {
        if (getValue==null) {
            return "Chưa cập nhật";
        }
        return getValue;
    }

    public static JPanel panelBuyProduct() {
        JPanel panelMain = new JPanel();
        panelMain.setBackground(color);
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        panelMain.setLayout(new BorderLayout());
        panelMain.add(new JPanel(){
            {
                setBackground(panelMain.getBackground());
                setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                setName("");
                add(new JLabel("Thông tin vận chuyển"){
                    {
                        setHorizontalAlignment(JLabel.LEFT);
                        Font font1 = new Font("JetBrains Mono", Font.PLAIN, 30);
                        setForeground(new Color(135, 169, 34));
                        setFont(font1);
                    }
                });
            }
        }, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setBackground(panelMain.getBackground());
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
        panel.setName("panelBuyProduct");
        panel.add(new JPanel(){
            {
                setBackground(panelMain.getBackground());
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                String[] name = {"Full name", "Address", "Email", "Number Phone"};
                cpnData(name, this);
            }
        }, BorderLayout.NORTH);
        panel.add(new JPanel(){
            {
                setBackground(panelMain.getBackground());
                add(new JLabel("Payment Methods: "){
                    {
                        setFont(font);
                    }
                });

                String[] data = {"Thanh toán sau khi nhận hàng", "Thanh toán trực tuyến"};
                add(new JComboBox<>(data){
                    {
                        setBackground(panelMain.getBackground());
                        setFont(font);
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                System.out.print("->");
                            }
                        });
                    }
                });
            }
        }, BorderLayout.WEST);
        panelMain.add(panel, BorderLayout.WEST);
        panelMain.add(new JPanel(){
            {
                setBackground(panelMain.getBackground());
                setName("");
                add(new JButton(){
                    {
                        setFocusPainted(false);
                        setBorderPainted(false);
                        setName("");
                        setText("Đặt hàng");
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                System.out.println("Đã mua hàng vui lòng theo dõi tiến trình mua hàng trên ứng dụng");
                            }
                        });


                    }
                });
            }
        }, BorderLayout.SOUTH);
        return panelMain;
    }

    private static void cpnData(String[] name, JPanel panel) {
        for (String data : name) {
            panel.add(new JLabel(){
                {
                    setText(data);
                    setFont(font);
                }
            });

            panel.add(new JTextField(){
                {
                    setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
                    setFont(font);
                }
            });
        }
    }
}

class btnSendMail implements  Runnable{
    private String mailto;

    btnSendMail(String mail) {
        this.mailto = mail;
    }

    @Override
    public void run() {
        SendMail.sendMail(mailto);
    }

}

class eventButton implements Runnable{
    private JButton button;
    eventButton(JButton btn) {
        this.button = btn;
    }

    @Override
    public void run() {
        PanelUser.statusSendMail = true;
        for(int i = 60; i> 0; i--) {
            button.setEnabled(false);
            button.setText(i + "s");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            button.setText("Gửi lại");
            button.setEnabled(true);
        }
        PanelUser.statusSendMail = false;
    }
}
