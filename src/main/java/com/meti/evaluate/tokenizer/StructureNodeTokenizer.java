package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.content.Strategy;
import com.meti.evaluate.FieldEvaluator;
import com.meti.render.Field;
import com.meti.render.Node;
import com.meti.render.StructureNode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StructureNodeTokenizer extends AbstractNodeTokenizer{
    public StructureNodeTokenizer(Content content) {
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
                List<Field> fields = fieldString.split(FieldStrategy::new)
                        .filter(Content::isPresent)
                        .map(FieldEvaluator::new)
                        .map(FieldEvaluator::evaluate)
                        .map(Optional::orElseThrow)
                        .collect(Collectors.toList());
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
                    .map(RootContent::new);
        }
    }
}
