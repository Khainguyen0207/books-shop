package QuanLiThuvien.Forms.Admin;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

import QuanLiThuvien.Forms.Login.ErrorList;
import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.Csdl;
import QuanLiThuvien.brain.FunctionQLTV;
import QuanLiThuvien.brain.SetIcon;
import QuanLiThuvien.thuvien.*;


public class AdminShopBook extends Admin {
    private static String namelate = null;
    private static String IDDelete;
    private static String nametable = "";
    private static String IDTable = "";
    private static String delete = "";
    private static JPanel panelHideInfomationSetting;
    private static final ArrayList<Integer> arrRomove = new ArrayList<>();
    public static void panelAdminShopBook() {
        long bd = System.currentTimeMillis();
        nametable = "";
        panelCenterAdminShopBook.setName("panelCenterAdminShopBook");
        panelCenterAdminShopBook.setLayout(new BorderLayout());
        panelCenterAdminShopBook.add(panelButton(), BorderLayout.EAST);
        panelCenterAdminShopBook.add(panelFind(), BorderLayout.NORTH);
        panelHideInfomationSetting = panelHideInfomationSetting();
        panelCenterAdminShopBook.add(panelHideInfomationSetting, BorderLayout.CENTER);
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
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setFont(new Font("", Font.PLAIN, 20));
                addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        txt.setText("");
                        setVisible(false);
                    }
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
            }
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
                setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                setFont(new Font(null, Font.PLAIN, 15));
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
            addButton(panelButton, "Bookcase", "Icon/addBookCase.png");
            addButton(panelButton, "Book", "Icon/AddBook.png");
            addButton(panelButton, "Back", "Icon/back.png");
            addButton(panelButton, "Delete", "Icon/delete.png");
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
        String data[] = {"STT", "Name category", "Quantity", "Infomatin Books"};
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnCount(0);
        //setting data table
        addColumn(model, data);
        //Up infomation table
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
        table.setCursor(new Cursor(Cursor.HAND_CURSOR)); //12: Hand_cursor
        table.getColumnModel().getColumn(0).setMinWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(3).setMinWidth(55);
        table.getColumnModel().getColumn(3).setMaxWidth(60);
        table.getColumnModel().getColumn(2).setMinWidth(55);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
        int idWidth = table.getColumnModel().getColumn(0).getWidth();
        int passwordWidth = table.getColumnModel().getColumn(2).getWidth();
        int emailWidth = table.getColumnModel().getColumn(3).getWidth();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Sự kiện nhấn thay đổi thông tin table
                int numberColumnClicked = table.columnAtPoint(e.getPoint());
                int numberRowClicked = table.rowAtPoint(e.getPoint());
                if (numberColumnClicked == 1 || numberColumnClicked == 3) {
                    if (numberColumnClicked == 1) {
                        //System.out.println(numberRowClicked + "|" + numberColumnClicked + ": " + (model.getValueAt(numberRowClicked, numberColumnClicked)));
                        namelate = model.getValueAt(numberRowClicked, numberColumnClicked).toString();

                    } else {
                        nametable = table.getValueAt(numberRowClicked, 1).toString();
                        IDTable = table.getValueAt(numberRowClicked, 0).toString();
                        ((JScrollPane) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelHideInfomationSetting"), "jScrollPane")).setViewportView(setTableBooks());;
                        updatePanelHideInfomationSetting("panelHideInfomationSetting");
                        updateStatusTable();
                        //AllComponent.getPanel(panelCenterAdminShopBook, "panelHideInfomationSetting").add(jScrollPane);
                    }
                }
            }
        });
        return table;
    }

    private static JTable setTableBooks() {
        System.out.println("Bookcase");
        JTable tableBooks = new JTable();
        tableBooks.setName("tableBooks");
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] data = {"Mã sách", "Name", "Giá", "Số lượng", "Tình trạng", "Thông tin"};
        //set model
        try {
            addColumn(model, data);
            addRowBookTT(model);
            addNameBookUpTable(model);
        } catch (Exception e) {System.out.println(e);}
        //setTableBooks
        tableBooks.setAutoCreateRowSorter(true);
        tableBooks.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                    //System.out.println(numberRowClicked + "|" + numberColumnClicked + ": " + (model.getValueAt(numberRowClicked, numberColumnClicked)));
                    namelate = model.getValueAt(numberRowClicked, numberColumnClicked).toString();
                    delete = model.getValueAt(numberRowClicked, 1).toString();
                    IDDelete = model.getValueAt(numberRowClicked, 0).toString();
                }
                if (numberColumnClicked == 100 || numberColumnClicked == 5) {
                    if (numberColumnClicked == 1) {
                        //System.out.println(numberRowClicked + "|" + numberColumnClicked + ": " + (model.getValueAt(numberRowClicked, numberColumnClicked)));
                        namelate = model.getValueAt(numberRowClicked, numberColumnClicked).toString();
                    } else {
                        System.out.println("Number: " + numberRowClicked +"|"+numberColumnClicked);
                        hideInfomationBook(tableBooks.getValueAt(numberRowClicked, 0).toString());
                    }
                }
            }
        });
        return tableBooks;
    }

    private static void addNameBookUpTable(DefaultTableModel model) {

    }

    private static void addColumn(DefaultTableModel model, String[] data) {
        for (String i : data) {
            model.addColumn(i);

        }
    }

    private static void addRowBookTT(DefaultTableModel model) {
        for (Map<String, String> data : UserModel.setDataForTableBooks("products", Integer.parseInt(IDTable))) {
            String dataRow[] = {data.get("id"), data.get("name"),data.get("price"),  data.get("quantity"), "Chưa bán", "Infomation"};
            model.addRow(dataRow);
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
        button.setIcon(new ImageIcon(SetIcon.imageProcess(url, 25, 25)));
        button.setText(name);
        button.setName(name);
        button.setFocusPainted(false); 
        button.setBackground(new Color(255,204,102));
        button.setMaximumSize(new Dimension(500, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        for (Map<String, String> data : UserModel.setDataForTable("categorys")) {
            String dataRow[] = {data.get("id"), data.get("name_category"), String.valueOf(UserModel.getCountProduct(Integer.parseInt(data.get("id")))), "EDIT"};
            model.addRow(dataRow);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String data = button.getText();

        if (data.equals("Bookcase")) {
            try {
                addBookCase();
                updatePanelHideInfomationSetting("panelHideInfomationSetting");
            } catch (MalformedURLException e1) {}
            
        } else if (data.equals("Book")) {
            try {
                addBook();
                updatePanelHideInfomationSetting("panelHideInfomationSetting");
            } catch (MalformedURLException e1) {}
        } else if (data.equals("Back")) {
            nametable = "";
            updateStatusTable();
            updatePanelHideInfomationSetting("panelHideInfomationSetting");
        } else if (data.equals("Delete")) {
            if (delete.equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tủ sách hoặc sách cần xóa");
            } else {
                int result = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa! - " + delete, "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Name Table: " + nametable);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("Deleted :" + delete);
                    if (hideBtnBackAndBook()) {
                        System.out.println(delete);
                        if (UserModel.delete(Integer.parseInt(IDDelete))) {
                            System.out.println("Đã xóa sách: " + delete);
                        } else {
                            System.out.println("Xóa sách: " + delete + " thất bại!");
                        }
                        delete = "";
                    } else {
                        delete = "";
                        //Xóa danh mục sách
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
            ImageIcon icon = new ImageIcon("Icon/TickSuccess.png");
            ImageIcon iconOption = new ImageIcon("Icon/AddBook.png");
            result = JOptionPane.showConfirmDialog(null, inputFields, "Thêm sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconOption);
            String nameBook = ((JTextField) inputFields[1]).getText();
            if (result == JOptionPane.OK_OPTION) {
                if (nameBook.equals("") && result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;

                } else if (!Csdl.checkNoExis(nameBook)) {
                    JOptionPane.showMessageDialog(null, "Đã tồn tại tủ sách\n" + "Name BOOK: " + nameBook, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;
                    
                }

                //Thêm danh mục mới không trùng danh mục cũ
                Map<String, String> bookcaseData = new HashMap<>();
                bookcaseData.put("name_category", nameBook);
                System.out.println(UserModel.createData(bookcaseData, "categorys"));
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
                                    JFileChooser chooser = new JFileChooser("C:/");
                                    chooser.setAcceptAllFileFilterUsed(false);
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & JBG", "png", "jbg");
                                    chooser.setFileFilter(filter);

                                    int result = chooser.showOpenDialog(frame);
                                    if (result == JFileChooser.APPROVE_OPTION) {
                                        for (Component c : panel.getComponents()) {
                                            if (c.getName().equals("pathImageSelect")) {
                                                ((JTextField) c).setText(chooser.getSelectedFile().toString());
                                            }
                                        }
                                    } else {
                                        System.out.println("File selection cancelled.");
                                    }
                                } 
                            }); 
                        }
                    });
                    
                }
            },
        };

        do {
            ImageIcon icon = new ImageIcon("Icon/TickSuccess.png");
            ImageIcon iconOption1 = new ImageIcon("Icon/AddBook.png");
            result = JOptionPane.showConfirmDialog(null, inputFields, "Thêm sách cho danh mục " + nametable , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconOption1);
            String nameBook = ((JTextField) inputFields[1]).getText();
            String money = ((JTextField) inputFields[3]).getText();
            String countBook = ((JTextField) inputFields[5]).getText();
            String content = ((JTextField) inputFields[7]).getText();
            File path = null;
            for (Component c : ((JPanel) inputFields[9]).getComponents()) {
                if (c.getName().equals("pathImageSelect")) {
                    path = new File(((JTextField) c).getText());
                }
            }
            if (result == JOptionPane.OK_OPTION) {
                if (ErrorList.checkErrorAddBook(nameBook, money, countBook ,content, path.toString()) && result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, ErrorList.errors.get("addBook"), "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;

                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE, icon);
                    Map<String, String> bookData = new HashMap<>();
                    Path targetDirectory = Path.of("DataImage");
                    Path targetFile = targetDirectory.resolve(path.getName());
                    try {
                        Files.copy(path.toPath(), targetFile);
                    } catch (IOException ex) {}
                    String pathImage = targetDirectory.getFileName() + "/" + path.getName();
                    System.out.println(pathImage);
                    bookData.put("category_id", IDTable);
                    bookData.put("name", nameBook);
                    bookData.put("quantity", countBook);
                    bookData.put("price", money);
                    bookData.put("type_product", "sach");
                    bookData.put("image", pathImage);
                    bookData.put("description", content);
                    System.out.println(UserModel.createData(bookData, "products"));

                    break;
                }
            }

        } while (result == JOptionPane.OK_OPTION);

    }

    @SuppressWarnings("unused")
    //add item for JComboBox Chưa đc sài :()
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
        panelCenterAdminShopBook.removeAll();
        panelCenterAdminShopBook.add(panelButton(), BorderLayout.EAST);
        panelCenterAdminShopBook.add(panelFind(), BorderLayout.NORTH);
        panelCenterAdminShopBook.add(panelHideInfomationSetting(), BorderLayout.CENTER);
        panelCenterAdminShopBook.validate();
        panelCenterAdminShopBook.repaint();
        updateStatusTable();
    }



    private static boolean hideBtnBackAndBook() {
        if (nametable.isEmpty()) {
            return false;
        }
        return true;
    }

    private static void updateStatusTable() {
        System.out.println("Name Table: " + nametable);
        System.out.println("ID Table: " + IDTable);
        ((JButton) AllComponent.getPanel(panelCenterAdminShopBook, "bookcaseClicked")).setText("Danh mục: " + nametable);
        ((JButton) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelButton"), "Back")).setVisible(hideBtnBackAndBook());
        ((JButton) AllComponent.getPanel(AllComponent.getPanel(panelCenterAdminShopBook, "panelButton"), "Book")).setVisible(hideBtnBackAndBook());
    }

    private static JTable setHideTable() {
        if (nametable.isEmpty()) {
            return tableSettingBookcase();
        }
        return setTableBooks();
    }

    private static void hideInfomationBook(String IDBook) {

        for (Map<String, String> data : UserModel.getDataByID("products", Integer.parseInt(IDBook))) {
            try {
                hideInfomation(data);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private static void hideInfomation(Map<String, String> data) throws MalformedURLException {
        int result;
        Object[] inputFields = {
            "Name Book:" , new JTextField(20){
                {
                    setText(data.get("name"));
                    setName("joptionPaneNameBook");
                }
            },
            "Giá tiền: ", new JTextField(20){
                {
                    setText(data.get("price"));
                    setName("joptionPaneMoney");
                }
            },
            "Số lượng: ", new JTextField(20){
                {
                    setText(data.get("quantity"));
                    setName("joptionPaneCountBook");
                }
            },
            "Nội dung: ", new JTextField(20){
                {
                    setText(data.get("description"));
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
                            setText(data.get("image"));
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
                                    JFileChooser chooser = new JFileChooser("C:/");
                                    chooser.setAcceptAllFileFilterUsed(false);
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & JBG", "png", "jbg");
                                    chooser.setFileFilter(filter);
                                    int result = chooser.showOpenDialog(frame);
                                    if (result == JFileChooser.APPROVE_OPTION) {
                                        for (Component c : panel.getComponents()) {
                                            if (c.getName().equals("pathImageSelect")) {
                                                ((JTextField) c).setText(chooser.getSelectedFile().toString());
                                            }
                                        }
                                    } else {
                                        System.out.println("File selection cancelled.");
                                    }
                                } 
                            }); 
                        }
                    });
                    
                }
            },
        };

        do {
            ImageIcon icon = new ImageIcon("Icon/TickSuccess.png");
            ImageIcon iconOption1 = new ImageIcon("Icon/AddBook.png");
            result = JOptionPane.showConfirmDialog(null, inputFields, "Thông tin sách: " + data.get("name") , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconOption1);
            String nameBookSelect = ((JTextField) inputFields[1]).getText();
            String money = ((JTextField) inputFields[3]).getText();
            String countBook = ((JTextField) inputFields[5]).getText();
            String content = ((JTextField) inputFields[7]).getText();
            File path = null;
            for (Component c : ((JPanel) inputFields[9]).getComponents()) {
                if (c.getName().equals("pathImageSelect")) {
                    path = new File(((JTextField) c).getText());
                }
            }
            if (result == JOptionPane.OK_OPTION) {
                if (ErrorList.checkErrorAddBook(nameBookSelect, money, countBook ,content, path.toString()) && result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, ErrorList.errors.get("addBook"), "Thông báo", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else {
                    Path targetDirectory = Path.of("DataImage");
                    Path targetFile = targetDirectory.resolve(path.getName());
                    try {
                        Files.copy(path.toPath(), targetFile);
                    } catch (IOException ex) {}
                    String pathImage = targetDirectory.getFileName() + "/" + path.getName();
                    System.out.println(pathImage);
                    JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE, icon);
                    System.out.println(nameBookSelect +"|"+money+"|"+countBook+"|"+content+"|"+pathImage);
                    Map<String, String> bookDataUpdate = new HashMap<>();
                    bookDataUpdate.put("quantity", countBook);
                    bookDataUpdate.put("name", nameBookSelect);
                    bookDataUpdate.put("price", money);
                    bookDataUpdate.put("image", pathImage);
                    bookDataUpdate.put("description", content);
                    UserModel.updateData("products", bookDataUpdate, Integer.parseInt(data.get("id")));
                    updatePanelHideInfomationSetting("panelHideInfomationSetting");
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