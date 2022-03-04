package com.callumrodgers.tabela.ui;

import com.callumrodgers.tabela.util.ResourceLoader;

import java.awt.*;

public class UIDefaults {

    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

    public static final Color TRANSPARENT = new Color(0x00000000, true);

    public static final Font DEFAULT_FONT_12 = new Font("Segoe UI", Font.PLAIN, 12),
                             DEFAULT_FONT_13 = DEFAULT_FONT_12.deriveFont(13.0f),
                             DEFAULT_FONT_15 = DEFAULT_FONT_12.deriveFont(15.0f);

    public static final Image icon = ResourceLoader.getImage("icon.png");
}
