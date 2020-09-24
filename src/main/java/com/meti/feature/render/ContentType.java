package com.meti.feature.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ContentType implements Type {
    private final Content content;

    public ContentType(Content content) {
        this.content = content;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(content).map(function);
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
        return new Monad<>(Group.Content);
    }

    @Override
    public String render(String name) {
        String message = "Cannot renderOptionally ContentType of value '%s' with name '%s'.";
        String formatted = String.format(message, content, name);
        throw new UnrenderableException(formatted);
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