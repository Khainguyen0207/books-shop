package QuanLiThuvien.thuvien;

import java.util.ArrayList;

public class thuvien {
	public static ArrayList<tusach> tusachs = new ArrayList<>();
	
	public static void addTS(tusach tusachs) {
		thuvien.tusachs.add(tusachs);
	}
	
	public static ArrayList<String> getTS() {
		ArrayList<String> arr = new ArrayList<>();
		for(tusach i : tusachs) {
			arr.add(i.nameTS);
		}
		return arr;
	}
	
	public static boolean checkNoExis(String name) {
		for(tusach i : tusachs) {
			if(i.nameTS.toLowerCase().equals(name.toLowerCase())) {
				return false;
			}
		}
		
		return true;
	}

}



