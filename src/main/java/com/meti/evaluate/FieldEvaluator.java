package com.meti.evaluate;

import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.render.FieldFlag;
import com.meti.render.InlineField;
import com.meti.type.ContentType;
import com.meti.type.Type;

import java.util.*;
import java.util.stream.Collectors;

public class FieldEvaluator implements Evaluator<Field> {
    private final Content content;

    public FieldEvaluator(Content content) {
        this.content = content;
    }

    @Override
    public Optional<Field> evaluate() {
        OptionalInt separatorOptional = content.index(":");
        if (separatorOptional.isPresent()) {
            Field field = formatWithType(separatorOptional.orElseThrow());
            return Optional.of(field);
        } else {
            throw new UnsupportedOperationException("Implicit fields aren't supported yet.");
        }
    }

    private Field formatWithType(int typeSeparator) {
        Content keyString = content.slice(0, typeSeparator);
        OptionalInt optional = keyString.lastIndex(" ");
        if (optional.isEmpty()) {
            Content typeContent = content.sliceToEnd(typeSeparator + 1);
            Type type = new ContentType(typeContent);
            return keyString.value()
                    .append(type)
                    .apply((name, type1) -> new InlineField(name, type1, Collections.emptyList()));
        } else {
            int nameSeparator = optional.getAsInt();
            Content flagString = keyString.slice(0, nameSeparator);
            List<FieldFlag> flags = parseFlags(flagString);
            if(flags.contains(FieldFlag.CONST) || flags.contains(FieldFlag.LET)) {
                Content name = keyString.sliceToEnd(nameSeparator + 1);
                Content typeContent = content.sliceToEnd(typeSeparator + 1);
                Type type = new ContentType(typeContent);
                return name.value()
                        .append(type)
                        .apply((name1, type1) -> new InlineField(name1, type1, Collections.emptyList()));
            } else {
                String message = String.format("No valid flags found in '%s'", content);
                throw new IllegalStateException(message);
            }
        }
    }

    private List<FieldFlag> parseFlags(Content flagString) {
        return flagString.split(FlagStrategy::new)
                .filter(Content::isPresent)
                .map(this::prepareFlags)
                .collect(Collectors.toList());
    }

    private FieldFlag prepareFlags(Content content) {
        return content.value()
                .map(String::toUpperCase)
                .apply(FieldFlag::valueOf);
    }

}
