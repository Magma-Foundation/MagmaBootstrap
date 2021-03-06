package com.meti.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Dyad<A, B> {
    private final A start;
    private final B end;

    public Dyad(A start, B end) {
        this.start = start;
        this.end = end;
    }

    public <R> Monad<R> map(BiFunction<A, B, R> function) {
        return new Monad<>(function.apply(start, end));
    }

    public <R> R apply(BiFunction<A, B, R> function) {
        return function.apply(start, end);
    }

    public Dyad<B, A> swap() {
        return new Dyad<>(end, start);
    }

    public void accept(BiConsumer<A, B> consumer) {
        consumer.accept(start, end);
    }

    public<R> Triad<A, B, R> append(R value) {
        return new Triad<>(start, end, value);
    }
}
