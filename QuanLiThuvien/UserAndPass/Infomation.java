package QuanLiThuvien.UserAndPass;

public class Infomation {
    public String name;
    public String birthday;
    public String address;
    
    public void setInfomation(String infomation) {
        String infoUser[] = infomation.split("-");
        this.name = infoUser[0];
        this.birthday = infoUser[1];
        this.address = infoUser[2];
    }
}
