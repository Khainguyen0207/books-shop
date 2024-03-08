package QuanLiThuvien.brain;

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

    public static Image imageProcess(String urlIcon, int weight, int height) {
        Image image = Toolkit.getDefaultToolkit().createImage(urlIcon);
        Image scaleImage = image.getScaledInstance(weight, height, Image.SCALE_SMOOTH);
        return scaleImage;
    }
    
}
