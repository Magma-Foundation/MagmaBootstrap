package com.meti.feature.render;

import com.meti.util.Monad;
import com.meti.util.Triad;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TypeField implements Field {
    private final Type type;

    public TypeField(Type type) {
        this.type = type;
    }

    @Override
    public Monad<String> name() {
        throw new UnsupportedOperationException();
    }

    private <R> R applyToType(Function<Type, R> mapping) {
        return mapping.apply(type);
    }

    private Field copy(Type type) {
        return new TypeField(type);
    }

    @Override
    public <R> R applyDestruction(BiFunction<String, Type, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Triad<String, Type, List<Flag>> destroy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> R applyToName(Function<String, R> mapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Monad<Type> type() {
        return new Monad<>(type);
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.ofNullable(type.render());
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFlagged(Flag flag) {
        return false;
    }

    @Override
    public boolean doesReturn(Type type) {
        return type.doesReturn(type);
    }

    @Override
    public boolean isNamed(String name){
        return name().test(name::equals);
    }
}
