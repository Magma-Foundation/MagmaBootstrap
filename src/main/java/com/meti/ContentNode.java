package com.meti;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ContentNode implements Node {
    private final Content content;

    public ContentNode(Content content) {
        this.content = content;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(content).map(function);
    }

    @Override
    public Prototype createPrototype() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> render(){
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Field> streamFields() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Node> streamChildren() {
        throw new UnsupportedOperationException();
    }
}
