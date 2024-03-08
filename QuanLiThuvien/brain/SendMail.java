package QuanLiThuvien.brain;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
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
            //"<p>" + SendMail.content() + "</p>" +
            "<p> Mã xác thực của bạn là: " + ranNum + "\r\n" + "</p>" +
            "<img src=\"https://contechco.com.vn/images/products/2018/04/03/original/dich-vu-may-tinh_1522792397-copy-copy.jpg\" alt=\"fixbug\" style=\"width: 200px; height: 100px;\">" + 
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
}