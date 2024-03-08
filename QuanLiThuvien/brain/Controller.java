package QuanLiThuvien.brain;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Controller
 */
public class Controller implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Component button = (Component) e.getSource();
        String clicked = button.getName();
        if (clicked.equals("btnLogin")) {

        }
    }
}