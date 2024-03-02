package QuanLiThuvien.thuvien;

import java.util.ArrayList;

public class tusach {
    public String nameTS;
	public ArrayList<sach> sachs = new ArrayList<>();
	
	public void setNameTS(String name) {
		this.nameTS = name;
	}
	
	public void addS(sach sach) {
		this.sachs.add(sach);
		
	}

}
