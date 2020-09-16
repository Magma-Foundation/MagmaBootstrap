package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstructionNode extends ParentNode {
    private final List<Node> children;

    public ConstructionNode(List<Node> children) {
        this.children = children;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Construction);
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
    public Optional<String> render() {
        return Optional.of(children.stream()
                .map(Node::render)
                .map(Optional::orElseThrow)
                .collect(Collectors.joining(",", "{" ,"}")));
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
            return new ConstructionNode(children);
        }
    }
}
