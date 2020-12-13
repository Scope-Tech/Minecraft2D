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

package io.github.overrun.mc2d.screen;

import io.github.overrun.mc2d.client.Mc2dClient;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import static io.github.overrun.mc2d.screen.Screens.openScreen;
import static io.github.overrun.mc2d.util.DrawHelper.drawDefaultBackground;
import static io.github.overrun.mc2d.util.DrawHelper.drawDirtBackground;
import static java.awt.event.KeyEvent.VK_ESCAPE;

/**
 * @author squid233
 * @since 2020/10/16
 */
public abstract class Screen {
    protected final ObjectList<ScreenWidget> widgets = new ObjectArrayList<>(6);
    protected final ObjectList<ButtonWidget> buttons = new ObjectArrayList<>(6);
    protected Mc2dClient client;

    protected Screen() {
        this.client = Mc2dClient.getInstance();
    }

    public void render(Graphics g) {
        drawDirtBackground(g);
        drawDefaultBackground(g);
        for (ScreenWidget w : widgets) {
            w.render(g);
        }
    }

    public void onKeyDown(KeyEvent e) {
        if (e.getKeyChar() == VK_ESCAPE) {
            close();
        }
    }

    public ScreenWidget addWidget(ScreenWidget widget) {
        widgets.add(0, widget);
        return widget;
    }

    public ButtonWidget addButton(ButtonWidget widget) {
        buttons.add(0, widget);
        return (ButtonWidget) addWidget(widget);
    }

    protected void close() {
        open(Screens.getParent());
    }

    protected void open(Screen screen) {
        openScreen(screen);
    }

    public Mc2dClient getClient() {
        return client;
    }

    public void onMouseClicked() {
        for (ButtonWidget b : buttons) {
            if (b.isHover() && b.isEnable()) {
                b.getAction().onPress(b);
                break;
            }
        }
    }
}
