package QuanLiThuvien.Forms.Admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import QuanLiThuvien.Icon.SetIcon;
import QuanLiThuvien.brain.Csdl;
import QuanLiThuvien.thuvien.sach;
import QuanLiThuvien.thuvien.thuvien;
import QuanLiThuvien.thuvien.tusach;

public class Edition_Form extends Admin{
    public static void editionFormAdmin(String nameTS, String string) {
        JFrame frameEdit = new JFrame("Name: " + nameTS);
        frameEdit.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                frame.setVisible(false);
            }
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setVisible(true);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(true);
            }
        });

        SetIcon.setIcon(frameEdit);
        JPanel panel_ts = new JPanel();

        panel_ts.setLayout(new BoxLayout(panel_ts, BoxLayout.X_AXIS));
        panel_ts.add(new JTextField(nameTS, 20){
            {
                setEditable(false);
            }
        });
        JPanel panel_data = new JPanel();
        panel_data.setLayout(new BoxLayout(panel_data, BoxLayout.Y_AXIS));
        for (tusach i : thuvien.tusachs) {
            if (i.nameTS.equals(nameTS)) {
                for (sach j : i.sachs) {
                    panel_data.add(new JTextField(j.name, 10){
                        {
                            setEditable(false);
                        }
                    });
                }
            }
        }
        frameEdit.setLayout(new BorderLayout());
        frameEdit.add(panel_ts, BorderLayout.WEST);
        frameEdit.add(panel_data, BorderLayout.CENTER);
        frameEdit.add(new JPanel(){
            {
                setLayout(new FlowLayout(FlowLayout.LEFT));
                add(new JButton("Lưu thay đổi"){
                    {
                        setCursor(new Cursor(12));
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Saved");
                                frameEdit.dispose();
                            }
                        });
                    }
                });

                add(new JButton("Hủy thay đổi"){
                    {
                        setCursor(new Cursor(12));
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Save fail");
                                frameEdit.dispose();
                            }
                        });
                    }
                });

                add(new JButton("Xóa tủ sách"){
                    {
                        setCursor(new Cursor(12));
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //chưa tối ưu để cập nhật sotusach và sosach
                                thuvien.tusachs.remove(Integer.valueOf(string).intValue() - 1);
                                Csdl.sotusach--;
                                AdminShopBook.updatePanelHideInfomationSetting("panelHideInfomationSetting");
                                AdminQLKH.updateQLKH();
                                frameEdit.dispose();
                            }
                        });
                    }
                });
            }
        }, BorderLayout.SOUTH);
        frameEdit.setLocationRelativeTo(null);
        frameEdit.pack();
        frameEdit.setVisible(true);
    }

   
}