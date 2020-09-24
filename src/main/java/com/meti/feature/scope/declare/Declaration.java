package com.meti.feature.scope.declare;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Parent;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

class Declaration extends Parent {
    private final Field identity;
    private final Node value;

    public Declaration(Field identity, Node value) {
        this.identity = identity;
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Declare);
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
        return new DeclarePrototype();
    }

    @Override
    public Optional<String> renderOptionally() {
        String renderedIdentity = identity.renderOptionally().orElseThrow(() -> new IllegalStateException(String.format("Identity '%s' had nothing to renderOptionally.", identity)));
        String renderedValue = value.renderOptionally().orElseThrow(() -> new IllegalStateException(String.format("Value '%s' had nothing to renderOptionally.", value)));
        return Optional.of(renderedIdentity + "=" + renderedValue + ";");
    }

    @Override
    public Prototype create(Node child) {
        return new DeclarePrototype(child);
    }

    @Override
    public Prototype create(Field field) {
        return new DeclarePrototype(field);
    }

    @Override
    public Prototype createWithChildren() {
        return streamChildren()
                .map(this::create)
                .reduce(createPrototype(), Prototype::merge);
    }

    @Override
    public Node transformFields(Function<Field, Field> mapping) {
        return new Declaration(mapping.apply(identity), value);
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        return new Declaration(identity, mapping.apply(value));
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    private static class DeclarePrototype implements Prototype {
        private final Field identity;
        private final Node value;

        public DeclarePrototype() {
            this(null, null);
        }

        public DeclarePrototype(Node value) {
            this(null, value);
        }

        public DeclarePrototype(Field identity) {
            this(identity, null);
        }

        public DeclarePrototype(Field identity, Node value) {
            this.identity = identity;
            this.value = value;
        }

        @Override
        public Prototype withField(Field field) {
            return new DeclarePrototype(field, value);
        }

        @Override
        public Prototype withChild(Node child) {
            return new DeclarePrototype(identity, child);
        }

        @Override
        public Node build() {
            if (identity == null) throw new IllegalStateException("No identity was provided.");
            if (value == null) throw new IllegalStateException("No value was provided.");
            return new Declaration(identity, value);
        }

        @Override
        public List<Node> listChildren() {
            return Collections.singletonList(value);
        }

        @Override
        public List<Field> listFields() {
            return Collections.singletonList(identity);
        }

        @Override
        public Prototype merge(Prototype other) {
            Prototype prototype = new DeclarePrototype();
            Prototype withIdentity = mergeIdentity(other).map(prototype::withField).orElse(prototype);
            return mergeValue(other).map(withIdentity::withChild).orElse(withIdentity);
        }

        private Optional<Field> mergeIdentity(Prototype other) {
            List<Field> identities = new ArrayList<>();
            identities.add(identity);
            identities.addAll(other.listFields());
            identities.removeIf(Objects::isNull);
            if (identities.isEmpty()) return Optional.empty();
            if (identities.size() > 1) throw new IllegalStateException("Too many identities were provided.");
            return Optional.ofNullable(identities.get(0));
        }

        private Optional<Node> mergeValue(Prototype other) {
            List<Node> children = new ArrayList<>();
            children.add(value);
            children.addAll(other.listChildren());
            children.removeIf(Objects::isNull);
            if (children.size() < 1) return Optional.empty();
            if (children.size() > 1) throw new IllegalStateException("Too many values were provided.");
            return Optional.ofNullable(children.get(0));
        }
    }
}
