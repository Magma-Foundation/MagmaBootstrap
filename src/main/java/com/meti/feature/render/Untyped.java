package com.meti.feature.render;

import com.meti.stack.CallStack;

import java.util.function.Function;
import java.util.stream.Stream;

public interface Untyped extends Node {
    @Override
    default Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    default Node transformFields(Function<Field, Field> mapping) {
        return this;
    }

    @Override
    default boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    @Override
    default boolean doesReturn(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }
}
