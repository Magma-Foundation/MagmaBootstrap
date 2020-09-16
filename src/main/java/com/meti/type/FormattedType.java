package com.meti.type;

import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class FormattedType implements Type {
    protected abstract String createFormat();

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> render(String name){
        return Optional.of(String.format(createFormat(), name));
    }

    @Override
    public Prototype createPrototype(){
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Type> streamChildren(){
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Field> streamFields(){
        throw new UnsupportedOperationException();
    }

    @Override
    public Monad<TypeGroup> group(){
        throw new UnsupportedOperationException();
    }
}
