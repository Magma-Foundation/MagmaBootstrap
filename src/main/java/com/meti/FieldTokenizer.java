package com.meti;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

public class FieldTokenizer implements Tokenizer<Field> {
    private final Content content;

    public FieldTokenizer(Content content) {
        this.content = content;
    }

    @Override
    public Optional<Field> tokenize() {
        OptionalInt separatorOptional = content.index(":");
        if (separatorOptional.isPresent()) {
            int separator = separatorOptional.getAsInt();
            Content name = content.slice(0, separator);
            Content typeContent = content.sliceToEnd(separator + 1);
            Type type = new ContentType(typeContent);
            Field field = name.value()
                    .append(type)
                    .apply(InlineField::new);
            return Optional.of(field);
        } else {
            return Optional.empty();
        }
    }
}
