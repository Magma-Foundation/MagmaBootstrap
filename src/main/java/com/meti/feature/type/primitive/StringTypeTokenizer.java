package com.meti.feature.type.primitive;

import com.meti.content.Content;
import com.meti.feature.type.point.Pointer;
import com.meti.feature.render.Type;
import com.meti.resolve.AbstractTypeTokenizer;

import java.util.Optional;
import java.util.function.Function;

public class StringTypeTokenizer extends AbstractTypeTokenizer {
    public StringTypeTokenizer(Type previous) {
        super(previous);
    }

    @Override
    public Optional<Type> tokenize() {
        return previous.applyToContent(this::isString).flatMap(Function.identity());
    }

    private Optional<Type> isString(Content content) {
        if(content.value().test("String"::equals)) {
            return Optional.of(Pointer.to(PrimitiveType.CHAR));
        } else {
            return Optional.empty();
        }
    }
}
