package com.meti.feature.block.function;

import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.*;
import com.meti.stack.CallStack;
import com.meti.util.Monad;
import com.meti.content.Content;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReturnTokenizer extends AbstractNodeTokenizer {
    public ReturnTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if(content.startsWith("return ")) {
            Content value = content.sliceToEnd(7);
            Node node = new ContentNode(value);
            return Optional.of(new Return(node));
        }
        return Optional.empty();
    }

    private static class ReturnPrototype implements Node.Prototype {
        private final Node value;

        private ReturnPrototype() {
            this(null);
        }

        private ReturnPrototype(Node value) {
            this.value = value;
        }

        @Override
        public Node.Prototype withField(Field field) {
            return this;
        }

        @Override
        public Node.Prototype withChild(Node child) {
            return new ReturnPrototype(child);
        }

        @Override
        public Node build() {
            if(value == null) throw new IllegalStateException("Value is not set.");
            return new Return(value);
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
        public Node.Prototype merge(Node.Prototype other) {
            throw new UnsupportedOperationException();
        }
    }

    private static class Return extends Parent {
        private final Node value;

        public Return(Node value) {
            this.value = value;
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
            return Stream.of(value);
        }

        @Override
        public Prototype createPrototype() {
            return new ReturnPrototype();
        }

        @Override
        public Optional<String> renderOptionally() {
            return Optional.of("return " + value.render() + ";");
        }

        @Override
        public Monad<Group> group(){
            return new Monad<>(Group.Return);
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
            return new Return(mapping.apply(value));
        }

        @Override
        public boolean matches(Type value, CallStack stack) {
            throw new UnsupportedOperationException();
        }
    }
}
