package QuanLiThuvien.brain;

import java.time.LocalDateTime;

public class TimeUpdateData {
    public static String timeUpdateData() {
        LocalDateTime local = LocalDateTime.now();
        String day = local.getDayOfMonth()  +"/"+ local.getMonth().getValue() + "/" + local.getYear() ;
        String time = local.getHour() +":"+ local.getMinute() +":"+ local.getSecond();
        String dayAndTime = day +"  "+ time;
        return dayAndTime;
    }
}
