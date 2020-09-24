package com.meti.feature.type.primitive;

import com.meti.content.Content;
import com.meti.feature.render.*;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;

public class String_ extends Leaf implements Untyped {
    private final String value;

    public String_(String value) {
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.String);
    }

    @Override
    public Prototype createPrototype() {
        return new PrototypeImpl();
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.of("\"" + value + "\"");
    }

    @Override
    public Prototype create(Node child){
        return createPrototype().withChild(child);
    }

    @Override
    public Prototype create(Field field) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Prototype createWithChildren() {
        return streamChildren()
                .map(this::create)
                .reduce(createPrototype(), Prototype::merge);
    }

    @Override
    public Node transformFields(Function<Field, Field> mapping) {
        return this;
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doesReturn(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    private class PrototypeImpl extends PassPrototype {
        @Override
        public Node build() {
            return new String_(value);
        }
    }
}
