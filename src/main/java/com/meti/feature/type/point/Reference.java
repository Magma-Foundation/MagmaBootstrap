package com.meti.feature.type.point;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Parent;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Reference extends Parent {
    private final Node value;

    public Reference(Node value) {
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Reference);
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
    public Optional<String> renderOptionally() {
        return Optional.of("&" + value.renderOptionally().orElseThrow());
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
    public Node transformChildren(Function<Node, Node> mapping) {
        return new Reference(mapping.apply(value));
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
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
            return new Reference(value);
        }

        @Override
        public List<Node> listChildren() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Field> listFields() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Prototype merge(Prototype other) {
            throw new UnsupportedOperationException();
        }
    }
}
