package com.meti.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;

public class IncludeNode extends LeafNode implements UntypedNode {
    private final Content value;

    public IncludeNode(Content value) {
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(value).map(function);
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Include);
    }

    @Override
    public Prototype createPrototype() {
        return new PrototypeImpl();
    }

    @Override
    public Optional<String> render() {
        return value.value()
                .map(s -> "#include <" + s + ".h>\n")
                .toOption();
    }

    private class PrototypeImpl implements Prototype {
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
            return new IncludeNode(value);
        }
    }
}
