/*
 * MIT License
 *
 * Copyright (c) 2020 Over-Run
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.overrun.mc2d.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author squid233
 * @since 2020/11/24
 */
public final class Images {
    public static final Image EMPTY = new ImageIcon().getImage();
    public static final BufferedImage WIDGETS = guiImg("widgets");
    public static final BufferedImage OPTIONS_BACKGROUND = guiImg("options_background");
    public static final BufferedImage LOGO = guiImg("logo");
    public static final BufferedImage BUTTON_DISABLE = widgetImg(0, 46, 200, 20);
    public static final BufferedImage BUTTON = widgetImg(0, 66, 200, 20);
    public static final BufferedImage BUTTON_HOVER = widgetImg(0, 86, 200, 20);
    public static final BufferedImage LANG_BUTTON = widgetImg(0, 106, 20, 20);
    public static final BufferedImage LANG_BUTTON_HOVER = widgetImg(0, 126, 20, 20);

    private static BufferedImage guiImg(String nm) {
        return ImgUtil.readImage(new ResourceLocation("textures/gui/" + nm + ".png"));
    }

    private static BufferedImage widgetImg(int x, int y, int w, int h) {
        return WIDGETS.getSubimage(x, y, w, h);
    }
}
