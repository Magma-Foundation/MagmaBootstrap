package com.meti.evaluate.tokenizer;

import com.meti.content.Strategy;
import com.meti.content.ChildContent;
import com.meti.content.Content;
import com.meti.render.BlockNode;
import com.meti.render.ContentNode;
import com.meti.render.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockTokenizer extends AbstractTokenizer {
    public BlockTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if (content.startsWith("{") && content.endsWith("}")) {
            Content value = content.slice(1, content.length() - 1);
            List<Node> children = value.split(LineStrategy::new)
                    .filter(Content::isPresent)
                    .map(ContentNode::new)
                    .collect(Collectors.toList());
            return Optional.of(new BlockNode(children));
        }
        return Optional.empty();
    }

    private static class LineStrategy implements Strategy {
        private final Content content;
        private final List<Content> lines = new ArrayList<>();
        private StringBuilder buffer = new StringBuilder();
        private int start = 0;
        private int end = 0;
        private int depth = 0;

        public LineStrategy(Content content) {
            this.content = content;
        }

        @Override
        public Stream<Content> split() {
            for (int i = 0; i < content.length(); i++) {
                char c = content.apply(i);
                if (c == ';' && depth == 0) {
                    complete();
                } else if (c == '}' && depth == 1) {
                    buffer.append('}');
                    complete();
                } else {
                    if (c == '{') depth++;
                    if (c == '}') depth--;
                    end++;
                    buffer.append(c);
                }
            }
            complete();
            return lines.stream();
        }

        private void complete() {
            Content child = new ChildContent(content, buffer.toString(), start, end);
            lines.add(child);
            buffer = new StringBuilder();
            start = end;
        }
    }
}
