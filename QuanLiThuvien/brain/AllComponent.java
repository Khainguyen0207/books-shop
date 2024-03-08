package QuanLiThuvien.brain;

import java.awt.*;

public class AllComponent {
    public static Component getPanel(Component component , String nameComponent) {
        for(Component component1 : ((Container) component).getComponents()) {
            //System.out.println("" + component1);
            if (checkNamePanel(component1, nameComponent)) {
                setComponent(component1);
            } else {
                getPanel(component1 , nameComponent);
            }
        }
        return getComponent();
    }

    private static Boolean checkNamePanel(Component component, String name) {

        if (component.getName() == null) {
            return false;
        }

        return component.getName().equals(name);
    }

    static Component c; //Component theo nameComponent

    private static void setComponent(Component component) {
        c = component;
    }

    private static Component getComponent() {
        return c;
    }
}
