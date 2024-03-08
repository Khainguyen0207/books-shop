package QuanLiThuvien.Forms.Login;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.UserAndPass.CsdlAccount;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.Csdl;


public class Register extends Form_Login {
    //Register
    public static JPanel setPanelRegister() {
        panelRegister.setName("panel");
        panelRegister.setBackground(new Color(204,255,255));
        panelRegister.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100));
        panelRegister.setLayout(new BoxLayout(panelRegister, BoxLayout.Y_AXIS));
        JPanel panelContentRegister = new JPanel();
        panelContentRegister.setName("panelContentRegister");
        panelContentRegister.setLayout(new BorderLayout());
        panelContentRegister.setBackground(new Color(0, 239, 253));
        panelContentRegister.add(new JLabel("Register"){
            { 
                setName("registerText");
                setFont(new Font("Maiandra GD", Font.BOLD, 40));
                setHorizontalAlignment(CENTER);
                setBackground(panelContentRegister.getBackground());
            }
        }, BorderLayout.NORTH);
        JPanel panelCenterRegister = new JPanel();
        panelCenterRegister.setName("panelCenterRegister");
        panelCenterRegister.setLayout(new GridBagLayout());
        panelCenterRegister.setBackground(panelContentRegister.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCenterRegister.add(new JLabel("Username"){
            {
                setName("userRegister");
                //setIcon(new ImageIcon("QuanLiThuvien/Icon/user.png"));
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtUserRegister");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCenterRegister.add(new JLabel("Password"){
            {
                setName("passRegister");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtPassRegister");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCenterRegister.add(new JLabel("Email: "){
            {
                setName("email");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtEmail");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterRegister.add(new JButton("Register"){
            {
                setName("btnRegister");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setBackground(new Color(0, 230, 0));
                setCursor(new Cursor(12));
                setHorizontalAlignment(CENTER);
                addActionListener(new Register());
            }
        }, gbc);

        panelContentRegister.add(panelCenterRegister, BorderLayout.CENTER);
        panelContentRegister.add(new JLabel(){
            {
                setName("exisAccount");
                setText("<html><p style='margin-bottom:10px;text-decoration: underline;font-weight: 700;'> Đăng nhập! </p></html>");
                setHorizontalAlignment(CENTER);
                setFont(new Font("", Font.BOLD, 15));
                setCursor(new Cursor(12));
                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        resetForm(Login.setPanelLogin());
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
        panelRegister.add(panelContentRegister);
        return panelRegister;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = ((JTextField) AllComponent.getPanel(panelRegister, "txtUserRegister")).getText();
        String password = ((JTextField) AllComponent.getPanel(panelRegister, "txtPassRegister")).getText();
        String email = ((JTextField) AllComponent.getPanel(panelRegister, "txtEmail")).getText();
        if (ErrorList.checkErrorRegister(username, password, email)) {
            JOptionPane.showMessageDialog(null, ErrorList.errors.values().toArray(), "Thông báo", 0);
            ErrorList.errors.clear();
        } else {
            UserModel userModel = new UserModel();
            Map<String, String> register = new HashMap<>();
            register.put("username", username);
            register.put("password", password);
            register.put("email", email);
            userModel.create(register);
            Csdl.updateTime("User " + username +" created");
            CsdlAccount.numberUser++;
            JOptionPane.showMessageDialog(null, "Đăng kí thành công", "Thông báo", 0, new ImageIcon("QuanLiThuvien/Icon/TickSuccess.png"));
            resetForm(Login.setPanelLogin());
        }
    }

    public static void resetForm(JPanel panel) {
        panelRegister.removeAll();
        frame.remove(panelRegister);
        frame.add(panel, BorderLayout.CENTER);
        frame.validate();
        frame.repaint();
    }
}
