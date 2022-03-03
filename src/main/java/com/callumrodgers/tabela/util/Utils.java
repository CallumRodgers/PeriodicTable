package com.callumrodgers.tabela.util;

import java.awt.*;

public class Utils {

    public static Rectangle getScreenBounds(Component c) {
        return new Rectangle(c.getLocationOnScreen(), c.getSize());
    }

    public static boolean isPointOnComponent(Point p, Component c) {
        return getScreenBounds(c).contains(p);
    }
}
