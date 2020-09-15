package com.meti.resolve;

import com.meti.content.Content;
import com.meti.type.ContentType;
import com.meti.type.Type;

import java.util.Optional;
import java.util.function.Function;

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
}
