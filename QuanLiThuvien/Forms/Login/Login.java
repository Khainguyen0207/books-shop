package QuanLiThuvien.Forms.Login;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import QuanLiThuvien.brain.AllComponent;

public class Login extends Form_Login {
    //Login
    public static JPanel setPanelLogin() {
        panelLogin.setName("panelLogin");
        panelLogin.setBackground(new Color(204,255,255));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(100, 150, 150, 150));
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        JPanel panelContentLogin = new JPanel();
        panelContentLogin.setName("panelContentLogin");
        panelContentLogin.setLayout(new BorderLayout());
        panelContentLogin.setBackground(new Color(0, 239, 253));
        panelContentLogin.add(new JLabel("Login"){
            { 
                setName("loginText");
                setFont(new Font("Maiandra GD", Font.BOLD, 40));
                setHorizontalAlignment(CENTER);
                setBackground(panelContentLogin.getBackground());
            }
        }, BorderLayout.NORTH);
        JPanel panelCenterLogin = new JPanel();
        panelCenterLogin.setName("panelCenterLogin");
        panelCenterLogin.setLayout(new GridBagLayout());
        panelCenterLogin.setBackground(panelContentLogin.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCenterLogin.add(new JLabel("Username"){
            {
                setName("userLogin");
                //setIcon(new ImageIcon("QuanLiThuvien/Icon/user.png"));
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCenterLogin.add(new JTextField(15){
            {
                setName("txtUserLogin");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCenterLogin.add(new JLabel("Password"){
            {
                setName("passLogin");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCenterLogin.add(new JTextField(15){
            {
                setName("txtPassLogin");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);
        

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterLogin.add(new JButton("Login"){
            {
                setName("btnLogin");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setBackground(new Color(0, 230, 0));
                setCursor(new Cursor(12));
                setHorizontalAlignment(CENTER);
                addActionListener(new Login());
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelCenterLogin.add(new JLabel(){
            {
                setName("forgetPass");
                setText("<html><u style='' >Quên mật khẩu!</u></html>");
                setHorizontalAlignment(LEFT);
                setFont(new Font("", Font.BOLD, 15));
                setCursor(new Cursor(12));
                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        resetForm(ForgetPassword.forgetPassword());
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
        panelContentLogin.add(new JLabel(){
            {
                setName("createAccount");
                setText("<html><p style='margin-bottom:10px;text-decoration: underline;font-weight: 700;'> Tạo tài khoản! </p></html>");
                setHorizontalAlignment(CENTER);
                setFont(new Font("", Font.BOLD, 15));
                setCursor(new Cursor(12));

                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        resetForm(Register.setPanelRegister());
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
        }, BorderLayout.SOUTH);
        panelLogin.add(panelContentLogin);
        return panelLogin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((JButton) AllComponent.getPanel(panelLogin, "btnLogin")).setEnabled(false);
        String username = ((JTextField) AllComponent.getPanel(panelLogin, "txtUserLogin")).getText();
        String password = ((JTextField) AllComponent.getPanel(panelLogin, "txtPassLogin")).getText();
        if (ErrorList.checkErrorLogin(username, password)) {
            JOptionPane.showMessageDialog(null, ErrorList.errors.values().toArray(), "Thông báo", 0);
            ((JTextField) AllComponent.getPanel(panelLogin, "txtPassLogin")).getText();
            ((JButton) AllComponent.getPanel(panelLogin, "btnLogin")).setEnabled(true);
            ErrorList.errors.clear();
        }
    }
    
    public static void resetForm(JPanel panel) {
        panelLogin.removeAll();
        frame.remove(panelLogin);  
        frame.add(panel, BorderLayout.CENTER);
        frame.validate();
        frame.repaint();
    }
}