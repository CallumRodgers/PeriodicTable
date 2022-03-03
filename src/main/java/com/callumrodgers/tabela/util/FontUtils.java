package com.callumrodgers.tabela.util;

import java.awt.*;

public class FontUtils {

    public static Font changeSize(Font font, int newSize) {
        return new Font(font.getFontName(), font.getStyle(), newSize);
    }
}
