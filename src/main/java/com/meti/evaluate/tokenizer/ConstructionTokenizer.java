package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.content.Strategy;
import com.meti.render.ConstructionNode;
import com.meti.render.ContentNode;
import com.meti.render.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstructionTokenizer extends AbstractNodeTokenizer {
    public ConstructionTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if (content.startsWith("<") && content.endsWith("}")) {
            OptionalInt optional = content.index("{");
            if (optional.isPresent()) {
                int separator = optional.getAsInt();
                Content slice = content.slice(separator + 1, content.length() - 1);
                List<Node> children = slice.split(ConstructionStrategy::new)
                        .filter(Content::isPresent)
                        .map(ContentNode::new)
                        .collect(Collectors.toList());
                return Optional.of(new ConstructionNode(children));
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private static class ConstructionStrategy implements Strategy {
        private final Content content;

        public ConstructionStrategy(Content content) {
            this.content = content;
        }

        @Override
        public Stream<Content> split() {
            return content.value()
                    .map(value -> value.split(","))
                    .apply(Arrays::stream)
                    .map(RootContent::new);
        }
    }
}
