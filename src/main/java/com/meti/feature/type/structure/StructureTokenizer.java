package com.meti.feature.type.structure;

import com.meti.content.Content;
import com.meti.content.StringContent;
import com.meti.content.Strategy;
import com.meti.feature.evaluate.FieldEvaluator;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StructureTokenizer extends AbstractNodeTokenizer {
    public StructureTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if(content.startsWith("struct ") && content.endsWith("}")) {
            OptionalInt separatorOptional = content.index("{");
            if(separatorOptional.isPresent()) {
                int separator = separatorOptional.getAsInt();
                Content name = content.slice(7, separator);
                Content fieldString = content.slice(separator + 1, content.length() - 1);
                Set<Field> fields = fieldString.split(FieldStrategy::new)
                        .filter(Content::isPresent)
                        .map(FieldEvaluator::new)
                        .map(FieldEvaluator::evaluate)
                        .map(Optional::orElseThrow)
                        .collect(Collectors.toSet());
                return Optional.of(new StructureNode(name, fields));
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private static class FieldStrategy implements Strategy {
        private final Content content;

        public FieldStrategy(Content content) {
            this.content = content;
        }

        @Override
        public Stream<Content> split() {
            return content.value()
                    .map(s-> s.split(";"))
                    .apply(Arrays::stream)
                    .map(StringContent::new);
        }
    }
}
