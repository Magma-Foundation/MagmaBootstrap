package com.meti.render;

import com.meti.content.Content;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> render() {
        throw content.value().apply(s -> new UnsupportedOperationException(String.format("Cannot render node with value of '%s', this node hasn't been parsed yet.", s)));
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
    public Monad<NodeGroup> group(){
        throw new UnsupportedOperationException();
    }
}
