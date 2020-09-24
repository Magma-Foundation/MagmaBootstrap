package com.meti.feature.block.function;

import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

class Implementation extends Function_ {
    private final Node value;

    public Implementation(Field identity, List<Field> parameters, Node value) {
        super(identity, parameters);
        this.value = value;
    }

    @Override
    public Prototype createPrototype() {
        return new Builder();
    }

    @Override
    protected String complete() {
        return value.render();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(value);
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Implementation);
    }

    @Override
    public Prototype create(Node child) {
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
    protected Node complete(Field newIdentity, List<Field> newParameters) {
        return new Implementation(newIdentity, newParameters, value);
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        return new Implementation(identity, parameters, mapping.apply(value));
    }

    public static class Builder implements Prototype {
        private final List<Field> fields;
        private final Node value;

        public Builder() {
            this(Collections.emptyList(), null);
        }

        public Builder(List<Field> fields, Node value) {
            this.fields = new ArrayList<>(fields);
            this.value = value;
        }

        public Builder withIdentity(Field identity) {
            if (fields.isEmpty()) fields.add(identity);
            else fields.set(0, identity);
            return this;
        }

        public Builder withParameters(List<Field> parameters) {
            this.fields.addAll(parameters);
            return this;
        }

        @Override
        public Prototype withField(Field field) {
            List<Field> newFields = new ArrayList<>(this.fields);
            newFields.add(field);
            return new Builder(newFields, value);
        }

        @Override
        public Prototype withChild(Node child) {
            return new Builder(fields, child);
        }

        @Override
        public Node build() {
            if (fields.isEmpty()) {
                throw new IllegalStateException("No return type was provided.");
            } else {
                return fields.get(0).applyDestruction((String name, Type name2) -> createNode(name));
            }
        }

        private Function_ createNode(String name) {
            List<Field> parameters = fields.subList(1, fields.size());
            if (value == null) {
                String message = String.format("Concrete function '%s' must have a value.", name);
                throw new IllegalStateException(message);
            }
            return new Implementation(fields.get(0), parameters, value);
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
