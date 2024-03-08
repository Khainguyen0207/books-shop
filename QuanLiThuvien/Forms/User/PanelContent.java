package QuanLiThuvien.Forms.User;

import java.awt.*;
import javax.swing.*;

import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SetIcon;
import QuanLiThuvien.brain.TimeUpdateData;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.Map;
import java.util.TimerTask;

/**
 * PanelContent
 * Design by Trọng Khải github: khainguyen0207
 *
 */
public class PanelContent extends Form_User {
    private static int x = 0;
    public static String notification = "Giảm ngay 50% tất cả các loại sách hết ngày " + TimeUpdateData.timeUpdateData(1);
    private final static Color color = new Color(225, 240, 218);

    public static JPanel panelCenter() { //Thiết lập JPanel của tất cả sản phẩm
        JPanel panelProduct = new JPanel();
        panelProduct.setName("panelMainCenter");
        panelProduct.setLayout(new BorderLayout());
        JScrollPane sJScrollPane = new JScrollPane(panelHideProduct());
        sJScrollPane.setName("sJScrollPane");
        sJScrollPane.setBorder(null);
        sJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar verticalScrollBar = sJScrollPane.getVerticalScrollBar();
        verticalScrollBar.setName("verticalScrollBar");
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        panelProduct.add(sJScrollPane, BorderLayout.CENTER);
        panelProduct.setVisible(false);
        return panelProduct;
    } 

    private static JPanel panelHideProduct() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, 1));
        center.setName("panelcenter");
        center.add(panelNotification(), BorderLayout.NORTH);
        for (Map<String, String> data : UserModel.getData("categorys")) {
            center.add(panelCenter(data.get("name_category"), data.get("image_category"), data.get("id")));
        }
        return center;
    }

    private static JPanel panelCenter(String nameList, String img, String name) {
        JPanel panelCenter = new JPanel();
        panelCenter.setName("panelCenter");
        panelCenter.setLayout(new BorderLayout());
        panelCenter.add(listProduct(nameList, img, name), BorderLayout.NORTH);
        panelCenter.add(panelProduct(name), BorderLayout.CENTER);
        return panelCenter;
    }

    private static Component panelProduct(String ID)  {
        JPanel panel = new JPanel();
        panel.setName("oneProduct");
        panel.setBackground(color);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        int maxproduct = 0;
        int height = 0;
        for (Map<String, String> data : UserModel.setDataForTableBooks("products", Integer.parseInt(ID))) {
            String image = data.get("image");
            if (image == null) {
                image = "DataImage/image-update.png";
            }
            panel.add(product(Integer.parseInt(data.get("price")), data.get("name"), image));
            Dimension expectedDimension = panel.getPreferredSize(); // Lấy kích thước thực của 1 panel
            height = (int) expectedDimension.getHeight();
            maxproduct++;
        }
        if(maxproduct % 5 ==0) {
            maxproduct /= 5;
        } else {
            maxproduct /= 5;
            maxproduct +=1;
        }

        panel.setPreferredSize(new Dimension(1000, height * maxproduct));

        return panel;
    }

    private static JPanel listProduct(String nameList, String img, String name) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 235, 178)); // Đặt màu vẽ
                g.fillRoundRect(10, 5, 300, 30, 5, 5); 
            }
        };
        panel.setBackground(color);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(1000, 40));
        panel.setBorder(null);
        panel.setName("nameListProduct");
        panel.add(new JLabel(){
            {
                JLabel label = this;
                Thread thread = new Thread(new setimg(img, this, panel, 24, 24));
                queue.add(thread);
                setBorder(null);
                setName(name);
                setText("<html> <p style='font-size:14px;color:rgb(33, 128, 58);'> " + nameList +  "</p></html>");
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }
                });
            }
        });
        panel.setBounds(panel.getBounds());
        return panel;
    }

    private static JPanel panelNotification() { //Panel thông báo 
        x = 0;
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
                g.setColor(Color.red);
                g.drawString(notification, x, 20);
            }
        };
        panel.setBorder(null);
        panel.setBackground(Color.lightGray);
        panel.setName("notification");
        panel.setBackground(null);
        panel.setPreferredSize(new Dimension(100, 35));
        int delay = 15;
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                x++;
                if (x > panel.getWidth()) {
                    notification = RandomTextNotifition.randomTextNotifition(notification);
                    x = 0;
                }
                panel.repaint();
            }
        });
        timer.start();
        return panel;
    }

    private static JPanel product(int money, String nameProduct, String image) { // Tạo 1 sản phẩm trên panel
        JPanel panelCenter = new JPanel();
        panelCenter.setMinimumSize(new Dimension(100, 100));
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setName("panelProduct");
        panelCenter.setLayout(new BorderLayout());

        panelCenter.add(new JLabel(){
            {

                setName("imgdanhmuc");
                setPreferredSize(new Dimension(180, 120));
                Thread thread = new Thread(new setimg(image, this, panelProduct, 180, 120));
                queue.add(thread);
            }
        }, BorderLayout.CENTER);
        panelCenter.add(new JPanel(){
            {
                setName("noidungdanhmuc");
                //setPreferredSize(new Dimension(button.getWidth(), button.getHeight()) );
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                add(new JLabel("<html> " + nameProduct + "</html>"){
                    {   
                        setName("noidung");
                        setPreferredSize(new Dimension(180, 50));
                        setFont(new Font("Arial", Font.PLAIN, 14));
                    }
                });
                add(new JLabel(){
                    {
                        setName("giatien");
                        setText("Giá tiền: " + getCoin(money));
                    }
                });
                add(new JButton(){
                    {
                        setName("btnmua");
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        setText("Mua ngay");
                        setFont(new Font("Arial", Font.PLAIN, 14));
                        setBorderPainted(false);
                        setFocusPainted(false);
                        addActionListener(new ActionListener() {
                            final Object[] inputFields = {
                                "Địa chỉ", new JTextField(){
                                    {
                                        setName("inputAddress");
                                    }
                                },
                                "SĐT", new JTextField(){
                                    {
                                        setName("inputNumberPhone");
                                    }
                                },
                            };
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                if (checkLogin) {
                                    int result;
                                    result = JOptionPane.showConfirmDialog(null, inputFields, "Thông tin mua hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                                    if (result == JOptionPane.OK_OPTION) {
                                        System.out.println("Mua mặt hàng: " + nameProduct);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập để mua hàng");
                                }
                            }
                        });
                    }
                });
                add(new JButton(){
                    {
                        setName("btnthemvaogio");
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        setText("Thêm vào giỏ hàng");
                        setFont(new Font("Arial", Font.PLAIN, 14));
                        setBorderPainted(false);
                        setFocusPainted(false);
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                if (checkLogin) {
                                    TimerTask timerTask = new TimerTask() {
                                        @Override
                                        public void run() {
                                            String success = "Đã thêm " + nameProduct + " vào giỏ hàng";
                                            JOptionPane.showMessageDialog(null, success, "Thông báo",JOptionPane.INFORMATION_MESSAGE);
                                            if(ShopCart.productInShopcart.getComponent(0).getName().equals("shopcartEmpty")) {
                                                ShopCart.productInShopcart.remove(0);
                                            }
                                            ShopCart.productInShopcart.add(ShopCart.productShopCart(nameProduct, "DataImage/image-update.png", money));
                                        }
                                    };
                                    Thread thread = new Thread(timerTask);
                                    thread.start();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập để mua hàng");
                                }
                            }
                        });
                    }
                });
            }
        }, BorderLayout.SOUTH);
        // Dimension size = panelCenter.getPreferredSize();
        // System.out.println("product: " + size);
        return panelCenter;
    } 

    private static String getCoin(int number) {
        String num = Integer.valueOf(number).toString();
        if (num.length() > 3) {
            int dem = 0;
            String a = "";
            for (int i = num.length() - 1; i >= 0 ; i--) { // Thiết lập giá tiền có dấu phân tách "."
                a = a + num.charAt(i);
                dem++;
                if (dem == 3) {
                    a = a + ".";
                    dem = 0;
                }
            }

            String b = "";
            for (int i = a.length() - 1; i >= 0 ; i--) { //Đảo chuỗi giá tiền đã có dấu phân tách
                if (i == a.length() - 1 ) {
                    if (a.charAt(i) == '.') {
                        continue;
                    }
                }
                b = b + a.charAt(i);
            }

            //System.out.println("Đã đặt giá: " + b);
            return b + " VNĐ";
        }
        return number + " VNĐ";
    }
}

class setimg implements Runnable {
    String anh;
    JLabel label;
    JPanel panel;
    int width;
    int height;
    public setimg(String url, JLabel label, JPanel panel, int width, int height) {
        this.anh = url;
        this.label = label;
        this.panel = panel;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        label.setIcon(new ImageIcon(SetIcon.imageProcess(anh, width, height)));
        //int activeCount = Thread.activeCount();
        //System.out.println("Số lượng luồng đang chạy: " + activeCount); 
    }
}