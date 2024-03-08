package QuanLiThuvien.brain;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import QuanLiThuvien.thuvien.Info;
import QuanLiThuvien.thuvien.sach;
import QuanLiThuvien.thuvien.thuvien;
import QuanLiThuvien.thuvien.tusach;

public class Csdl extends thuvien {
    static File fileLogs = new File("TimeUpdateData.txt");
    static File file = new File("thuvien.txt");
    static File file2 = new File("test.txt");
    public static int sosach = 0;
    public static int sotusach = 0;
    public static void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (tusach tusach : tusachs) {
                fileWriter.append(tusach.nameTS +"/");
                for (sach sach : tusach.sachs) {
                    fileWriter.append(sach.name + "_");
                    for (Info info : sach.infomations) {
                        fileWriter.append(info.info[0]+ "_" + info.info[1] + "_" + info.info[2] + "_" + info.info[3] + "-");
                    }
                }
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            
        }
    }

    public static void readFile() {       
        for (tusach i : tusachs) {
            System.out.print(i.nameTS + "/");
            for (sach j : i.sachs) {
                System.out.print(j.name + ",");
            }
            System.out.print("\n");
        }

    }

    public static void updateData() {
        resetArrayList();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String dataScan = scanner.nextLine();
                String bookcase[] = dataScan.split("/");
                if (bookcase.length > 1) { //Thực thi khi có sách trong tủ
                    tusach tusach = new tusach();
                    tusach.setNameTS(bookcase[0]);
                    thuvien.addTS(tusach);
                    sotusach++;
                    String book[] = bookcase[1].split("-");
                    for (String countBook : book) {
                        String info[] = countBook.split("_");
                        //thêm sách vào tủ sách
                        sach sach = new sach();
                        sach.setBook(info[0]);
                        tusach.addS(sach);
                        sosach++;
                        //thêm thông của quyển sách
                        Info infoBook = new Info();
                        infoBook.setInfo(info[1], info[2], info[3], info[4]);
                        sach.addInfomation(infoBook);
                    }
                    
                } else { //Thực thi khi không có sách nào trong tủData/sach1_gt_sl_ct_link-sach2_gt_sl_ct_link
                    FunctionQLTV.setTS(bookcase[0]);
                    sotusach++;
                }
                
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ResetFile() {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            tusachs.removeAll(tusachs);
            fw.close();
            updateData();
            System.out.println("Success");
            readFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    private static void resetArrayList() {
        tusachs.clear();
        sosach = 0;
        sotusach = 0;
    }

    public static void updateTime(String nameUpdate) {
        try {
            FileWriter fileWriter = new FileWriter(fileLogs , true);
            fileWriter.append(TimeUpdateData.timeUpdateData(2) +"-->"+ nameUpdate +"\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private static Object find(String bookcase, String book, int findNumber) {
        for (tusach tusach : tusachs) {
            if (tusach.nameTS.equals(bookcase) && findNumber == 0) {
                return tusach;
            } else if(tusach.nameTS.equals(bookcase)) {
                for (sach sach : tusach.sachs) {
                    if (sach.name.equals(book) && findNumber == 1) {
                        return sach;
                    }
                }
            }  
        }
        return "Không có thông tin";
    }

    public static tusach getBookCase(String bookcase) {
        return (tusach) find(bookcase, null,0);
    }

    public static sach getBook(String Bookcase, String book) {
        return ((sach) find(Bookcase, book, 1));
    }

    //hàm xóa thông tin
    static int index = -1;
    public static void setNumberDelete(Object data, ArrayList<?> ArrayList) {
        int indexData = ArrayList.indexOf(data);
        index = indexData;
    }
    
    public static int getNumberdelete() {
        return index;
    }

   
}
