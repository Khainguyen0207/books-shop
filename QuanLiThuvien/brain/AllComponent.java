package QuanLiThuvien.brain;

import java.awt.*;

public class AllComponent {
    public static Component getPanel(Component component , String nameComponent) {
        for(Component component1 : ((Container) component).getComponents()) {
            if (checkNamePanel(component1, nameComponent)) {
                setComponent(component1);
                return getComponent();
            } else {
                if (component1.getName().equals(null)) {
                    continue;
                } else {
                    getPanel(component1 , nameComponent);
                }
                
            }
        }
        return getComponent();
    }

    private static Boolean checkNamePanel(Component component, String name) {
        if (component.getName().equals(name)) {
            return true;
        }
        return false;
    }

    static Component c; //Component theo nameComponent

    private static Component setComponent(Component component) {
        c = component;
        return component;
    }
    
    private static Component getComponent() {
        return c;
    }
}
