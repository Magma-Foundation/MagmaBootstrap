package com.meti.resolve;

import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.type.ContentType;
import com.meti.type.Type;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class PointerResolver extends AbstractResolver {
    public PointerResolver(Type previous) {
        super(previous);
    }

    @Override
    public Optional<Type> resolve() {
        return previous.applyToContent(this::resolveContent)
                .flatMap(Function.identity());
    }

    private Optional<Type> resolveContent(Content content) {
        if (content.startsWith("Pointer")) {
            Content value = content.slice(8, content.length() - 1);
            ContentType child = new ContentType(value);
            PointerType value1 = new PointerType(child);
            return Optional.of(value1);
        }
        return Optional.empty();
    }

    private static class PointerType implements Type {
        private final Type child;

        private PointerType(Type child) {
            this.child = child;
        }

        @Override
        public <R> Optional<R> applyToContent(Function<Content, R> function) {
            return Optional.empty();
        }

        @Override
        public Optional<String> render(String name) {
            return Optional.of(child.render("* " + name).orElseThrow());
        }

        @Override
        public Prototype createPrototype() {
            return new PointerResolver.PointerPrototype(child);
        }

        @Override
        public Stream<Type> streamChildren() {
            return Stream.of(child);
        }

        @Override
        public Stream<Field> streamFields() {
            return Stream.empty();
        }
    }

    private static class PointerPrototype implements Type.Prototype {
        private final Type child;

        public PointerPrototype() {
            this(null);
        }

        public PointerPrototype(Type child) {
            this.child = child;
        }

        @Override
        public Type.Prototype withChild(Type child) {
            return new PointerPrototype(child);
        }

        @Override
        public Type.Prototype withField(Field field) {
            return this;
        }

        @Override
        public Type build() {
            if(child == null) throw new IllegalStateException("Pointer child is null.");
            return new PointerType(child);
        }
    }
}
