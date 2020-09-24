package com.meti.feature.render;

import com.meti.content.Content;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ContentNode implements Node {
    private final Content content;

    public ContentNode(Content content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        ContentNode that = (ContentNode) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(content).map(function);
    }

    @Override
    public Prototype createPrototype() {
        throw new UnsupportedOperationException(String.format("Cannot create prototype for value: '%s'", content.asString()));
    }

    @Override
    public Optional<String> renderOptionally() {
        throw content.value().apply(s -> new UnsupportedOperationException(String.format("Cannot renderOptionally node with value of '%s', this node hasn't been parsed yet.", s)));
    }

    @Override
    public Stream<Field> streamFields() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Node> streamChildren() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Monad<Group> group(){
        return new Monad<>(Group.Content);
    }

    @Override
    public boolean isParent() {
        return false;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doesReturn(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }
}
