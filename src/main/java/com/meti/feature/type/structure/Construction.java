package com.meti.feature.type.structure;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Parent;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Construction extends Parent {
    private final List<Node> children;

    public Construction(List<Node> children) {
        this.children = children;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Construction);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamChildren() {
        return children.stream();
    }

    @Override
    public Prototype createPrototype() {
        return new ConstructionPrototype();
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.of(children.stream()
                .map(Node::renderOptionally)
                .map(Optional::orElseThrow)
                .collect(Collectors.joining(",", "{" ,"}")));
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
        List<Node> newChildren = children.stream().map(mapping).collect(Collectors.toList());
        return new Construction(newChildren);
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    private static class ConstructionPrototype implements Prototype {
        private final List<Node> children;

        private ConstructionPrototype() {
            this(Collections.emptyList());
        }

        private ConstructionPrototype(List<Node> children) {
            this.children = children;
        }

        @Override
        public Prototype withField(Field field) {
            return this;
        }

        @Override
        public Prototype withChild(Node child) {
            List<Node> newChildren = new ArrayList<>(children);
            newChildren.add(child);
            return new ConstructionPrototype(newChildren);
        }

        @Override
        public Node build() {
            return new Construction(children);
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
