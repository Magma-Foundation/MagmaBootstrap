package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class FieldNode extends ParentNode{
    private final Content fieldName;
    private final Node parent;

    public FieldNode(Node parent, Content fieldName) {
        this.fieldName = fieldName;
        this.parent = parent;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(fieldName).map(function);
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Field);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(parent);
    }

    @Override
    public Prototype createPrototype() {
        return new FieldNodePrototype(fieldName, parent);
    }

    @Override
    public Optional<String> render() {
        return Optional.of(parent.render().orElseThrow() + "." + fieldName.value().apply(Function.identity()));
    }

    private static class FieldNodePrototype implements Prototype {
        private final Content fieldName;
        private final Node parent;

        private FieldNodePrototype(Content fieldName) {
            this(fieldName, null);
        }

        private FieldNodePrototype(Content fieldName, Node parent) {
            this.fieldName = fieldName;
            this.parent = parent;
        }

        @Override
        public Prototype withField(Field field) {
            return this;
        }

        @Override
        public Prototype withChild(Node child) {
            return new FieldNodePrototype(fieldName, child);
        }

        @Override
        public Node build() {
            return new FieldNode(parent, fieldName);
        }
    }
}
