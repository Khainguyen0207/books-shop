package QuanLiThuvien.brain;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * SendMail
 * Designer by Trọng Khải
 */
public class SendMail {
    public static int code;
    public static void sendMail(String mailto) {
        final String from = "cudaimst1@gmail.com";
        final String password = "uyreflopvmumzopr";
        final String to = mailto;
        
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        //Dữ liệu mail
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Random rand = new Random();
        int ranNum = rand.nextInt(8999) + 1000;
        code = ranNum;
        System.out.println(code);
        Session session = Session.getInstance(properties, auth);
        MimeMessage msg = new MimeMessage(session);        
        try {
            msg.addHeader("Content-Type", "text/html; charset=UTF-8");
            msg.setContent("<!DOCTYPE html>\r\n" +
            "<html>\r\n" +
            "<body> \r\n" +
            "<h1>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</h1>\r\n" +
            "<p>" + SendMail.content() + "</p>" +
            "<img src=\"https://contechco.com.vn/images/products/2018/04/03/original/dich-vu-may-tinh_1522792397-copy-copy.jpg\" alt=\"fixbug\" style=\"width: 200px; height: 100px;\">" + 
            "<p> Mã xác thực của bạn là: " + ranNum + "\r\n" +
            "</body>\r\n" + 
            "</html>", "text/html; charset=UTF-8");
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("Thư từ Nguyễn Trọng Khải - IP:" + ranNum, "UTF-8");
            msg.setSentDate(new Date());
            msg.setReplyTo(InternetAddress.parse("tkhai12386@gmail.com"));
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static String content() {
        String a = "Tôi vô cùng xúc động khi nhận được món quà ý nghĩa mà anh đã dành tặng cho tôi nhân dịp tết. Món quà thực sự là một bất ngờ tuyệt vời và khiến tôi cảm thấy vô cùng hạnh phúc. \r\n\n"
                    + "Tôi rất trân trọng tấm lòng của anh khi đã dành thời gian và công sức để lựa chọn món quà phù hợp với sở thích của em/tôi. Món quà này không chỉ mang giá trị vật chất mà còn thể hiện sự quan tâm, yêu thương và tình cảm chân thành của anh dành cho tôi. \r\n"            
                    + "Tôi hứa sẽ luôn trân trọng và gìn giữ món quà này như một kỷ niệm đẹp về tình bạn/tình cảm của chúng ta. Tôi xin gửi lời cảm ơn chân thành nhất đến anh vì món quà đầy ý nghĩa này. \r\n";      
        return a;
    }
}