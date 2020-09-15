package com.meti.util;

public class Triad<A, B, C>{
    private final A start;
    private final B middle;
    private final C end;

    public Triad(A start, B middle, C end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public <R> R apply(TriFunction<A, B, C, R> function) {
        return function.apply(start, middle, end);
    }
}
