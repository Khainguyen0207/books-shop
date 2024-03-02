package QuanLiThuvien.brain;

import java.awt.event.*;


import QuanLiThuvien.Forms.Admin.*;
import QuanLiThuvien.thuvien.*;


public class FunctionQLTV extends thuvien implements ActionListener {
    static int input = 0;
    String nameTSADD;
    
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void addBookCaseForArrayList(String nameBookCase) {
        setTS(nameBookCase);
        Csdl.sotusach++;
        AdminShopBook.updatePanelHideInfomationSetting("panelHideInfomationSetting");
        AdminQLKH.updateQLKH();
        Csdl.updateTime("AddBookCase");
    }

    public static void addBookForArrayList(String nameTSADD, String nameBook) {
        for(tusach i : tusachs) {
            if (i.nameTS.equals(nameTSADD)) {
                setBook(nameBook, i);
                Csdl.sosach++;
            }  
        }
        AdminShopBook.updatePanelHideInfomationSetting("panelHideInfomationSetting");
        AdminQLKH.updateQLKH();
        Csdl.updateTime("AddBook");
    }

    public static void setTS(String nameBookCase) {
        tusach ts = new tusach();
        ts.setNameTS(nameBookCase);
        addTS(ts);
    }

    public static void setBook(String nameBook, tusach tusach) {
        String info[] = nameBook.split("_");
        //thêm sách vào tủ sách
        sach sach = new sach();
        sach.setBook(info[0]);
        tusach.addS(sach);
        Csdl.sosach++;
        //thêm thông của quyển sách
        Info infoBook = new Info();
        infoBook.setInfo(info[1], info[2], info[3], info[4]);
        sach.addInfomation(infoBook);
    }
}