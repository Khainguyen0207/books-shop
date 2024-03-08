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
import QuanLiThuvien.brain.TimeUpdateData;


public class Register extends Form_Login {
    //Register
    public static JPanel setPanelRegister() {
        panelRegister = new JPanel();
        panelRegister.setName("panel");
        panelRegister.setBackground(new Color(204,255,255));
        panelRegister.setLayout(new BorderLayout());
        JPanel panelContentRegister = setContentRegister();
        panelRegister.add(panelContentRegister, BorderLayout.CENTER);
        panelRegister.add(Form_Login.nameDesign(), BorderLayout.SOUTH);
        return panelRegister;
    }

    private static JPanel setContentRegister() {
        JPanel panelContentRegister = new JPanel();
        panelContentRegister.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        panelContentRegister.setName("panelContentRegister");
        panelContentRegister.setLayout(new BorderLayout());
        panelContentRegister.setBackground(new Color(204,255,255));
        Color bgContent = new Color(0, 239, 253);
        panelContentRegister.add(new JPanel(){
            {
                setBackground(bgContent);
                add(new JLabel(){
                    {
                        setText("Register");
                        setName("registerText");
                        setFont(new Font("Maiandra GD", Font.BOLD, 40));
                        setHorizontalAlignment(CENTER);
                        setBackground(getBackground());
                    }
                });
            }
        }, BorderLayout.NORTH);
        JPanel panelCenterRegister = new JPanel();
        panelCenterRegister.setName("panelCenterRegister");
        panelCenterRegister.setLayout(new GridBagLayout());
        panelCenterRegister.setBackground(bgContent);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy++;
        panelCenterRegister.add(new JLabel("Name"){
            {
                setName("registerName");
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
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtRegisterName");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);gbc.gridx = 0;
        gbc.gridy++;
        panelCenterRegister.add(new JLabel("Username"){
            {
                setName("userRegister");
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
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtUserRegister");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCenterRegister.add(new JLabel("Password"){
            {
                setName("passRegister");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtPassRegister");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCenterRegister.add(new JLabel("Email: "){
            {
                setName("email");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setHorizontalAlignment(LEFT);
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterRegister.add(new JTextField(15){
            {
                setName("txtEmail");
                setFont(new Font("", Font.BOLD, 20));
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.gridwidth = 2;
        panelCenterRegister.add(new JButton("Register"){
            {
                setName("btnRegister");
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setBackground(new Color(0, 230, 0));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setHorizontalAlignment(CENTER);
                addActionListener(new Register());
            }
        }, gbc);

        panelContentRegister.add(panelCenterRegister, BorderLayout.CENTER);
        panelContentRegister.add(new JPanel(){
            {
                setBackground(bgContent);
                add(new JLabel(){
                    {
                        setName("existAccount");
                        setText("<html><p style='padding-bottom: 5px;'> Đăng nhập </p></html>");
                        setHorizontalAlignment(CENTER);
                        setFont(new Font("", Font.BOLD, 15));
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                setForeground(Color.BLACK);
                                hideCenter(panelLogin);
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
        return panelContentRegister;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JTextField) AllComponent.getPanel(panelRegister, "txtRegisterName")).getText();
        String username = ((JTextField) AllComponent.getPanel(panelRegister, "txtUserRegister")).getText();
        String password = ((JTextField) AllComponent.getPanel(panelRegister, "txtPassRegister")).getText();
        String email = ((JTextField) AllComponent.getPanel(panelRegister, "txtEmail")).getText();
        if (ErrorList.checkErrorRegister(username, password, email, name)) {
            JOptionPane.showMessageDialog(null, ErrorList.errors.values().toArray(), "Thông báo", JOptionPane.ERROR_MESSAGE);
            ErrorList.errors.clear();
        } else {
            UserModel userModel = new UserModel("users");
            Map<String, String> register = new HashMap<>();
            register.put("name", name);
            register.put("username", username);
            register.put("password", password);
            register.put("email", email);
            register.put("create_day", TimeUpdateData.timeUpdateData(2));
            register.put("birthday", "Chưa cập nhật");
            userModel.create(register);
            Csdl.updateTime("User " + username +" created");
            CsdlAccount.numberUser++;
            JOptionPane.showMessageDialog(null, "Đăng kí thành công", "Thông báo", JOptionPane.ERROR_MESSAGE, new ImageIcon("Icon/TickSuccess.png"));
            hideCenter(panelLogin);
            resetRegister();
        }
    }

    private static void resetRegister() {
        ((JTextField) AllComponent.getPanel(panelRegister, "txtUserRegister")).setText("");
        ((JTextField) AllComponent.getPanel(panelRegister, "txtPassRegister")).setText("");
        ((JTextField) AllComponent.getPanel(panelRegister, "txtEmail")).setText("");
    }

}
