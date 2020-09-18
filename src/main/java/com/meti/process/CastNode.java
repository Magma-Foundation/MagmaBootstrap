package com.meti.process;

import com.meti.render.Field;
import com.meti.render.InlineField;
import com.meti.render.Node;
import com.meti.render.NodeGroup;
import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class CastNode extends FreeNode {
    private final Type type;
    private final Node value;

    public CastNode(Type type, Node value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Cast);
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.of(new InlineField(type));
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(value);
    }

    @Override
    public Prototype createPrototype() {
        return new CastPrototype(type);
    }

    @Override
    public Optional<String> render() {
        return type.render()
                .map(String::trim)
                .map(this::formatCast)
                .map(this::formatValue);
    }

    private String formatValue(String value) {
        return value + this.value.render().orElseThrow();
    }

    private String formatCast(String value) {
        return String.format("(%s)", value);
    }

    private static class CastPrototype implements Prototype {
        private final Type type;
        private final Node value;

        private CastPrototype(Type type) {
            this(type, null);
        }

        private CastPrototype(Type type, Node value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public Prototype withField(Field field) {
            return field.type()
                    .append(value)
                    .apply(CastPrototype::new);
        }

        @Override
        public Prototype withChild(Node child) {
            return new CastPrototype(type, child);
        }

        @Override
        public Node build() {
            return new CastNode(type, value);
        }
    }
}
