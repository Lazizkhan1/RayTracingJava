package io.github.lazizkhan1.raytracingjava.util;

import java.util.ArrayList;

public class List<T> extends ArrayList<T> {
    public T set(T element) {
        add(element);
        return super.get(size() - 1);
    }
}
