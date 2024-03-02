import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import QuanLiThuvien.Forms.Login.ErrorList;
import QuanLiThuvien.brain.SendMail;

public class Mail extends SendMail {
    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.mail();
    }

    public void mail() {
        int result = 0;
        
        Object data[] = {
            "Email" , 
            new JTextField(){
                {
                    setPreferredSize(new Dimension(30, 30));
                    setName("txtMail");
                }
            }, 
            new JButton("Send Mail"){
                {
                    setFocusPainted(false);
                    setCursor(new Cursor(12));
                    setName("btnSend");
                }
            },
            new JLabel(){
                {
                    setText(" ");
                    setName("errorMail");
                }
            },
            "Code" , 
            new JTextField(10) {
                {
                    setName("txtCode");
                    setEditable(false);
                }
            } , new JLabel(){
                {
                    setText(" ");
                    setName("errorCode");
                }
            }, new JButton("Gửi lại mã mail") {
                {
                    setVisible(false);
                }
            }
        };
        String codeInput = ((JTextField) data[5]).getText();
        while (result == 0) {
            ((JButton) data[2]).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    String mailto = ((JTextField) data[1]).getText();
                    if (ErrorList.checkErrorRegister(mailto, mailto, mailto)) {
                        ((JLabel) data[3]).setText("Mail không đúng! Nhập lại");                  
                        ((JLabel) data[3]).setForeground(Color.red);
                        JOptionPane.setRootFrame(null);
                    } else {
                        ((JButton) data[2]).setVisible(false);
                        ((JLabel) data[3]).setVisible(false);
                        ((JTextField) data[1]).setEditable(false);
                        ((JTextField) data[5]).setEditable(true);
                        ((JButton) data[7]).setVisible(true);
                        if (SendMail.code == 0) {
                            sendMail(mailto);
                        }
                    }
                }
            });
            ((JButton) data[7]).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    synchronized(((JButton) data[7])) {
                        try {
                            System.out.println("ok");
                            ((JButton) data[7]).setText("Đợi 60s");
                            ((JButton) data[7]).setFocusable(false);
                            ((JButton) data[7]).wait(60000);
                        } catch (InterruptedException e1) {
                            System.out.println(e1);
                        }
                        ((JButton) data[7]).setFocusable(true);
                    }
                }
            });
            result = JOptionPane.showOptionDialog(null, data, "Quên mật khẩu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);     
            try {
                String mailto = ((JTextField) data[1]).getText();
                codeInput = ((JTextField) data[5]).getText();
                if (ErrorList.checkErrorRegister(mailto, mailto, mailto)) {
                    ((JLabel) data[3]).setText("Mail không đúng! Nhập lại");                  
                    ((JLabel) data[3]).setForeground(Color.red);
                } else if (Integer.valueOf(codeInput) != code) {
                    ((JLabel) data[6]).setText("Mã sai vui lòng thử lại");
                    ((JLabel) data[6]).setForeground(Color.red);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Mã phải là 4 số");
            }
        }
    }
}