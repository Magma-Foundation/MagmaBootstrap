package com.meti;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InlineField implements Field {
    private final String name;
    private final Type type;

    public InlineField(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public <R> R applyToType(Function<Type, R> mapping) {
        return mapping.apply(type);
    }

    @Override
    public Field copy(Type type) {
        return new InlineField(name, type);
    }

    @Override
    public <R> R applyDestruction(BiFunction<String, Type, R> function) {
        return function.apply(name, type);
    }

    @Override
    public Optional<String> render() {
        return Optional.of(type.render(name));
    }
}
