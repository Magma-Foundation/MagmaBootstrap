package com.meti.feature.type.point;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Pointer implements Type {
    private final Type child;

    private Pointer(Type child) {
        this.child = child;
    }

    public static Pointer to(Type child) {
        return new Pointer(child);
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Prototype createPrototype() {
        return new PointerPrototype(child);
    }

    @Override
    public Stream<Type> streamChildren() {
        return Stream.of(child);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Monad<Group> group() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render(String name) {
        return child.render("* " + name);
    }

    @Override
    public String render() {
        return child.render("*");
    }

    @Override
    public boolean is(Group group) {
        return group().test(group.matches());
    }

    @Override
    public Type transformField(Function<Field, Field> mapping) {
        return this;
    }

    @Override
    public Type transformChildren(Function<Type, Type> mapping) {
        return new Pointer(mapping.apply(child));
    }

    @Override
    public boolean doesReturn(Type type) {
        return false;
    }
}
