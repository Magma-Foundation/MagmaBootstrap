package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Field extends Renderable {
    <R> R applyToType(Function<Type, R> mapping);

    Field copy(Type type);

    <R> R applyDestruction(BiFunction<String, Type, R> function);

    <R> R applyToName(Function<String, R> mapping);
}