package com.meti.type;

import com.meti.content.Content;

import java.util.Optional;
import java.util.function.Function;

public interface Type {
    <R> Optional<R> applyToContent(Function<Content, R> function);

    Optional<String> render(String name);
}