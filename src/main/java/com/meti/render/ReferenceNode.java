package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReferenceNode extends ParentNode {
    private final Node value;

    public ReferenceNode(Node value) {
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Reference);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(value);
    }

    @Override
    public Prototype createPrototype() {
        return new ReferencePrototype();
    }

    @Override
    public Optional<String> render() {
        return Optional.of("&" + value.render().orElseThrow());
    }

    private static class ReferencePrototype implements Prototype {
        private final Node value;

        private ReferencePrototype() {
            this(null);
        }

        private ReferencePrototype(Node value) {
            this.value = value;
        }

        @Override
        public Prototype withField(Field field) {
            return this;
        }

        @Override
        public Prototype withChild(Node child) {
            return new ReferencePrototype(child);
        }

        @Override
        public Node build() {
            if(value == null) throw new IllegalStateException("No value was provided.");
            return new ReferenceNode(value);
        }
    }
}
