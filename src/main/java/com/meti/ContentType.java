package com.meti;

import java.util.Optional;
import java.util.function.Function;

public class ContentType implements Type {
    private final Content content;

    public ContentType(Content content) {
        this.content = content;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(content).map(function);
    }

    @Override
    public Optional<String> render(String name) {
        return Optional.empty();
    }
}