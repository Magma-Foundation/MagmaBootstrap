package com.meti;

import java.util.Optional;
import java.util.function.Function;

public abstract class FormattedType implements Type {

    protected abstract String createFormat();

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> render(String name){
        throw new UnsupportedOperationException();
    }
}
