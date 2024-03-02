package QuanLiThuvien.thuvien;

public class Info {
    public String[] info;

    public void setInfo(String money, String countBook, String content, String fileImage) {
        String[] info = {money, countBook, content, fileImage};
        this.info = info;
    }
    
}