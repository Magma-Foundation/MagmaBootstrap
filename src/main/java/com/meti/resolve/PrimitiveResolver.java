package com.meti.resolve;

import com.meti.content.Content;
import com.meti.type.PrimitiveType;
import com.meti.type.Type;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class PrimitiveResolver extends AbstractResolver {
    public PrimitiveResolver(Type previous) {
        super(previous);
    }

    @Override
    public Optional<Type> resolve() {
        return previous.applyToContent(this::find).flatMap(Function.identity());
    }

    private Optional<? extends Type> find(Content content) {
        return Arrays.stream(PrimitiveType.values())
                .filter(primitiveType -> isInstance(content, primitiveType))
                .findFirst();
    }

    private boolean isInstance(Content content, PrimitiveType primitiveType) {
        return content.value()
                .map(String::toUpperCase)
                .test(primitiveType.name()::equals);
    }
}
