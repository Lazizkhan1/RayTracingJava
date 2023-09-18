package io.github.lazizkhan1.raytracingjava.components;

import io.github.lazizkhan1.raytracingjava.util.Property;

import javax.swing.JSplitPane;
import java.awt.Component;


public class SplitPane extends JSplitPane {
    private final String mId;

    public SplitPane(String id, int newOrientation, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newLeftComponent, newRightComponent);
        mId = id;
        setDividerLocation(Property.get(id));
        setResizeWeight(1.0);
        setDividerSize(4);
    }

    public String getId() {
        return mId;
    }

}
