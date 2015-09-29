package ru.svalov.util;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class InfiniteIterator<T> implements Iterator<T> {

    private final List<T> list;
    private int index = 0;

    public InfiniteIterator(List<T> list) {
        Objects.requireNonNull(list, "list");
        this.list = list;
    }

    public boolean hasNext() {
        return list.size() > 0;
    }

    public T next() {
        if (index == list.size()) {
            this.index = 0;
        }
        return hasNext() ? list.get(index++) : null;
    }
}
