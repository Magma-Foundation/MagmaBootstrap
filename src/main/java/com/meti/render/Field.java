package com.meti.render;

import com.meti.type.Type;
import com.meti.util.Monad;
import com.meti.util.Triad;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Field extends Renderable {
    Monad<String> name();

    <R> R applyToType(Function<Type, R> mapping);

    Field copy(Type type);

    <R> R applyDestruction(BiFunction<String, Type, R> function);

    Triad<String, Type, List<FieldFlag>> destroy();

    <R> R applyToName(Function<String, R> mapping);
}