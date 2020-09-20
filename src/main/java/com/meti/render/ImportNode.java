package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ImportNode extends ParentNode implements UntypedNode{
    private final Node value;

    public ImportNode(Node value) {
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Import);
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(value);
    }

    @Override
    public Prototype createPrototype() {
        return new PrototypeImpl(value);
    }

    @Override
    public Optional<String> render() {
        return value.render();
    }

    private static class PrototypeImpl implements Prototype {
        private final Node value;

        PrototypeImpl() {
            this(null);
        }

        PrototypeImpl(Node value) {
            this.value = value;
        }

        @Override
        public Prototype withField(Field field) {
            return this;
        }

        @Override
        public Prototype withChild(Node child) {
            return new PrototypeImpl(child);
        }

        @Override
        public Node build() {
            return new ImportNode(value);
        }
    }
}
