package com.meti.feature.block.function;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.InlineField;
import com.meti.feature.render.Node;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Abstraction extends Function_ {
    public Abstraction(Field identity, List<Field> parameters) {
        super(identity, parameters);
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Prototype createPrototype() {
        return new Builder();
    }

    @Override
    protected String complete() {
        return "";
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.empty();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Abstraction);
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
        return new Abstraction(newIdentity, newParameters);
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        return this;
    }

    public static class Builder implements Prototype {
        private final List<Field> fields;

        public Builder() {
            this(Collections.emptyList());
        }

        public Builder(List<Field> fields) {
            this.fields = new ArrayList<>(fields);
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
            return new Builder(newFields);
        }

        @Override
        public Prototype withChild(Node child) {
            return new Builder(fields);
        }

        @Override
        public Node build() {
            if (fields.isEmpty()) {
                throw new IllegalStateException("No return type was provided.");
            } else {
                return fields.get(0).destroy().apply(this::assemble);
            }
        }

        private Abstraction assemble(String name, Type returnType, List<Field.Flag> flags) {
            List<Field> parameters = fields.subList(1, fields.size());
            Field identity = new InlineField(name, new FunctionType(returnType, parameters), flags);
            return new Abstraction(identity, parameters);
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
