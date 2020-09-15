package com.meti.evaluate.tokenizer;

import com.meti.CompileException;
import com.meti.content.Content;
import com.meti.evaluate.FieldEvaluator;
import com.meti.render.*;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DeclareTokenizer extends AbstractTokenizer {
    public DeclareTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        OptionalInt optional = content.index("=");
        if (optional.isPresent()) {
            int separator = optional.getAsInt();
            Content identityContent = content.slice(0, separator);
            Field identity = new FieldEvaluator(identityContent)
                    .evaluate()
                    .orElseThrow(createIdentityFailure(identityContent));
            Content valueContent = content.sliceToEnd(separator + 1);
            Node value = new ContentNode(valueContent);
            return Optional.of(new DeclareNode(identity, value));
        }
        return Optional.empty();
    }

    private Supplier<CompileException> createIdentityFailure(Content identityContent) {
        return () -> {
            String message = String.format("Failed to create identity for '%s'", identityContent);
            return new CompileException(message);
        };
    }

    private static class DeclareNode extends ParentNode {
        private final Field identity;
        private final Node value;

        public DeclareNode(Field identity, Node value) {
            this.identity = identity;
            this.value = value;
        }

        @Override
        public <R> Optional<R> applyToContent(Function<Content, R> function) {
            return Optional.empty();
        }

        @Override
        public Monad<NodeGroup> group() {
            return new Monad<>(NodeGroup.Declare);
        }

        @Override
        public Stream<Field> streamFields() {
            return Stream.of(identity);
        }

        @Override
        public Stream<Node> streamChildren() {
            return Stream.of(value);
        }

        @Override
        public Prototype createPrototype() {
            return new DeclarePrototype();
        }

        @Override
        public Optional<String> render() {
            String renderedIdentity = identity.render().orElseThrow(() -> new IllegalStateException(String.format("Identity '%s' had nothing to render.", identity)));
            String renderedValue = value.render().orElseThrow(() -> new IllegalStateException(String.format("Value '%s' had nothing to render.", value)));
            return Optional.of(renderedIdentity + "=" + renderedValue + ";");
        }

        private static class DeclarePrototype implements Prototype {
            private final Field identity;
            private final Node value;

            public DeclarePrototype() {
                this(null, null);
            }

            public DeclarePrototype(Field identity, Node value) {
                this.identity = identity;
                this.value = value;
            }

            @Override
            public Prototype withField(Field field) {
                return new DeclarePrototype(field, value);
            }

            @Override
            public Prototype withChild(Node child) {
                return new DeclarePrototype(identity, child);
            }

            @Override
            public Node build() {
                if (identity == null) throw new IllegalStateException("No identity was provided.");
                if (value == null) throw new IllegalStateException("No value was provided.");
                return new DeclareNode(identity, value);
            }
        }
    }
}
