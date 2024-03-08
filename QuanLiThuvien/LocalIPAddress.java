package QuanLiThuvien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class LocalIPAddress {
    static Font font = new Font("JetBrains Mono", Font.PLAIN, 15);
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thanh Toan");
        frame.setSize(800,500);
        //frame.add(panelBuyProduct());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}