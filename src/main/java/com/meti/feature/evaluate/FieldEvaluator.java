package com.meti.feature.evaluate;

import com.meti.content.Content;
import com.meti.feature.render.InlineField;
import com.meti.feature.render.Type;
import com.meti.feature.render.ContentType;
import com.meti.feature.render.Field;

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
            String message = String.format("Implicit fields aren't supported yet in '%s'", content);
            throw new UnsupportedOperationException(message);
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
            List<Field.Flag> flags = parseFlags(flagString);
            if(flags.contains(Field.Flag.CONST) || flags.contains(Field.Flag.LET)) {
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

    private List<Field.Flag> parseFlags(Content flagString) {
        return flagString.split(FlagStrategy::new)
                .filter(Content::isPresent)
                .map(this::prepareFlags)
                .collect(Collectors.toList());
    }

    private Field.Flag prepareFlags(Content content) {
        return content.value()
                .map(String::toUpperCase)
                .apply(Field.Flag::valueOf);
    }

}
