package QuanLiThuvien.Forms.User;

import QuanLiThuvien.brain.AllComponent;
import QuanLiThuvien.brain.SetIcon;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.concurrent.Flow;

public class ShopCart {
    public static int numberProductInShopcart;
    static final Color color = Color.WHITE;
    static final Font font = new Font("JetBrains Mono", Font.PLAIN, 15);
    static JPanel payProduct;
    public static JPanel productInShopcart;
    static int totalPayment = 0;
    public static JPanel shopCart() {
        JPanel shopCart = new JPanel();
        shopCart.setName("shopcart");
        shopCart.setBackground(color);
        shopCart.setLayout(new BorderLayout());
        shopCart.add(new JLabel() {
            {
                setHorizontalAlignment(JLabel.LEFT);
                setText("<html><p style='margin: 10px;'>Giỏ hàng</p></html>");
                setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
            }
        }, BorderLayout.NORTH);
        shopCart.add(panelShopCart(), BorderLayout.CENTER);
        return shopCart;
    }

    public static JPanel panelShopCart() {
        System.out.println("Test: ");
        JPanel panel = new JPanel();
        panel.setName("panelShopCart");
        panel.setLayout(new BorderLayout());
        panel.setBorder(null);
        //Load tất cả sản phẩm đã thêm vào giỏ hàng
        productInShopcart = productsShopCart();
        payProduct = payProduct();


        //Tạo 1 nút cuộn
        JScrollPane pane = new JScrollPane(productInShopcart);
        pane.setBorder(null);
        JScrollBar verticalScrollBar = pane.getVerticalScrollBar();
        verticalScrollBar.setName("verticalScrollBar");
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        pane.add(verticalScrollBar);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        if (productInShopcart.getComponentCount() == 0) {
            productInShopcart.setBackground(Color.WHITE);
            productInShopcart.add(new JLabel("Giỏ hàng trống", JLabel.CENTER) {
                {
                    setName("shopcartEmpty");
                    setPreferredSize(new Dimension(500, 500));
                    setFont(font);
                }
            });
        }
        panel.add(pane,BorderLayout.WEST);
        panel.add(payProduct, BorderLayout.CENTER);
        System.out.println(payProduct.getPreferredSize());
        return panel;
    }

    private static JPanel payProduct() {
        Color bgPay = new Color(196, 228, 255);
        payProduct = new JPanel();
        payProduct.setLayout(new BorderLayout());
        payProduct.setBackground(bgPay);
        payProduct.setName("payProduct");
        payProduct.add(new JLabel("", JLabel.CENTER){
            {
                setText("<html> <p style='text-align: center;'> Hóa đơn </p> " +
                        "<p style='text-align: center;'>───────────────────────────────────</p> " +
                        "</html>");
                setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
            }
        }, BorderLayout.NORTH);
        payProduct.add(new JPanel(){
            {
                setBackground(bgPay);
                setLayout(new BoxLayout(this, 1));
                setName("productbill");
                add(new JTable(){
                    {
                        setName("tableBill");
                        DefaultTableModel model = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        String tittle[] = {"Tên sản phẩm", "SL", "Thành tiền"};
                        for (String string : tittle) {
                            model.addColumn(string);

                        }
                        model.addRow(tittle);
                        if (getRow() != null) {
                            model.addRow(getRow());
                        }
                        setEnabled(false);
                        setBackground(bgPay);
                        setSelectionBackground(bgPay);
                        setSelectionForeground(Color.BLACK);
                        setModel(model);
                        setFont(font);
                        setVisible(true);
                        getColumnModel().getColumn(0).setPreferredWidth(250);
                        getColumnModel().getColumn(1).setPreferredWidth(20);
                        setVisible(true);
                        addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                //Thiết lập khi nhấn vào panel
                            }
                        });
                        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
                    }
                });
            }
        }, BorderLayout.CENTER);
        payProduct.add(new JPanel(){
            {
                setLayout(new BoxLayout(this, 1));
                setBackground(bgPay);
                setBorder(BorderFactory.createEmptyBorder(0,10,10,0));
                add(new JLabel(){
                    {
                        setFont(font);
                        setName("totalPayment");
                        setText("<html> <p style='border-top: solid 2px; padding-top:2px; padding-bottom:5px;'> Thành tiền: "  + getCoin(totalPayment) + "</p> </html>");
                    }
                });
                add(new JButton(){
                    {
                        setFocusPainted(false);
                        setBorderPainted(false);
                        setText("Thanh toán");
                        setCursor(new Cursor(12));
                        setFont(font);
                        setBackground(Color.GREEN);
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                PanelUser.setHidePanel(PanelUser.panelBuyProduct());
                            }
                        });
                    }
                });
            }
        }, BorderLayout.SOUTH);
        return payProduct;
    }
    static String billProducts[] = null;

    private static void setRow(String name, int quantity, int money) {
        String data[] = {"<html>" + name + "</html>", String.valueOf(quantity), String.valueOf(money)};
        billProducts = data;
    }
    private static String[] getRow() {
        return billProducts;
    }

    private static JPanel productsShopCart() { //Upload từ database tất cả sản phẩm đã thêm vào giỏ hàng
        JPanel products = new JPanel();
        products.setName("products"); // Được gọi từ AllComponents
        products.setLayout(new BoxLayout(products, 1));
        products.setBorder(null);
        return products;
    }

    public static JPanel productShopCart(String name, String img, int money) { //bảng chứa hình ảnh và nội dung của 1 sản phẩm
        JPanel panel = new JPanel();
        numberProductInShopcart++;
        panel.setName("productShopCart");
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        panel.setBackground(Color.WHITE);
        panel.add(new JPanel() {
            {
                RoundedLineBorder roundedLineBorder = new RoundedLineBorder(Color.BLACK, 1, 10, true);
                setBorder(roundedLineBorder);
                setBackground(Color.WHITE);
                setLayout(new FlowLayout(FlowLayout.LEFT));
                add(new JCheckBox(){
                    {
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                if (isSelected()) {
                                    //Setting khi chọn sản phẩm thêm vào
                                    int quantityProduct = Integer.valueOf(((JTextField)AllComponent.getPanel(panel, "txtQuanlity")).getText());
                                    totalPayment += quantityProduct * money;
                                    ((JLabel)AllComponent.getPanel(payProduct, "totalPayment")).setText("<html> <p style='border-top: solid 2px; padding-bottom:5px; padding-top:2px;'> Thành tiền: "  + getCoin(totalPayment) + "</p> </html>");
                                    setRow(name, quantityProduct, quantityProduct * money);
                                    JTable table = ((JTable)AllComponent.getPanel(payProduct, "tableBill"));
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(getRow());
                                    int height = AllComponent.getPanel(panel, "labelnameproduct").getPreferredSize().height;
                                    table.setRowHeight(model.getRowCount() - 1, height + 20);
                                } else {
                                    DefaultTableModel model = (DefaultTableModel) ((JTable)AllComponent.getPanel(payProduct, "tableBill")).getModel();
                                    for(int i = 0; i < model.getRowCount(); i++) {
                                        if (model.getValueAt(i, 0).equals("<html>" + name + "</html>")) {
                                            totalPayment -= Integer.valueOf(model.getValueAt(i, 2).toString());
                                            ((JLabel)AllComponent.getPanel(payProduct, "totalPayment")).setText("<html> <p style='border-top: solid 2px; padding-bottom:5px; padding-top:2px;'> Thành tiền: "  + getCoin(totalPayment) + "</p> </html>");
                                            model.removeRow(i);
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
                add(new JLabel() {
                    {
                        //setIcon(new ImageIcon(SetIcon.imageProcess("DataImage/image-update.png", 150, setInfomationProduct(name, money, panel).getPreferredSize().height)));
                        Thread thread = new Thread(new setimg("DataImage/image-update.png", this, panel, 150, setInfomationProduct(name, money, panel).getPreferredSize().height));
                        thread.run();
                    }
                });
                add(setInfomationProduct(name, money, panel));
            }
        });
//        panel.add(setInfomationProduct(name, money, panel));
        panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
        return panel;
    }

    private static JPanel setInfomationProduct(String name, int money, JPanel panel) {
        Color bgColorProduct = Color.WHITE;
        JPanel infomationProductInShopcart = new JPanel(){
            {
                final int quantity = 1;
                setBackground(bgColorProduct);
                setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
                setLayout(new BoxLayout(this, 1));
                add(new JLabel() {
                    {
                        setVerticalAlignment(JLabel.TOP);
                        setName("labelnameproduct");
                        setBackground(bgColorProduct);
                        setMaximumSize(new Dimension(350, Integer.MAX_VALUE));
                        setText("<html> <p style='width: 250px'> Tên sách: " + name + "</p></html>");
                        setFont(font);
                    }
                });
                add(new JPanel() {
                    {
                        setAlignmentX(Component.LEFT_ALIGNMENT);
                        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
                        setBackground(bgColorProduct);
                        add(new JLabel() {
                            {
                                setVerticalAlignment(JLabel.TOP);
                                setText("Số lượng: ");
                                setFont(font);
                            }
                        });
                        add(new JPanel() {
                            {
                                setBorder(null);
                                setLayout(new FlowLayout());
                                JTextField txtQuanlity = new JTextField() {
                                    {
                                        setName("txtQuanlity");
                                        setFont(font);
                                        int border = 5;
                                        setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
                                        setText(String.valueOf(quantity));
                                        setEditable(false);
                                        setHorizontalAlignment(JLabel.CENTER);
                                        setPreferredSize(new Dimension(getPreferredSize().width + 20, getPreferredSize().height));
                                    }
                                };
                                add(new JLabel() {
                                    {
                                        setFont(font);
                                        setText("-");
                                        addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                int v2 = Integer.valueOf(txtQuanlity.getText());
                                                if (v2 > 1) {
                                                    v2--;
                                                }
                                                txtQuanlity.setText(String.valueOf(v2));
                                                ((JLabel) AllComponent.getPanel(panel, "money")).setText("Giá tiền: " + getCoin(v2 * money));
                                            }

                                            @Override
                                            public void mouseEntered(MouseEvent e) {
                                                setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
                                                setCursor(new Cursor(12));
                                            }

                                            @Override
                                            public void mouseExited(MouseEvent e) {
                                                setFont(font);
                                                setCursor(new Cursor(0));
                                            }
                                        });
                                    }
                                });
                                add(txtQuanlity);
                                add(new JLabel() {
                                    {
                                        setBackground(bgColorProduct);
                                        setFont(font);
                                        setText("+");
                                        setCursor(new Cursor(12));
                                        addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                int v2 = Integer.valueOf(txtQuanlity.getText());
                                                v2++;
                                                txtQuanlity.setText(String.valueOf(v2));
                                                ((JLabel) AllComponent.getPanel(panel, "money")).setText("Giá tiền: " + getCoin(v2 * money));
                                            }

                                            @Override
                                            public void mouseEntered(MouseEvent e) {
                                                setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
                                            }

                                            @Override
                                            public void mouseExited(MouseEvent e) {
                                                setFont(font);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
                add(new JLabel() {
                    {
                        setAlignmentX(Component.LEFT_ALIGNMENT);
                        setBackground(bgColorProduct);
                        setName("money");
                        setText("Giá tiền: " + getCoin(money * quantity));
                        setFont(font);
                        setBorder(BorderFactory.createEmptyBorder(0,0 , 10,0 ));
                    }
                });
                add(new JButton(){
                    {
                        setPreferredSize(new Dimension(70, 30));
                        setName("btnbuy");
                        setText("Chi tiết sản phẩm");
                        setFont(font);
                        setFocusPainted(false);
                        setBorderPainted(false);
                        setHorizontalAlignment(JLabel.LEFT);
                        setCursor(new Cursor(12));
                    }
                });
            }
        };
        return infomationProductInShopcart;
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
class RoundedLineBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int radius;
    private final boolean roundedCorners;

    public RoundedLineBorder(Color color, int thickness, int radius, boolean roundedCorners) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
        this.roundedCorners = roundedCorners;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        int r = radius;
        if (roundedCorners) {
            g2d.drawRoundRect(x, y, width - thickness, height - thickness, r, r);
        } else {
            g2d.drawRect(x, y, width - thickness, height - thickness);
        }
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(thickness, thickness, thickness, thickness);
        return insets;
    }
}



