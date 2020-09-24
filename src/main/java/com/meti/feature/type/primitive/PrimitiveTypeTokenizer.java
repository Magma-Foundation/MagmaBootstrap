package com.meti.feature.type.primitive;

import com.meti.content.Content;
import com.meti.feature.render.Type;
import com.meti.resolve.AbstractTypeTokenizer;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class PrimitiveTypeTokenizer extends AbstractTypeTokenizer {
    public PrimitiveTypeTokenizer(Type previous) {
        super(previous);
    }

    @Override
    public Optional<Type> tokenize() {
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
