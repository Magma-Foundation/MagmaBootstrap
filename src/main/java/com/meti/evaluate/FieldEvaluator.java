package com.meti.evaluate;

import com.meti.content.ChildContent;
import com.meti.content.Content;
import com.meti.content.Strategy;
import com.meti.render.Field;
import com.meti.render.FieldFlag;
import com.meti.render.InlineField;
import com.meti.type.ContentType;
import com.meti.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                    .apply(InlineField::new);
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
                        .apply(InlineField::new);
            } else {
                String message = String.format("No valid flags found in '%s'", content);
                throw new IllegalStateException(message);
            }
        }
    }

    private List<FieldFlag> parseFlags(Content flagString) {
        List<FieldFlag> flags = flagString.split(FlagStrategy::new)
                .filter(Content::isPresent)
                .map(this::prepareFlags)
                .collect(Collectors.toList());
        return flags;
    }

    private FieldFlag prepareFlags(Content content) {
        return content.value()
                .map(String::toUpperCase)
                .apply(FieldFlag::valueOf);
    }

    private static class FlagStrategy implements Strategy {
        private final List<Content> values;
        private final Content content;
        private StringBuilder builder;
        private int start;
        private int end;

        public FlagStrategy(Content content) {
            this.content = content;
            values = new ArrayList<>();
            builder = new StringBuilder();
            start = 0;
            end = 0;
        }

        @Override
        public Stream<Content> split() {
            for (int i = 0; i < content.length(); i++) {
                char c = content.apply(i);
                if(c == ' ') {
                    complete();
                    builder = new StringBuilder();
                    start = end;
                } else {
                    builder.append(c);
                    end++;
                }
            }
            complete();
            return values.stream();
        }

        private void complete() {
            values.add(new ChildContent(content, builder.toString(), start, end));
        }
    }
}
