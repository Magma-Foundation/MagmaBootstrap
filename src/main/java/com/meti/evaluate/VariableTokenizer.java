package com.meti.evaluate;

import com.meti.util.Monad;
import com.meti.render.NodeGroup;
import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.render.Node;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class VariableTokenizer extends AbstractTokenizer {
    public VariableTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        return Optional.of(new VariableNode(content));
    }

    private static class VariableNode implements Node {
        private final Content content;

        public VariableNode(Content content) {
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
            return new VariableTokenizer.VariablePrototype(content);
        }

        @Override
        public Optional<String> render() {
            return content.value().toOption();
        }

        @Override
        public Monad<NodeGroup> group(){
            return new Monad<>(NodeGroup.Variable);
        }
    }

    private static class VariablePrototype implements Node.Prototype {
        private final Content content;

        public VariablePrototype(Content content) {
            this.content = content;
        }

        @Override
        public Node.Prototype withField(Field field) {
            return this;
        }

        @Override
        public Node.Prototype withChild(Node child) {
            return this;
        }

        @Override
        public Node build() {
            return new VariableNode(content);
        }
    }
}
