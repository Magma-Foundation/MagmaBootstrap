package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockNode extends ParentNode {
    private final List<Node> children;

    public BlockNode(Node... children) {
        this(List.of(children));
    }

    public BlockNode(List<Node> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockNode blockNode = (BlockNode) o;
        return children.containsAll(blockNode.children) &&
                blockNode.children.containsAll(children);
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
    public Stream<Field> streamFields() {
        return Stream.empty();
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
    public Optional<String> render() {
        return Optional.of(children.stream()
                .map(Node::render)
                .flatMap(Optional::stream)
                .collect(Collectors.joining("", "{", "}")));
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Block);
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
            return new BlockNode(children);
        }
    }
}
