package com.meti.feature.render;

import com.meti.util.Monad;
import com.meti.util.Triad;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Field extends Renderable, Comparable<Field> {
    boolean isNamed(String name);

    Monad<String> name();

    int compareTo(Field o);

    <R> R applyDestruction(BiFunction<String, Type, R> function);

    Triad<String, Type, List<Flag>> destroy();

    <R> R applyToName(Function<String, R> mapping);

    Monad<Type> type();

    Field transformByType(Function<Type, Type> mapping);

    String renderWithMore(String more);

    @Override
    default String render() {
        return renderOptionally().orElseThrow(() -> new UnrenderableException("Not renderable."));
    }

    default String renderType() {
        return type().apply(Type::render);
    }

    boolean isFlagged(Flag flag);

    default boolean isTyped(Type type) {
        return type().test(type::equals);
    }

    boolean doesReturn(Type type);

    enum Flag {
        CONST,
        LET,
        NATIVE,
        IMPLICIT,
        DEF
    }
}