package com.meti;

import java.util.Optional;
import java.util.OptionalInt;

public class FieldEvaluator implements Evaluator<Field> {
    private final Content content;

    public FieldEvaluator(Content content) {
        this.content = content;
    }

    @Override
    public Optional<Field> evaluate() {
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
