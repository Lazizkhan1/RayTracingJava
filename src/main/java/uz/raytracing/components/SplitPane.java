package uz.raytracing.components;

import uz.raytracing.util.Property;

import javax.swing.JSplitPane;
import java.awt.Component;


public class SplitPane extends JSplitPane {

    public SplitPane(Component newLeftComponent, Component newRightComponent) {
        super(HORIZONTAL_SPLIT, newLeftComponent, newRightComponent);
    }

    public SplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newLeftComponent, newRightComponent);
        setDividerLocation(Property.get("DividerLocation"));
        setResizeWeight(1.0);
        setDividerSize(4);
    }
}
