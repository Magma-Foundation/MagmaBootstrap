package com.meti.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Monad<T> {
    private final T value;

    public Monad(T value) {
        this.value = value;
    }

    public <R> R apply(Function<T, R> function) {
        return function.apply(value);
    }

    public <R> Dyad<T, R> append(R other) {
        return new Dyad<>(value, other);
    }

    public Optional<T> toOption() {
        return Optional.of(value);
    }

    public boolean test(Predicate<T> predicate) {
        return predicate.test(value);
    }

    public <R> Dyad<T, R> extract(Function<T, R> function) {
        return new Dyad<>(value, function.apply(value));
    }
}
