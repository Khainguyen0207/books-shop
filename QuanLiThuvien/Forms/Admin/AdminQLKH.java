package QuanLiThuvien.Forms.Admin;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

import javax.swing.*;

import QuanLiThuvien.UserAndPass.CsdlAccount;
import QuanLiThuvien.thuvien.thuvien;
import QuanLiThuvien.thuvien.tusach;

public class AdminQLKH extends Admin {
    public static void panelAdminQLKH() {
        long bd = System.currentTimeMillis();
        panelCenterQLKH.setLayout(new BorderLayout());
        panelCenterQLKH.setName("panelCenterQLKH");
        panelCenterQLKH.setBackground(new Color(171, 235, 198));
        panelCenterQLKH.add(new JTextArea(""){
            {
                setName("TableText");
                setFont(new Font("", ALLBITS, 15));
                setEnabled(true);
                setEditable(false);
                setPreferredSize(new Dimension(1000,400));
            }
        }, BorderLayout.CENTER);
        panelCenterQLKH.add(panelInfomation(), BorderLayout.NORTH);
        long kt = System.currentTimeMillis();
        System.out.println("AdminQLKH: " + (kt - bd));
    }

    private static JPanel panelInfomation() {
        JPanel panelInfomation = new JPanel();
        panelInfomation.setName("panelInfomation");
        panelInfomation.setBackground(panelCenterQLKH.getBackground());
        panelInfomation.add(addLabel("Thành viên", CsdlAccount.numberUser , "./Icon/user.png"));
        panelInfomation.add(addLabel("Danh mục", thuvien.tusachs.size() , "Icon/bookshelf.png"));
        int numberBook = 0;
        for (tusach string : thuvien.tusachs) {
            numberBook += string.sachs.size();
        }
        panelInfomation.add(addLabel("Số lượng", numberBook, "Icon/books.png"));
        return panelInfomation;
    }
    
    private static JButton addLabel(String name, int quantity, String urlString) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 80));
        JPanel panel = new JPanel();
        button.add(panel);
        panel.setLayout(new FlowLayout());
        panel.setBackground(button.getBackground());
        panel.setPreferredSize(new Dimension(200, 80));
        panel.setBorder(BorderFactory.createLineBorder(button.getBackground()));
        JPanel panelIcon = new JPanel();
        panelIcon.setBackground(button.getBackground());
        try {
            panelIcon.add(new JLabel(){
                ImageIcon icon = new ImageIcon(urlString);
                Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                {
                    setIcon(new ImageIcon(image));
                }
            }, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println(e);
        }
        JPanel panelContent = new JPanel();
        panelContent.setBackground(button.getBackground());
        panelContent.setLayout(new BoxLayout(panelContent, 1));
        panelContent.add(new JLabel(name, JLabel.CENTER) {
            {
                setFont(new Font("Courier New", Font.BOLD, 16));
            }
        });
        panelContent.add(new JLabel("" + quantity, JLabel.CENTER){
            {
                setFont(new Font("Malgun Gothic", Font.BOLD, 20));
            }
        });
        panel.add(panelIcon);
        panel.add(panelContent);
        return button;
    }

    public static void updateQLKH() {
        for (Component i : panelCenterQLKH.getComponents()) {
            if (i.getName().equals("panelInfomation")) {
                panelCenterQLKH.remove(i);
                panelCenterQLKH.add(panelInfomation(), BorderLayout.NORTH);
                panelCenterQLKH.validate();
                panelCenterQLKH.repaint();
            }

            if (i.getName().equals("TableText")) {
                ((JTextArea) i).setText("");
                try{
                    File file = new File("TimeUpdateData.txt");
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        ((JTextArea) i).append(scanner.nextLine()+"\n");
                    }
                    scanner.close();
                } catch (Exception e){
                    
                }
            }
        }


    }

}