package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;

public class EmptyNode extends LeafNode implements UntypedNode {
    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Empty);
    }

    @Override
    public Prototype createPrototype() {
        return new EmptyPrototype();
    }

    @Override
    public Optional<String> render() {
        return Optional.of("");
    }

    private static class EmptyPrototype implements Prototype {
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
            return new EmptyNode();
        }
    }
}
