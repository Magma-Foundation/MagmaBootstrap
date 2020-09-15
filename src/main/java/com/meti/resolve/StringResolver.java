package com.meti.resolve;

import com.meti.content.Content;
import com.meti.type.PrimitiveType;
import com.meti.type.Type;

import java.util.Optional;
import java.util.function.Function;

public class StringResolver extends AbstractResolver {
    public StringResolver(Type previous) {
        super(previous);
    }

    @Override
    public Optional<Type> resolve() {
        return previous.applyToContent(this::isString).flatMap(Function.identity());
    }

    private Optional<Type> isString(Content content) {
        if(content.value().test("String"::equals)) {
            return Optional.of(new PointerType(PrimitiveType.CHAR));
        } else {
            return Optional.empty();
        }
    }
}
