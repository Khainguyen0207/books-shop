package QuanLiThuvien.Forms.User;

import java.util.Random;

public class RandomTextNotifition extends PanelContent{
    public static String randomTextNotifition(String notification) {
        Random random = new Random();
        int numberRandom = random.nextInt(3);
        switch (numberRandom) { //Có thể cải tiến sang đọc file hoặc dùng 1 String[] 
            case 0:
            {
                notification = "Để tôi đoán tâm trạng của bạn hôm nay nhé!";
                return notification;
            }
            case 1:
            {
                notification = "Chúc bạn 1 ngày tốt lành";
                return notification;
            }
            case 2:
            {
                notification = "Nếu không ai thương em thì tui sẽ thương em ^^";
                return notification;
            }
            case 3:
            {
                notification = "Hôm nay bạn thế nào?";
                return notification;
            }
        }
        return null;
    }
}
