package com.meti.feature.type;

import com.meti.feature.render.*;
import com.meti.util.Monad;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Cast extends Free {
    private final Node value;
    private final Field identity;

    public Cast(Node value, Field identity) {
        this.value = value;
        this.identity = identity;
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Cast);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.of(identity);
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(value);
    }

    @Override
    public Prototype createPrototype() {
        return new CastPrototype();
    }

    @Override
    public Optional<String> renderOptionally() {
        String renderedType = identity.render().trim();
        String encapsulated = String.format("(%s)", renderedType);
        String renderedValue = this.value.render();
        return Optional.of(encapsulated + renderedValue);
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
        return new Cast(value, mapping.apply(identity));
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        return new Cast(mapping.apply(value), identity);
    }

    private static class CastPrototype implements Prototype {
        private final Field identity;
        private final Node value;

        private CastPrototype() {
            this(null, null);
        }

        private CastPrototype(Field identity, Node value) {
            this.identity = identity;
            this.value = value;
        }

        @Override
        public Prototype withField(Field fieldNode) {
            return new CastPrototype(fieldNode, value);
        }

        @Override
        public Prototype withChild(Node child) {
            return new CastPrototype(identity, child);
        }

        @Override
        public Node build() {
            return new Cast(value, identity);
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
