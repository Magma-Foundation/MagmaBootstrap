package com.meti.feature.block.function;

import com.meti.feature.CompileException;
import com.meti.content.ChildContent;
import com.meti.content.Content;
import com.meti.content.Strategy;
import com.meti.feature.evaluate.FieldEvaluator;
import com.meti.feature.evaluate.FlagStrategy;
import com.meti.feature.render.*;
import com.meti.feature.render.Type;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionTokenizer extends AbstractNodeTokenizer {
    public FunctionTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        OptionalInt startOptional = content.index("(");
        OptionalInt endOptional = content.index(")");
        if (startOptional.isPresent() && endOptional.isPresent()) {
            int start = startOptional.getAsInt();
            int end = endOptional.getAsInt();
            return tokenizeWithParameters(start, end);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Node> tokenizeWithParameters(int start, int end) {
        List<Field> fields = parseFields(start, end);
        Content keyString = content.slice(0, start);
        OptionalInt separatorOptional = keyString.lastIndex(" ");
        Content name;
        List<Field.Flag> flags;
        if (separatorOptional.isPresent()) {
            int separator = separatorOptional.getAsInt();
            name = keyString.sliceToEnd(separator + 1);
            flags = parseFlags(keyString, separator);
        } else {
            name = content;
            flags = Collections.emptyList();
        }
        OptionalInt returnSeparator = content.indexFrom(":", end);
        OptionalInt contentSeparator = content.index("=>");
        if (returnSeparator.isPresent()) {
            int returnStart = returnSeparator.getAsInt();
            if (contentSeparator.isPresent()) {
                int returnEnd = contentSeparator.getAsInt();
                ContentType type = new ContentType(content.slice(returnStart + 1, returnEnd));
                Field identity = createIdentity(name, type, flags);
                return buildConcrete(fields, identity, parseContent(returnEnd));
            } else {
                final ContentType type = new ContentType(content.sliceToEnd(returnStart + 1));
                Field identity = createIdentity(name, type, flags);
                return buildAbstract(fields, identity);
            }
        } else {
            return Optional.empty();
        }
    }

    public List<Field.Flag> parseFlags(Content keyString, int separator) {
        try {
            return keyString.slice(0, separator)
                    .split(FlagStrategy::new)
                    .filter(Content::isPresent)
                    .map(this::mapToFlag)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new CompileException("Invalid flags in " + keyString, e);
        }
    }

    private Field.Flag mapToFlag(Content content) {
        return content.value().map(String::toUpperCase).apply(Field.Flag::valueOf);
    }

    private Optional<Node> buildAbstract(List<Field> parameters, Field identity) {
        return Optional.of(new Abstraction.Builder()
                .withIdentity(identity)
                .withParameters(parameters)
                .build());
    }

    private Optional<Node> buildConcrete(List<Field> parameters, Field identity, Node value) {
        return Optional.of(new Implementation.Builder()
                .withIdentity(identity)
                .withParameters(parameters)
                .withChild(value)
                .build());
    }

    private Field createIdentity(Content name, Type type, List<Field.Flag> flags) {
        return name.value().append(type).append(flags).apply(InlineField::new);
    }

    private Node parseContent(int returnEnd) {
        Content valueContent = content.sliceToEnd(returnEnd + 2);
        return new ContentNode(valueContent);
    }

    private List<Field> parseFields(int start, int end) {
        return content.slice(start + 1, end).split(ParameterStrategy::new)
                .filter(Content::isPresent)
                .map(FieldEvaluator::new)
                .map(FieldEvaluator::evaluate)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    //TODO: make immutable
    private static class ParameterStrategy implements Strategy {
        private final Content parent;
        private final List<Content> arguments = new ArrayList<>();
        private StringBuilder builder = new StringBuilder();
        private int start = 0;
        private int end = 0;
        private int depth = 0;

        public ParameterStrategy(Content parent) {
            this.parent = parent;
        }

        @Override
        public Stream<Content> split() {
            int length = parent.length();
            for (int i = 0; i < length; i++) {
                char c = parent.apply(i);
                if (c == ',' && depth == 0) {
                    append();
                } else {
                    if (c == '(') depth++;
                    if (c == ')') depth--;
                    builder.append(c);
                    end++;
                }
            }
            append();
            return arguments.stream();
        }

        private void append() {
            String value = builder.toString();
            builder = new StringBuilder();
            if (!value.isEmpty()) {
                ChildContent child = new ChildContent(parent, value, start, end);
                arguments.add(child);
                start = end;
            }
        }
    }
}
