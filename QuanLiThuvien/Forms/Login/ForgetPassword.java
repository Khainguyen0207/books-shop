package QuanLiThuvien.Forms.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SendMail;


/**
 * Forget
 */
public class ForgetPassword extends Login {
    public static JPanel forgetPassword() {
        panelForget = new JPanel();
        panelForget.setName("panelForget");
        panelForget.setBackground(new Color(204,255,255));
        panelForget.setBorder(BorderFactory.createEmptyBorder(100, 70, 150, 70));
        panelForget.setLayout(new BorderLayout());

        JPanel panelContentForget = new JPanel();
        panelContentForget.setBackground(new Color(0, 239, 253));
        panelContentForget.setName("panelContentForget");
        panelContentForget.setBorder(null);
        panelContentForget.setLayout(new BorderLayout());

        JPanel panelCenterForget = new JPanel();
        panelCenterForget.setBackground(panelContentForget.getBackground());
        panelCenterForget.setName("panelCenterForget");
        panelCenterForget.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCenterForget.add(new JLabel("Email"){
            {
                setHorizontalAlignment(LEFT);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("labelEmail");
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCenterForget.add(new JTextField(20){
            {
                setHorizontalAlignment(LEFT);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("txtEmail");
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCenterForget.add(new JButton("Send"){
            {
                setBackground(Color.YELLOW);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setHorizontalAlignment(CENTER);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("btnMail");
                addActionListener(new ForgetPassword());
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCenterForget.add(new JLabel("Code"){
            {
                setHorizontalAlignment(LEFT);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("labelCode");
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;

        panelCenterForget.add(new JTextField(20){
            {
                setEditable(false);
                setHorizontalAlignment(LEFT);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("txtCode");
            }
        }, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        panelCenterForget.add(new JButton("Send"){
            {
                setBackground(Color.YELLOW);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setEnabled(false);
                setHorizontalAlignment(CENTER);
                setFont(new Font("Maiandra GD", Font.BOLD, 15));
                setName("btnCode");
                addActionListener(new ForgetPassword());
            }
        }, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelCenterForget.add(new JLabel("Password"){
            {
                setVisible(false);
                setHorizontalAlignment(LEFT);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("labelPass");
            }
        }, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;

        panelCenterForget.add(new JTextField(15){
            {
                setVisible(false);
                setHorizontalAlignment(LEFT);
                setFont(new Font("Maiandra GD", Font.BOLD, 20));
                setName("txtPass");
            }
        }, gbc);


        gbc.gridx = 1;
        gbc.gridy = 5;
        panelCenterForget.add(new JButton("Change"){
            {
                setBackground(Color.GREEN);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setVisible(false);
                setHorizontalAlignment(CENTER);
                setFont(new Font("Maiandra GD", Font.BOLD, 15));
                setName("btnChangePass");
                addActionListener(new ForgetPassword());
            }
        }, gbc);

        JPanel panelHeader = new JPanel();
        panelHeader.setName("panelHeader");
        panelHeader.setBackground(panelContentForget.getBackground());
        panelHeader.add(new JLabel("Forget Password", JLabel.CENTER){
            {
                setName("titleForget");
                setFont(new Font("Maiandra GD", Font.BOLD, 25));
            }
        });

        JPanel panelFooter = new JPanel();
        panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelFooter.setName("panelFooter");
        panelFooter.setBackground(panelContentForget.getBackground());
        panelFooter.add(new JLabel("<html><p style='margin-bottom:10px;'>Đăng nhập</p></html>"){
            {
                setName("login");
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
        panelFooter.add(new JLabel("<html><p style='margin-bottom:10px;'>Tạo tài khoản</p></html>"){
            {
                setName("linkregister");
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
        
        panelContentForget.add(panelHeader, BorderLayout.NORTH);
        panelContentForget.add(panelCenterForget, BorderLayout.CENTER);
        panelContentForget.add(panelFooter, BorderLayout.SOUTH);
        panelForget.add(panelContentForget);
        return panelForget;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getName();
        String mailto = ((JTextField) AllComponent.getPanel(panelForget, "txtEmail")).getText();
        if (name.equals("btnMail")) {
            if (ErrorList.checkMailForget(mailto)) {
                JOptionPane.showMessageDialog(null, ErrorList.errors.get("mail"));
                ErrorList.errors.clear();
            } else {
                panelForget.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                ((JTextField) AllComponent.getPanel(panelForget, "txtEmail")).setEditable(false);
                ((JTextField) AllComponent.getPanel(panelForget, "txtCode")).setEditable(true);
                AllComponent.getPanel(panelForget, "btnCode").setEnabled(true);
                eventButton buttonEvent = new eventButton(button);
                buttonEvent.start();
                btnSendMail sendMail = new btnSendMail(mailto);
                sendMail.start();
                panelForget.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        } else if (name.equals("btnCode")) {
            String codeInput = ((JTextField) AllComponent.getPanel(panelForget, "txtCode")).getText();
            if (ErrorList.checkCodeForget(codeInput)) {
                JOptionPane.showMessageDialog(null, ErrorList.errors.get("code"));
                ErrorList.errors.clear();
            } else {
                AllComponent.getPanel(panelForget, "btnMail").setVisible(false);
                ((JTextField) AllComponent.getPanel(panelForget, "txtEmail")).setEditable(false);
                ((JTextField) AllComponent.getPanel(panelForget, "txtCode")).setEditable(false);
                AllComponent.getPanel(panelForget, "btnCode").setEnabled(false);
                AllComponent.getPanel(panelForget, "txtPass").setVisible(true);
                AllComponent.getPanel(panelForget, "btnChangePass").setVisible(true);
                AllComponent.getPanel(panelForget, "labelPass").setVisible(true);
            }
        } else {
            String password = ((JTextField) AllComponent.getPanel(panelForget, "txtPass")).getText();
            if (!ErrorList.checkPasswordOld(password)) {
                JOptionPane.showMessageDialog(null, ErrorList.errors.values().toArray());
                ErrorList.errors.clear();
            } else {
                chanegPassword change = new chanegPassword(password, mailto);
                change.start();
                JOptionPane.showMessageDialog(null, "Đổi thành công");
                panelForget = forgetPassword();
                hideCenter(panelLogin);
            }
            
        }

    }
}

class chanegPassword extends Thread{
    String password;
    String email;
    chanegPassword(String pass, String email) {
        this.password = pass;
        this.email = email;
    }

    @Override
    public void run() {
        UserModel.updateInfo(password, email);
    }
}


class btnSendMail extends Thread{
    private final String mailto;

    btnSendMail(String mail) {
        this.mailto = mail;
    }

    @Override
    public void run() {
        SendMail.sendMail(mailto);
    }

}

class eventButton extends Thread{
    private final JButton button;
    eventButton(JButton btn) {
        this.button = btn;
    }

    @Override
    public void run() {
        for(int i = 60; i> 0; i--) {
            button.setEnabled(false);
            button.setText("Send to: " + i + "s");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            button.setText("Send to");
            button.setEnabled(true);
        }
    }
}

