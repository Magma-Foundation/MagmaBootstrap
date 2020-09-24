package com.meti.feature.scope.variable;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Leaf;
import com.meti.feature.render.Node;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Variable extends Leaf {
    private final Content content;

    public Variable(Content content) {
        this.content = content;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(content).map(function);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.empty();
    }

    @Override
    public Prototype createPrototype() {
        return new PrototypeImpl(content);
    }

    @Override
    public Optional<String> renderOptionally() {
        return content.value().toOption();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Variable);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(content, variable.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
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
    public boolean matches(Type value, CallStack stack) {
        return stack.matches(content.asString(), value);
    }

    @Override
    public boolean doesReturn(Type value, CallStack stack) {
        return stack.doesReturn(content.asString(), value);
    }

    private static class PrototypeImpl implements Prototype {
        private final Content content;

        public PrototypeImpl(Content content) {
            this.content = content;
        }

        @Override
        public Prototype withField(Field field) {
            return this;
        }

        @Override
        public Prototype withChild(Node child) {
            return this;
        }

        @Override
        public Node build() {
            return new Variable(content);
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
