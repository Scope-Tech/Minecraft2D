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

package io.github.overrun.mc2d.registry;

import io.github.overrun.mc2d.util.Identifier;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author squid233
 * @since 2020/10/03
 */
public class RegistryKey<T> {
    private static final Map<String, RegistryKey<?>> INSTANCES = Collections.synchronizedMap(new IdentityHashMap<>());
    private final Identifier registry;
    private final Identifier value;

    private RegistryKey(Identifier registry, Identifier value) {
        this.registry = registry;
        this.value = value;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> RegistryKey<T> of(Identifier registry, Identifier value) {
        String str = (registry + ":" + value).intern();
        return (RegistryKey) INSTANCES.computeIfAbsent(str, s -> new RegistryKey<>(registry, value));
    }

    public static <T> RegistryKey<T> ofRegistry(Identifier registry) {
        return of(Registry.ROOT_KEY, registry);
    }

    public Identifier getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ResourceKey[" + registry + " / " + value + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistryKey<?> that = (RegistryKey<?>) o;
        return Objects.equals(registry, that.registry) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(registry, getValue());
    }
}