package QuanLiThuvien.Forms.Admin;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

import QuanLiThuvien.Forms.Login.ErrorList;
import QuanLiThuvien.Icon.SetIcon;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.Csdl;
import QuanLiThuvien.brain.FunctionQLTV;
import QuanLiThuvien.thuvien.*;


public class AdminShopBook extends Admin {
    private static String namelate = null;
    private static String nametable = "";
    private static String delete = "";
    private static ArrayList<Integer> arrRomove = new ArrayList<>();
    public static void panelAdminShopBook() {
        long bd = System.currentTimeMillis();
        panelCenterAdminShopBook.setName("panelCenterAdminShopBook");
        panelCenterAdminShopBook.setLayout(new BorderLayout());
        panelCenterAdminShopBook.add(panelButton(), BorderLayout.EAST);
        panelCenterAdminShopBook.add(panelFind(), BorderLayout.NORTH);
        panelCenterAdminShopBook.add(panelHideInfomationSetting(), BorderLayout.CENTER);  
        long kt = System.currentTimeMillis();
        System.out.println("AdminShopBook: " + (kt - bd));
    }

    private static JPanel panelFind() {      
        JPanel panelFind = new JPanel();
        panelFind.setName("panelFind");
        JPanel panelFindLeft = new JPanel();
        panelFindLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField txt = new JTextField( 20);
        txt.setName("txtFind");
        JLabel label = new JLabel("x"){
            {
                setName("labelX");
                setVisible(false);
                setCursor(new Cursor(12));
                setFont(new Font("", ALLBITS, 20));
                addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        txt.setText("");
                        setVisible(false);
                    };
                });
                
            }
        };
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!txt.getText().isEmpty() && !txt.getText().equals(" ") && e.getKeyCode() != KeyEvent.VK_CONTROL) {
                    label.setVisible(true);
                    txt.setText(txt.getText());
                } else {
                    label.setVisible(false);
                    txt.setText(txt.getText().trim());
                }
            };
        });
        panelFindLeft.setName("panelFindLeft");
        panelFindLeft.add(new JLabel("Search: "){
            {
                setName("search");
            }
        });
        panelFindLeft.add(txt);
        panelFindLeft.add(label);
        panelFindLeft.add(new JButton("Find"){
            {
                setFocusPainted(false);
                setCursor(new Cursor(12));
                setName("btnFind");
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        arrRomove.clear();
                        int a = 0;
                        for (tusach i : thuvien.tusachs) { 
                            if (!checkExisCharacter(i.nameTS.toLowerCase(), txt.getText().toLowerCase().trim())) {
                                setArrRemove(a);
                            }
                            a++;
                        }
                        updatePanelHideInfomationSetting("panelHideInfomationSetting");
                    }
                });
            }
        });
        panelFind.setLayout(new BorderLayout());
        panelFind.add(panelFindLeft, BorderLayout.WEST);
        panelFind.add(new JButton("Danh mục"){
            {
                setName("bookcaseClicked");
                setFont(new Font(null, 32, 15));
                setForeground(Color.BLACK);
                setVisible(true);
            }
        }, BorderLayout.EAST);

        return panelFind;
    }

    private static JPanel panelButton() {
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = new TitledBorder(border, "Chức năng", TitledBorder.CENTER, 0);
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        try {
            addButton(panelButton, "Bookcase", "QuanLiThuvien/Icon/addBookCase.png");
            addButton(panelButton, "Book", "QuanLiThuvien/Icon/AddBook.png");
            addButton(panelButton, "Back", "QuanLiThuvien/Icon/back.png");
            addButton(panelButton, "Delete", "QuanLiThuvien/Icon/delete.png");
        } catch (Exception e) {
            System.out.println("No find URL IconImage");
        }
        AllComponent.getPanel(panelButton, "Back").setVisible(hideBtnBackAndBook());
        AllComponent.getPanel(panelButton, "Book").setVisible(hideBtnBackAndBook());
        Boolean ad = hideBtnBackAndBook();
        AllComponent.getPanel(panelButton, "Bookcase").setVisible(!ad);
        panelButton.setName("panelButton");
        panelButton.setBackground(Color.lightGray);
        panelButton.setBorder(null);
        panelButton.setBorder(titledBorder);
        return panelButton;
    }

    public static JPanel panelHideInfomationSetting() {
        JPanel panelHideInfomation = new JPanel();
        panelHideInfomation.setLayout(new BoxLayout(panelHideInfomation, BoxLayout.Y_AXIS));
        panelHideInfomation.setBorder(null);
        panelHideInfomation.setName("panelHideInfomationSetting");
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setName("jScrollPane");
        jScrollPane.setViewportView(setHideTable());
        jScrollPane.setBorder(null);
        panelHideInfomation.add(jScrollPane);
        return panelHideInfomation;
    }

    private static JTable tableSettingBookcase() {
        JTable table = new JTable(Csdl.sotusach, 4);
        table.setName("tableBookCase");
        String data[] = {"STT", "Name", "Count Book", "Edit Data"};
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        model.setColumnCount(0);
        //setting data table
        addColumn(model, data);
        addRowTT(model, data);
        addNameTsUpTable(model);
        setRemoveRow(model);
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int rowChanged = e.getFirstRow();
                int column = e.getColumn();
                String nameChange = table.getValueAt(rowChanged, column).toString().trim();
                if (column == 1 && namelate != nameChange) {             
                    if (!Csdl.checkNoExis(nameChange) || namelate == nameChange || nameChange.isEmpty()) {
                        table.setValueAt(namelate, rowChanged, column);
                    } else {
                        System.out.println("Change: " + namelate + "->" + nameChange);
                        Csdl.getBookCase(namelate).nameTS = nameChange;
                        updatePanelHideInfomationSetting("panelHideInfomationSetting");
                    }
                }
            }
        });
        //setting table
        table.setModel(model);       
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setRowHeight(40);
        table.getColumnName(0);
        table.setFont(new Font("", Font.BOLD, 15));
        table.setEnabled(true);
        table.setAutoCreateRowSorter(true);
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setCursor(new Cursor(12)); //12: Hand_cursor
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numberColumnClicked = table.columnAtPoint(e.getPoint());
                int numberRowClicked = table.rowAtPoint(e.getPoint());
                if (numberColumnClicked != 3) {
                    delete = table.getValueAt(numberRowClicked, 1).toString();
                    tusach bookcase = Csdl.getBookCase(delete);
                    Csdl.setNumberdelete(bookcase, thuvien.tusachs);
                }
                if (numberColumnClicked == 1 || numberColumnClicked == 3) {
                    if (numberColumnClicked == 1) {
                        //System.out.println(numberRowClicked + "|" + numberColumnClicked + ": " + (model.getValueAt(numberRowClicked, numberColumnClicked)));
                        namelate = model.getValueAt(numberRowClicked, numberColumnClicked).toString();
                        
                    } else {
                        nametable = table.getValueAt(numberRowClicked, 1).toString();
                        ((JScrollPane) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelHideInfomationSetting"), "jScrollPane")).setViewportView(setTableBooks());;
                        updateStatusTable();
                        updatePanelHideInfomationSetting("panelHideInfomationSetting");
                        //AllComponent.getPanel(panelCenterAdminShopBook, "panelHideInfomationSetting").add(jScrollPane);
                    }
                }
            }
        });
        return table;
    }

    private static JTable setTableBooks() {
        JTable tableBooks = new JTable();
        tableBooks.setName("tableBooks");
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String data[] = {"STT", "Name", "Giá", "Số lượng", "Tình trạng", "Thông tin"};
        //set model
        try {
            addColumn(model, data);
            addRowBookTT(model, data);
            addNameBookUpTable(model);
        } catch (Exception e) {System.out.println(e);}
        //setTableBooks
        tableBooks.setAutoCreateRowSorter(true);
        tableBooks.setCursor(new Cursor(12));
        tableBooks.setFont(new Font("", 32, 15));
        tableBooks.setRowHeight(40);
        tableBooks.setSelectionBackground(Color.WHITE);
        tableBooks.setSelectionForeground(Color.BLACK);
        tableBooks.getTableHeader().setResizingAllowed(false);
        tableBooks.getTableHeader().setReorderingAllowed(false);
        tableBooks.setModel(model);
        tableBooks.setVisible(true);
        tableBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numberColumnClicked = tableBooks.columnAtPoint(e.getPoint());
                int numberRowClicked = tableBooks.rowAtPoint(e.getPoint());
                if (numberColumnClicked != 5) {
                    delete = tableBooks.getValueAt(numberRowClicked, 1).toString();
                    tusach bookcase = Csdl.getBookCase(nametable);
                    sach book = Csdl.getBook(bookcase.nameTS, delete);
                    Csdl.setNumberdelete(book, bookcase.sachs);
                }
                if (numberColumnClicked == 100 || numberColumnClicked == 5) {
                    if (numberColumnClicked == 1) {
                        //System.out.println(numberRowClicked + "|" + numberColumnClicked + ": " + (model.getValueAt(numberRowClicked, numberColumnClicked)));
                        namelate = model.getValueAt(numberRowClicked, numberColumnClicked).toString();
                        
                    } else {
                        System.out.println("Number: " + numberRowClicked +"|"+numberColumnClicked);
                        hideInfomationBook(tableBooks.getValueAt(numberRowClicked, 1).toString());
                    }
                }
            }
        });
        return tableBooks;
    }

    private static void addNameBookUpTable(DefaultTableModel model) {
        int a = 0;
        for (tusach tusach : thuvien.tusachs) {
            if (tusach.nameTS.equals(nametable)) {
                for (sach sach : tusach.sachs) {
                    for (Info info : sach.infomations) {
                        model.setValueAt(a, a, 0);
                        model.setValueAt(sach.name, a, 1);
                        model.setValueAt(info.info[0], a, 2);
                        model.setValueAt(info.info[1], a, 3);
                        if (Integer.valueOf(info.info[1]) == 0) {
                            model.setValueAt("Đã bán hết", a, 4);
                        } else {
                            model.setValueAt("Đang bán", a, 4);
                        }
                        JLabel label = new JLabel("<html><u><style='color:gray'><i>Infomation</i></u></html>", JLabel.CENTER);
                        label.setName("edit " + a);
                        model.setValueAt(label.getText(), a, 5);
                        a++;
                    }
                }
            }
        }
    }

    private static void addColumn(DefaultTableModel model, String[] data) {
        for (String i : data) {
            model.addColumn(i);
        }
        
    }

    private static void addRowBookTT(DefaultTableModel model, String data[]) {
        for (tusach tusach : thuvien.tusachs) {
            if (tusach.nameTS.equals(nametable)) {
                for(int i = 0; i < tusach.sachs.size(); i++) {
                    model.addRow(data);
                }
            }
        }
    }
    private static void setRemoveRow(DefaultTableModel model) {
        Collections.sort(arrRomove, Collections.reverseOrder());
        for (int i : arrRomove) {
            model.removeRow(i);
        }
    }

    private static void setArrRemove(int rowRemove) {
        arrRomove.add(rowRemove);
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

        button.addActionListener(new AdminShopBook());
        panel.add(button);
    }

    private static void addRowTT(DefaultTableModel model, String[] data) {
        data[2] = "0";
        for(int i = 1; i <= thuvien.tusachs.size(); i++) {
            model.addRow(data);
        }
        
    }

    private static void addNameTsUpTable(DefaultTableModel model) {
        int a = 0;
        for (tusach tusach : thuvien.tusachs) {
            model.setValueAt(a, a, 0);
            model.setValueAt(tusach.nameTS, a, 1);
            model.setValueAt(tusach.sachs.size(), a, 2);
            JLabel label = new JLabel("<html><u><style='color:red'><i>EDIT</i></u></html>", JLabel.CENTER);
            label.setName("edit " + a);
            model.setValueAt(label.getText(), a, 3);
            a++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String data = button.getText();

        if (data.equals("Bookcase")) {
            try {
                addBookCase();
            } catch (MalformedURLException e1) {}
            
        } else if (data.equals("Book")) {
            if (Csdl.sotusach == 0) {
                JOptionPane.showMessageDialog(null, "Hiện tại không có tủ sách để thêm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    addBook();
                } catch (MalformedURLException e1) {}
            }
        } else if (data.equals("Back")) {
            nametable = "";
            updateStatusTable();
            updatePanelHideInfomationSetting("panelHideInfomationSetting");
        } else if (data.equals("Delete")) {
            if (delete.equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tủ sách hoặc sách cần xóa");
            } else {
                int result = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa! - " + delete, "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("Deleted :" + delete);
                    if (hideBtnBackAndBook()) {
                        delete = "";
                        Csdl.getBookCase(nametable).sachs.remove(Csdl.getNumberdelete());
                    } else {
                        delete = "";
                        thuvien.tusachs.remove(Csdl.getNumberdelete());
                    }
                } else {
                    System.out.println("Delete fail: " + delete);
                    delete = "";
                }
                updatePanelHideInfomationSetting("panelHideInfomationSetting");
            }
        }
    }

    private void addBookCase() throws MalformedURLException {
        int result = 0;
        Object[] inputFields = {
            "Name Book Case:" , new JTextField(10),
        };

        do {
            ImageIcon icon = new ImageIcon(SetIcon.class.getResource("TickSuccess.png"));
            ImageIcon iconOption = new ImageIcon("QuanLiThuvien/Icon/AddBook.png");
            result = JOptionPane.showConfirmDialog(null, inputFields, "Thêm sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconOption);
            String nameBook = ((JTextField) inputFields[1]).getText();
            //System.out.println(result);
            if (result == JOptionPane.OK_OPTION) {
                if (nameBook.equals("") && result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;

                } else if (!Csdl.checkNoExis(nameBook)) {
                    JOptionPane.showMessageDialog(null, "Đã tồn tại tủ sách\n" + "Name BOOK: " + nameBook, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;
                    
                }

                FunctionQLTV.addBookCaseForArrayList(nameBook);
                JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE, icon);
                break;
       
            }

        } while (result == JOptionPane.OK_OPTION);

    }

    private void addBook() throws MalformedURLException {
        int result;
        Object[] inputFields = {
            "Name Book:" , new JTextField(20){
                {
                    setName("joptionPaneNameBook");
                }
            },
            "Giá tiền: ", new JTextField(20){
                {
                    setName("joptionPaneMoney");
                }
            },
            "Số lượng: ", new JTextField(20){
                {
                    setName("joptionPaneCountBook");
                }
            },
            "Nội dung: ", new JTextField(20){
                {
                    setName("joptionPaneContent");
                }
            },
            "Hình ảnh", new JPanel(){
                {
                    JPanel panel = this;
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    setName("imageSelect");
                    add(new JTextField("Chưa có ảnh", 20){
                        {
                            setEditable(false);
                            setName("pathImageSelect");
                        }
                    });
                    add(new JButton("Chọn"){
                        
                        {
                            setName("btnSelectImage");
                            addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFileChooser chooser = new JFileChooser("QuanLiThuvien/DataImage");
                                    chooser.setAcceptAllFileFilterUsed(false);
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & JBG", "png", "jbg");
                                    chooser.setFileFilter(filter);
                                    chooser.showOpenDialog(frame);
                                    for (Component c : panel.getComponents()) {
                                        if (c.getName().equals("pathImageSelect")) {
                                            ((JTextField) c).setText(chooser.getSelectedFile().getPath());
                                        }
                                    }
                                } 
                            }); 
                        }
                    });
                    
                }
            },
        };

        do {
            ImageIcon icon = new ImageIcon(SetIcon.class.getResource("TickSuccess.png"));
            ImageIcon iconOption1 = new ImageIcon("QuanLiThuvien/Icon/AddBook.png");
            result = JOptionPane.showConfirmDialog(null, inputFields, "Thêm sách cho danh mục " + nametable , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconOption1);
            String nameBook = ((JTextField) inputFields[1]).getText();
            String money = ((JTextField) inputFields[3]).getText();
            String countBook = ((JTextField) inputFields[5]).getText();
            String content = ((JTextField) inputFields[7]).getText();
            String path = "";
            for (Component c : ((JPanel) inputFields[9]).getComponents()) {
                if (c.getName().equals("pathImageSelect")) {
                    path = ((JTextField) c).getText();
                }
            }
            if (result == JOptionPane.OK_OPTION) {
                if (ErrorList.checkErrorAddBook(nameBook, money, countBook ,content, path) && result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, ErrorList.errors.get("addBook"), "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;

                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE, icon);
                    String databook = nameBook + "_" + money + "_"+ countBook + "_"+ content + "_"+ path + "_";
                    FunctionQLTV.addBookForArrayList(nametable.toString(), databook);
                    System.out.println(path);
                    break;

                }  
            }

        } while (result == JOptionPane.OK_OPTION);

    }

    @SuppressWarnings("unused")
    //add item for JComboBox
    private static void updateArea(JTextField txt, JComboBox<Object> box) {
        String content = txt.getText();
        System.out.println(content);
        box.removeAllItems();
        for (tusach i : thuvien.tusachs) {
            if (checkExisCharacter(i.nameTS.toLowerCase(), content.toLowerCase())) {
                box.addItem(i.nameTS);
            }
        }
        txt.setText(content);
    }

    private static Boolean checkExisCharacter(String nameTS, String text) {
        if (!nameTS.contains(text) && !text.equals("")) {
            return false;
        }
        return true;

    }

    public static void updatePanelHideInfomationSetting(String component) {
        delete = "";
        for (Component i : panelCenterAdminShopBook.getComponents()) {
            if (i.getName().equals(component)) {
                panelCenterAdminShopBook.remove(i);
            }
        }
        panelCenterAdminShopBook.add(panelHideInfomationSetting(), BorderLayout.CENTER);
        panelCenterAdminShopBook.validate();
        panelCenterAdminShopBook.repaint();
    }



    private static boolean hideBtnBackAndBook() {
        if (nametable.isEmpty()) {
            return false;
        }
        return true;
    }

    private static void updateStatusTable() {
        ((JButton) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelFind"), "bookcaseClicked")).setText("Danh mục " + nametable);
        ((JButton) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelButton"), "Back")).setVisible(hideBtnBackAndBook());
        ((JButton) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelButton"), "Book")).setVisible(hideBtnBackAndBook());
    }

    private static JTable setHideTable() {
        if (nametable.isEmpty()) {
            return tableSettingBookcase();
        }
        return setTableBooks();
    }

    private static void hideInfomationBook(String nameBookSelect) {
        for (tusach tusach  : thuvien.tusachs) {
            if (tusach.nameTS.equals(nametable)) {
                for (sach sach : tusach.sachs) {
                    if (sach.name.equals(nameBookSelect)) {
                        for (Info info : sach.infomations) {
                            try {
                                hideInfomation(info.info, nameBookSelect);
                            } catch (MalformedURLException e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void hideInfomation(String nameBook[], String book) throws MalformedURLException {
        int result;
        Object[] inputFields = {
            "Name Book:" , new JTextField(20){
                {
                    setText(book);
                    setName("joptionPaneNameBook");
                }
            },
            "Giá tiền: ", new JTextField(20){
                {
                    setText(nameBook[0]);
                    setName("joptionPaneMoney");
                }
            },
            "Số lượng: ", new JTextField(20){
                {
                    setText(nameBook[1]);
                    setName("joptionPaneCountBook");
                }
            },
            "Nội dung: ", new JTextField(20){
                {
                    setText(nameBook[2]);
                    setName("joptionPaneContent");
                }
            },
            "Hình ảnh", new JPanel(){
                {
                    JPanel panel = this;
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    setName("imageSelect");
                    add(new JTextField(20){
                        {
                            setText(nameBook[3]);
                            setEditable(false);
                            setName("pathImageSelect");
                        }
                    });
                    add(new JButton("Chọn"){
                        
                        {
                            setName("btnSelectImage");
                            addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFileChooser chooser = new JFileChooser("QuanLiThuvien/DataImage");
                                    chooser.setAcceptAllFileFilterUsed(false);
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & JBG", "png", "jbg");
                                    chooser.setFileFilter(filter);
                                    chooser.showOpenDialog(frame);
                                    for (Component c : panel.getComponents()) {
                                        if (c.getName().equals("pathImageSelect")) {
                                            ((JTextField) c).setText(chooser.getSelectedFile().getPath());
                                        }
                                    }
                                } 
                            }); 
                        }
                    });
                    
                }
            },
        };

        do {
            ImageIcon icon = new ImageIcon(SetIcon.class.getResource("TickSuccess.png"));
            ImageIcon iconOption1 = new ImageIcon("QuanLiThuvien/Icon/AddBook.png");
            result = JOptionPane.showConfirmDialog(null, inputFields, "Thông tin sách: " + nameBook[0] , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconOption1);
            String nameBookSelect = ((JTextField) inputFields[1]).getText();
            String money = ((JTextField) inputFields[3]).getText();
            String countBook = ((JTextField) inputFields[5]).getText();
            String content = ((JTextField) inputFields[7]).getText();
            String path = "";
            for (Component c : ((JPanel) inputFields[9]).getComponents()) {
                if (c.getName().equals("pathImageSelect")) {
                    path = ((JTextField) c).getText();
                }
            }
            if (result == JOptionPane.OK_OPTION) {
                if (ErrorList.checkErrorAddBook(nameBookSelect, money, countBook ,content, path) && result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, ErrorList.errors.get("addBook"), "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else {
                    String databook[] = {money , countBook , content , path};
                    System.out.println(nameBookSelect+ "|"+ book);
                    if (Arrays.equals(nameBook, databook) && nameBookSelect.equals(book)) {
                        System.out.println("T");
                    } else {    
                        JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE, icon);
                        change(databook, nameBookSelect, book);
                        updatePanelHideInfomationSetting("panelHideInfomationSetting");
                    }
                    break;

                }  
            }
        } while (result == JOptionPane.OK_OPTION);
    }

    public static void change(String databook[], String nameBookSelect, String book) {
        for (Info info : Csdl.getBook(nametable, book).infomations) {
            Csdl.getBook(nametable, book).name=  nameBookSelect;
            info.info = databook;
        }
    }
}