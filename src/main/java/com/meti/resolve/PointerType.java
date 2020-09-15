package com.meti.resolve;

import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.type.Type;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

class PointerType implements Type {
    private final Type child;

    PointerType(Type child) {
        this.child = child;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Optional<String> render(String name) {
        return Optional.of(child.render("* " + name).orElseThrow());
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
}
