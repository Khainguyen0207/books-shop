package QuanLiThuvien.Icon;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

public class SetIcon extends JFrame {
    public static void setIcon(JFrame jFrame, String fileIcon, int weight, int height) {
        
        try {
            //setIcon
            Image image = Toolkit.getDefaultToolkit().createImage(fileIcon);
            Image scaleImage = image.getScaledInstance(weight, height, Image.SCALE_SMOOTH);
            jFrame.setIconImage(scaleImage);
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
