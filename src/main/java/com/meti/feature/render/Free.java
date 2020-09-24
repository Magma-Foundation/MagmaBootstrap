package com.meti.feature.render;

import com.meti.content.Content;
import com.meti.stack.CallStack;

import java.util.Optional;
import java.util.function.Function;

public abstract class Free extends Parent {
    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }
}
