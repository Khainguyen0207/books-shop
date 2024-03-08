package QuanLiThuvien.Forms.User;

import javax.swing.*;

import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SetIcon;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

/**
 * Menu
 * Design by Trọng Khải github: khainguyen0207
 */
public class PanelMenu extends Form_User {
    public static JPanel panelMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setName("menu");
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setPreferredSize(new Dimension(0, 0));
        panelMenu.setBackground(Color.WHITE);
        JPanel listBook = new JPanel();
        listBook.setName("listbook");
        listBook.setBackground(panelMenu.getBackground());
        listBook.setLayout(new BoxLayout(listBook, BoxLayout.Y_AXIS));
        listBook.add(new JLabel(){
            {
                setText("<html> <p style='margin-bottom: 20px;'> Danh mục </p> </html>");
                setForeground(Color.red);
                setFont(new Font("Times New Roman", Font.PLAIN, 25));
            }
        });
        int size = 24;
        for (Map<String, String> data : UserModel.getData("categorys")) {
            listBook.add(setLabel(data.get("name_category"), data.get("id"), data.get("image_category"), size));
        }
//        listBook.add(setLabel("Trò chơi điện tử", "game", "Icon/game.png", size));
//        listBook.add(setLabel("Sách giáo khoa", "sgk","Icon/book-sc.png", size));
//        listBook.add(setLabel("Tiểu thuyết", "tieuthuyet", "Icon/novel.png", size));
//        listBook.add(setLabel("Truyện tranh", "animation", "Icon/comic.png", size));
//        listBook.add(setLabel("Ngôn ngữ lập trình", "IT", "Icon/code.png", size));
        
        JPanel close = new JPanel();
        close.setName("close");
        close.setLayout(new FlowLayout());
        close.setBackground(panelMenu.getBackground());
        close.add(new JLabel(){
            {
                setIcon(new ImageIcon(SetIcon.imageProcess("Icon/close1.png", 24, 24)));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setForeground(Color.RED);
                setFont(new Font("", Font.BOLD, 14));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panelMenu.setPreferredSize(new Dimension(0, 0));
                        panelMenu.updateUI();
                        //panelMenu.setVisible(false);
                    }
        
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setFont(new Font("", Font.PLAIN, 20));
                    }
        
                    @Override
                    public void mouseExited(MouseEvent e) {
                        setFont(new Font("", Font.PLAIN, 14));
                    }
                });
            }
        });
        panelMenu.add(listBook, BorderLayout.CENTER);
        panelMenu.add(close, BorderLayout.EAST);
        panelMenu.setVisible(true);
        return panelMenu;
    }
    private static JLabel setLabel(String text, String name, String image, int sizeImage) {
        JLabel label = new JLabel();
        label.setFont(new Font("", Font.PLAIN, 14));
        label.setName(name);
        label.setText(text);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setIcon(new ImageIcon(SetIcon.imageProcess(image, sizeImage, sizeImage)));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel label1 = (JLabel) AllComponent.getPanel(panelProduct, name);
                JScrollPane scrollPane = (JScrollPane) AllComponent.getPanel(panelProduct, "sJScrollPane");
                scrollPane.getViewport().setViewPosition(new Point(0,0));
                JLabel st = (JLabel) AllComponent.getPanel(panelProduct, "1"); //Sản phẩm game đầu tiên
                int data = label1.getLocationOnScreen().y;
                //JPanel panel = (JPanel) AllComponent.getPanel(panelProduct, "notification"); panel.getPreferredSize().height; // lướt tới giá tr đầu trang tìm kiếm
                scrollPane.getViewport().setViewPosition(new Point(0,data - st.getLocationOnScreen().y));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setFont(new Font("", Font.PLAIN, 16));
                label.setForeground(new Color(0, 127, 115));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setFont(new Font("", Font.PLAIN, 14));
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    
}