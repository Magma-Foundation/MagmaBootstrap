package com.meti.evaluate.tokenizer;

import com.meti.render.LeafNode;
import com.meti.util.Monad;
import com.meti.render.NodeGroup;
import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.render.Node;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class IntTokenizer extends AbstractNodeTokenizer {
    public static final BigInteger START = BigInteger.valueOf(-32767);
    public static final BigInteger END = BigInteger.valueOf(32767);

    public IntTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        try {
            BigInteger capsule = content.value().apply(BigInteger::new);
            //checking range per https://en.wikipedia.org/wiki/C_data_types
            if (capsule.compareTo(START) > 0 && capsule.compareTo(END) < 0) {
                int value = capsule.intValueExact();
                return Optional.of(new Int(value));
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static class Int extends LeafNode {
        private final int value;

        public Int(int value) {
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
                return new Int(value);
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
}
