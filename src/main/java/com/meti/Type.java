package com.meti;

import java.util.Optional;
import java.util.function.Function;

public interface Type {
    <R> Optional<R> applyToContent(Function<Content, R> function);

    @Deprecated
    default <R> Optional<R> applyToValue(Function<String, R> function) {
        return Optional.empty();
    }

    String render(String name);
}
