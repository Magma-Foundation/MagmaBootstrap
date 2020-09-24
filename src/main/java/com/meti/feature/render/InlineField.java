package com.meti.feature.render;

import com.meti.util.Monad;
import com.meti.util.Triad;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InlineField implements Field {
    private final String name;
    private final Type type;
    private final List<Flag> flags;

    public InlineField(String name, Type type, List<Flag> flags) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    private <R> R applyToType(Function<Type, R> mapping) {
        return mapping.apply(type);
    }

    private Field copy(Type type) {
        return new InlineField(name, type, flags);
    }

    @Override
    public <R> R applyDestruction(BiFunction<String, Type, R> function) {
        return function.apply(name, type);
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.ofNullable(type.render(name));
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
    public Triad<String, Type, List<Flag>> destroy() {
        return new Triad<>(name, type, flags);
    }

    @Override
    public Monad<Type> type() {
        return new Monad<>(type);
    }

    @Override
    public Field transformByType(Function<Type, Type> mapping) {
        Type newType = applyToType(mapping);
        return copy(newType);
    }

    @Override
    public int compareTo(Field o) {
        return name().apply(s -> o.name().apply(s::compareTo));
    }

    @Override
    public String renderWithMore(String more) {
        return type.render(name + more);
    }

    @Override
    public boolean isFlagged(Flag flag) {
        return flags.contains(flag);
    }

    @Override
    public boolean doesReturn(Type type) {
        return this.type.doesReturn(type);
    }

    @Override
    public boolean isNamed(String name){
        return name().test(name::equals);
    }
}
