package QuanLiThuvien.brain;

import java.time.LocalDateTime;

public class TimeUpdateData {
    public static String timeUpdateData(int getTime) {
        LocalDateTime local = LocalDateTime.now();
        String day = local.getDayOfMonth()  +"/"+ local.getMonth().getValue() + "/" + local.getYear();
        String time = local.getHour() +":"+ local.getMinute() +":"+ local.getSecond();
        String dayAndTime = day +"  "+ time;
        switch (getTime) {
            case 0:
                return time;
            case 1:
                return day;
            case 2:
                return dayAndTime;
            default: System.out.println("Lỗi getdata không hợp lệ"); return null;
        }
    }
}
