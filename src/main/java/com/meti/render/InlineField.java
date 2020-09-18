package com.meti.render;

import com.meti.type.Type;
import com.meti.util.Monad;
import com.meti.util.Triad;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InlineField implements Field {
    private final String name;
    private final Type type;
    private final List<FieldFlag> flags;

    public InlineField(Type type) {
        this("", type, Collections.emptyList());
    }

    public InlineField(String name, Type type, List<FieldFlag> flags) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    @Override
    public <R> R applyToType(Function<Type, R> mapping) {
        return mapping.apply(type);
    }

    @Override
    public Field copy(Type type) {
        return new InlineField(name, type, flags);
    }

    @Override
    public <R> R applyDestruction(BiFunction<String, Type, R> function) {
        return function.apply(name, type);
    }

    @Override
    public Optional<String> render() {
        return type.render(name);
    }

    @Override
    public <R> R applyToName(Function<String, R> mapping) {
        return mapping.apply(name);
    }

    @Override
    public Monad<String> name() {
        return new Monad<>(name);
    }

    @Override
    public Triad<String, Type, List<FieldFlag>> destroy() {
        return new Triad<>(name, type, flags);
    }

    @Override
    public Monad<Type> type() {
        return new Monad<>(type);
    }
}
