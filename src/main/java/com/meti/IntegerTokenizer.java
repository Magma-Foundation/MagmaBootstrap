package com.meti;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class IntegerTokenizer extends AbstractTokenizer {
    public static final BigInteger START = BigInteger.valueOf(-32767);
    public static final BigInteger END = BigInteger.valueOf(32767);

    public IntegerTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        try {
            BigInteger capsule = content.value().apply(BigInteger::new);
            //checking range per https://en.wikipedia.org/wiki/C_data_types
            if (capsule.compareTo(START) > 0 && capsule.compareTo(END) < 0) {
                int value = capsule.intValueExact();
                return Optional.of(new IntNode(value));
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static class IntNode implements Node {
        private final int value;

        public IntNode(int value) {
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
            return Stream.empty();
        }

        @Override
        public Prototype createPrototype() {
            return new IntPrototype();
        }

        @Override
        public Optional<String> render() {
            return Optional.of(String.valueOf(value));
        }

        @Override
        public Monad<NodeGroup> group(){
            return new Monad<>(NodeGroup.Integer);
        }

        private class IntPrototype implements Prototype {
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
                return new IntNode(value);
            }
        }
    }
}
