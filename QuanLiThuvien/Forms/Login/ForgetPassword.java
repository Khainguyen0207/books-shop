package QuanLiThuvien.Forms.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SendMail;


/**
 * Forget
 */
public class ForgetPassword extends Login {
    public static JPanel forgetPassword() {
        panelForget.setName("panelForget");
        panelForget.setBackground(new Color(204,255,255));
        panelForget.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100));
        panelForget.setLayout(new BoxLayout(panelForget, BoxLayout.Y_AXIS));

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
                setCursor(new Cursor(12));
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
                setCursor(new Cursor(12));
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
                setCursor(new Cursor(12));
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
        panelFooter.setName("panelFooter");
        panelFooter.setBackground(panelContentForget.getBackground());
        panelFooter.add(new JLabel("<html> <u> <i>Đăng nhập</i></u></html>"){
            {
                setName("linklogin");
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
                    };

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setForeground(Color.BLACK);
                    }
                    
                });
            }
        });
        panelFooter.add(new JLabel("<html> <u> <i>Tạo tài khoản mới</i></u></html>"){
            {
                setName("linkregister");
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
                    };

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

    public static void resetForm(JPanel panel) {
        panelForget.removeAll();
        frame.remove(panelForget);
        frame.add(panel, BorderLayout.CENTER);
        frame.validate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getName();
        if (name.equals("btnMail")) {
            String mailto = ((JTextField) AllComponent.getPanel(panelForget, "txtEmail")).getText();
            if (ErrorList.checkMailForget(mailto)) {
                JOptionPane.showMessageDialog(null, ErrorList.errors.get("mail"));
                ErrorList.errors.clear();
            } else {
                panelForget.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                ((JTextField) AllComponent.getPanel(panelForget, "txtEmail")).setEditable(false);
                ((JButton) AllComponent.getPanel(panelForget, "btnMail")).setEnabled(false);
                ((JTextField) AllComponent.getPanel(panelForget, "txtCode")).setEditable(true);
                ((JButton) AllComponent.getPanel(panelForget, "btnCode")).setEnabled(true);
                
                SendMail.sendMail(mailto);
                panelForget.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        } else if (name.equals("btnCode")) {
            String codeInput = ((JTextField) AllComponent.getPanel(panelForget, "txtCode")).getText();
            if (ErrorList.checkCodeForget(codeInput)) {
                JOptionPane.showMessageDialog(null, ErrorList.errors.get("code"));
                ErrorList.errors.clear();
            } else {
                ((JTextField) AllComponent.getPanel(panelForget, "txtCode")).setEditable(false);
                ((JButton) AllComponent.getPanel(panelForget, "btnCode")).setEnabled(false);
                ((JTextField) AllComponent.getPanel(panelForget, "txtPass")).setVisible(true);
                ((JButton) AllComponent.getPanel(panelForget, "btnChangePass")).setVisible(true);
                ((JLabel) AllComponent.getPanel(panelForget, "labelPass")).setVisible(true);
            }
        } else {
            System.out.println("Đổi pass thành công");
            resetForm(Login.setPanelLogin());
        }

    }
}