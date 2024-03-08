package QuanLiThuvien.Forms.Login;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import QuanLiThuvien.brain.AllComponent;

public class Login extends Form_Login {
    //Login
    public static JPanel setPanelLogin() {
        panelLogin = new JPanel();
        panelLogin.setName("panelLogin");
        panelLogin.setLayout(new BorderLayout());
        JPanel panelContentLogin = setContentLogin();
        panelLogin.add(panelContentLogin, BorderLayout.CENTER);
        panelLogin.add(Form_Login.nameDesign(), BorderLayout.SOUTH);
        return panelLogin;
    }

    private static JPanel setContentLogin() {
        JPanel panelContentLogin = new JPanel();
        panelContentLogin.setName("panelContentLogin");
        panelContentLogin.setLayout(new BorderLayout());
        panelContentLogin.setBorder(BorderFactory.createEmptyBorder(100, 150, 150, 150));
        panelContentLogin.setBackground(new Color(204,255,255));
        Color bgContent = new Color(0, 239, 253);
        panelContentLogin.add(new JPanel(){
            {
                setBackground(bgContent);
                add(new JLabel(){
                    {
                        setText("Login");
                        setName("loginText");
                        setFont(new Font("Maiandra GD", Font.BOLD, 40));
                        setHorizontalAlignment(CENTER);
                    }
                });
            }
        }, BorderLayout.NORTH); // Name login

        JPanel panelCenterLogin = new JPanel();
        panelCenterLogin.setName("panelCenterLogin");
        panelCenterLogin.setLayout(new GridBagLayout());
        panelCenterLogin.setBackground(bgContent);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCenterLogin.add(new JLabel("Username"){
            {
                setName("userLogin");
                //setIcon(new ImageIcon("Icon/user.png"));
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterLogin.add(new JTextField(15){
            {
                setName("txtUserLogin");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        panelCenterLogin.add(new JLabel("Password"){
            {
                setName("passLogin");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterLogin.add(new JPasswordField(15){
            {
                setName("txtPassLogin");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterLogin.add(new JButton("Login"){
            {
                setName("btnLogin");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setBackground(new Color(0, 230, 0));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setHorizontalAlignment(CENTER);
                addActionListener(new Login());
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCenterLogin.add(new JLabel(){
            {
                setName("forgetPass");
                setText("<html><p style='padding-bottom: 5px;'>Quên mật khẩu</p></html>");
                setHorizontalAlignment(LEFT);
                setFont(new Font("", Font.BOLD, 15));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setForeground(Color.BLACK);
                        hideCenter(panelForget);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setForeground(Color.RED);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setForeground(Color.BLACK);
                    }

                });
            }
        }, gbc);
        panelContentLogin.add(panelCenterLogin, BorderLayout.CENTER);
        panelContentLogin.add(new JPanel(){
            {
                setBackground(bgContent);
                add(new JLabel(){
                    {
                        setName("createAccount");
                        setText("<html><p style='margin-bottom:5px;'> Tạo tài khoản </p></html>");
                        setHorizontalAlignment(CENTER);
                        setFont(new Font("", Font.BOLD, 15));
                        setCursor(new Cursor(Cursor.HAND_CURSOR));

                        addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                setForeground(Color.BLACK);
                                hideCenter(panelRegister);
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                setForeground(Color.RED);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setForeground(Color.BLACK);
                            }

                        });
                    }
                });
            }
        }, BorderLayout.SOUTH);
        return panelContentLogin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((JButton) AllComponent.getPanel(panelLogin, "btnLogin")).setEnabled(false);
        System.out.println("Button Hided");
        String username = ((JTextField) AllComponent.getPanel(panelLogin, "txtUserLogin")).getText();
        String password = ((JPasswordField) AllComponent.getPanel(panelLogin, "txtPassLogin")).getText();
        if (ErrorList.checkErrorLogin(username, password)) {
            JOptionPane.showMessageDialog(null, ErrorList.errors.values().toArray(), "Thông báo", 0);
            ((JTextField) AllComponent.getPanel(panelLogin, "txtPassLogin")).getText();
            ((JButton) AllComponent.getPanel(panelLogin, "btnLogin")).setEnabled(true);
            ErrorList.errors.clear();
        }
    }
}