package QuanLiThuvien.Forms.Admin;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import QuanLiThuvien.Icon.SetIcon;

public class AdminDSKH extends Admin {
    static Boolean time = true;
    public static JPanel panelAdminDSKH() {
        panelCenterAdminDSKH.setLayout(new BorderLayout());
        panelCenterAdminDSKH.add(panelCenterDSKH(), BorderLayout.CENTER);
        panelCenterAdminDSKH.add(panelButton(), BorderLayout.EAST);
        return panelCenterAdminDSKH;
    }


    private static JPanel panelCenterDSKH() {
        JPanel panelHide = new JPanel();
        panelHide.setBorder(null);
        panelHide.setLayout(new BoxLayout(panelHide, BoxLayout.Y_AXIS));
        panelHide.setBackground(Color.LIGHT_GRAY);
        JScrollPane nutcuon = new JScrollPane();
        nutcuon.setName("nutcuonPanelHide");
        nutcuon.setViewportView(setTableDSKH());
        nutcuon.setBorder(null);
        panelHide.add(nutcuon);
        return panelHide;
    }
    private static JTable setTableDSKH() {
        JTable tableDSKH = new JTable();
        tableDSKH.setName("tableDSKH");
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String data[] = {"STT", "Username", "Tên người dùng" , "Hạng", "Thông tin"};
        addColumn(model, data);
        addRowBookTT(model, data);
        addNameBookUpTable(model);
        tableDSKH.setShowHorizontalLines(false);
        tableDSKH.setShowVerticalLines(false);
        tableDSKH.setRowHeight(40);
        tableDSKH.setFont(new Font("", Font.BOLD, 15));
        tableDSKH.setSelectionBackground(Color.WHITE);
        tableDSKH.setSelectionForeground(Color.BLACK);
        tableDSKH.setCursor(new Cursor(12));
        tableDSKH.setAutoCreateRowSorter(true);
        tableDSKH.getTableHeader().setReorderingAllowed(false);
        tableDSKH.getTableHeader().setResizingAllowed(false);
        tableDSKH.setModel(model);
        tableDSKH.setVisible(true);
        return tableDSKH;
    }

    private static JPanel panelButton() {
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = new TitledBorder(border, "Chức năng", TitledBorder.CENTER, 0);
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        try {
            addButton(panelButton, "Bookcase", "QuanLiThuvien/Icon/addBookCase.png");
            addButton(panelButton, "Book", "QuanLiThuvien/Icon/AddBook.png");
        } catch (Exception e) {
            System.out.println("No find URL IconImage");
        }
        panelButton.setName("panelButton");
        panelButton.setBackground(Color.lightGray);
        panelButton.setBorder(null);
        panelButton.setBorder(titledBorder);
        return panelButton;
    }

    private static void addButton(JPanel panel, String name, String url) {
        JButton button = new JButton();
        SetIcon.setIconButton(button, url);
        button.setText(name);
        button.setName(name);
        button.setFocusPainted(false); 
        button.setBackground(new Color(255,204,102));
        button.setMaximumSize(new Dimension(500, 40));
        button.setCursor(new Cursor(12));
        button.addMouseListener(new MouseAdapter() {
           
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255,204,102));
            }
        });

        button.addActionListener(new AdminDSKH());
        panel.add(button);
    }

    private static void addColumn(DefaultTableModel model, String data[]) {
        for (String string : data) {
            model.addColumn(string);
        }
    }

    private static void addRowBookTT(DefaultTableModel model, String data[]) {
        model.addRow(data);
    }

    private static void addNameBookUpTable(DefaultTableModel model) {
        model.setValueAt("0", 0, 0);
        model.setValueAt("NTK", 0, 1);
        model.setValueAt("Trọng Khải", 0, 2);
        model.setValueAt("Kim Cương", 0, 3);
        JLabel label = new JLabel("<html> <u> Infomation </u> </html>");
        label.setName("infomationAccount");
        model.setValueAt(label.getText(), 0, 4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Ch update");
    }

}
