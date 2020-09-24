package com.meti.feature.render;

import com.meti.content.Content;
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

    private Optional<String> renderOptionally(String name){
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
    public Monad<Group> group(){
        throw new UnsupportedOperationException();
    }

    private Optional<String> renderOptionally() {
        return renderOptionally("");
    }

    @Override
    public String render(String name) {
        return renderOptionally().orElseThrow();
    }

    @Override
    public String render(){
        return render("");
    }

    @Override
    public boolean is(Group group) {
        return group().test(group.matches());
    }

    @Override
    public Type transformField(Function<Field, Field> mapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type transformChildren(Function<Type, Type> mapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doesReturn(Type type) {
        return false;
    }
}
