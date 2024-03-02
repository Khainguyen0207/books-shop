package QuanLiThuvien.Icon;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

public class SetIcon extends JFrame {
    public static void setIcon(JFrame jFrame) {
        
        try {
            //setIcon
            String ImageIcon = "admin.png";
            ImageIcon icon = new ImageIcon(SetIcon.class.getResource(ImageIcon));
            jFrame.setIconImage(icon.getImage());
            //End_setIcon
        } catch (Exception e) {
           System.out.println("PNG null");
        }
    }

    public static void setIconButton(JButton button, String urlIcon) {
        Image image = Toolkit.getDefaultToolkit().createImage(urlIcon);
        Image scaleImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaleImage));
    }
    
}
