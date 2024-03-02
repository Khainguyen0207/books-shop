package QuanLiThuvien.thuvien;

import java.util.ArrayList;

public class sach {
    public String name;
	public ArrayList<Info> infomations = new ArrayList<>();
	public void setBook(String name) {
		this.name = name;
		
	}

	public void addInfomation(Info info) {
		infomations.add(info);
	}

}
