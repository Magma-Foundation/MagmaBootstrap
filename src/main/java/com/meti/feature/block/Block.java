package com.meti.feature.block;

import com.meti.content.Content;
import com.meti.feature.render.*;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Block extends Parent implements Untyped {
    private final List<Node> children;

    public Block(Node... children) {
        this(List.of(children));
    }

    public Block(List<Node> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return children.containsAll(block.children) &&
                block.children.containsAll(children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Stream<Node> streamChildren() {
        return children.stream();
    }

    @Override
    public Prototype createPrototype() {
        return new BlockPrototype();
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.of(children.stream()
                .map(Node::render)
                .collect(Collectors.joining("", "{", "}")));
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Block);
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
    public Node transformChildren(Function<Node, Node> mapping) {
        List<Node> newChildren = children.stream()
                .map(mapping)
                .collect(Collectors.toList());
        return new Block(newChildren);
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }

    private static class BlockPrototype implements Prototype {
        private final List<Node> children;

        private BlockPrototype() {
            this(new ArrayList<>());
        }

        private BlockPrototype(List<Node> children) {
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
            return new BlockPrototype(newChildren);
        }

        @Override
        public Node build() {
            return new Block(children);
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
