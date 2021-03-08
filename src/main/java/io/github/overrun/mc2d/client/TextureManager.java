/*
 * MIT License
 *
 * Copyright (c) 2020-2021 Over-Run
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

package io.github.overrun.mc2d.client;

import io.github.overrun.mc2d.mod.ModLoader;
import io.github.overrun.mc2d.util.Identifier;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.nio.ByteBuffer;

import static io.github.overrun.mc2d.util.ImageReader.read;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author squid233
 * @since 2021/01/25
 */
public final class TextureManager implements Closeable {
    private final Object2IntMap<Identifier> idMap = new Object2IntOpenHashMap<>(16);
    private int lastId = -999999;

    public int loadTexture(Identifier id) {
        return loadTexture(id, GL_NEAREST);
    }

    public int loadTexture(Identifier id, int mode) {
        if (idMap.containsKey(id)) {
            return idMap.getInt(id);
        } else {
            int texId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texId);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, mode);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, mode);
            BufferedImage img;
            img = read("assets/" + id.getNamespace() + "/" + id.getPath(),
                    id.isVanilla()
                            ? ClassLoader.getSystemClassLoader()
                            : ModLoader.getLoader(id.getNamespace()));
            ByteBuffer pixels;
            int w, h;
            int[] rawPixels;
            if (img == null) {
                w = h = 2;
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    pixels = stack.malloc(16);
                }
                rawPixels = new int[]{
                        0xfff800f8, 0xff000000,
                        0xff000000, 0xfff800f8
                };
            } else {
                w = img.getWidth();
                h = img.getHeight();
                pixels = MemoryUtil.memAlloc(w * h * 4);
                rawPixels = new int[w * h];
                img.getRGB(0, 0, w, h, rawPixels, 0, w);
                for (int i = 0; i < rawPixels.length; ++i) {
                    int a = rawPixels[i] >> 24 & 255;
                    int r = rawPixels[i] >> 16 & 255;
                    int g = rawPixels[i] >> 8 & 255;
                    int b = rawPixels[i] & 255;
                    rawPixels[i] = a << 24 | b << 16 | g << 8 | r;
                }
            }
            pixels.asIntBuffer().put(rawPixels);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
            idMap.put(id, texId);
            return texId;
        }
    }

    public void bindTexture(Identifier id) {
        int texId = loadTexture(id);
        if (lastId != texId) {
            glBindTexture(GL_TEXTURE_2D, texId);
            lastId = texId;
        }
    }

    @Override
    public void close() {
        for (int id : idMap.values()) {
            glDeleteTextures(id);
        }
        idMap.clear();
    }
}