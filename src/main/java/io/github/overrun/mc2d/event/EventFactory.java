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

package io.github.overrun.mc2d.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author squid233
 * @since 2020/12/16
 */
public final class EventFactory {
    private static final List<ArrayBackedEvent<?>> ARRAY_BACKED_EVENTS = new ArrayList<>();

    private EventFactory() {}

    public static void invalidate() {
        ARRAY_BACKED_EVENTS.forEach(ArrayBackedEvent::update);
    }

    /**
     * Create an {@link Event} object.
     *
     * @param invokerFactory The list to an event class. You can traverse the list to execute the event callback.
     * @param <T> The event class. Maybe an interface.
     * @return The array backed event.
     */
    public static <T> Event<T> createArrayBacked(Function<List<T>, T> invokerFactory) {
        return createArrayBacked(null, invokerFactory);
    }

    public static <T> Event<T> createArrayBacked(T emptyInvoker, Function<List<T>, T> invokerFactory) {
        ArrayBackedEvent<T> event = new ArrayBackedEvent<>(emptyInvoker, invokerFactory);
        ARRAY_BACKED_EVENTS.add(event);
        return event;
    }
}
