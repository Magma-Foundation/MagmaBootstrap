package com.meti.feature.block.invoke;

import com.meti.content.ChildContent;
import com.meti.content.Content;
import com.meti.content.Strategy;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.ContentNode;
import com.meti.feature.render.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class InvocationTokenizer extends AbstractNodeTokenizer {
    public InvocationTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if(content.endsWith(")")) {
            //TODO: replace with more complex indexing system, will fail in calls like "printf(getValue())
            OptionalInt startOptional = content.index("(");
            if(startOptional.isPresent()) {
                int separator = startOptional.getAsInt();
                Content callerContent = content.slice(0, separator);
                Node caller = new ContentNode(callerContent);
                Content slice = content.slice(separator + 1, content.length() - 1);
                List<Node> arguments = slice.split(ArgumentStrategy::new)
                        .filter(Content::isPresent)
                        .map(ContentNode::new)
                        .reduce(new ArrayList<>(), this::join, (contents, contents2) -> contents2);
                return Optional.of(new Mapping(caller, arguments));
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private <T> ArrayList<T> join(ArrayList<T> list, T value) {
        list.add(value);
        return list;
    }

    private static class ArgumentStrategy implements Strategy {
        private final List<Content> children;
        private final Content content;
        private StringBuilder buffer;
        private int start;
        private int end;
        private int depth;

        public ArgumentStrategy(Content content) {
            this.content = content;
            children = new ArrayList<>();
            buffer = new StringBuilder();
            start = 0;
            end = 0;
        }

        @Override
        public Stream<Content> split() {
            for (int i = 0; i < content.length() ; i++) {
                char c = content.apply(i);
                if(c == ',' && depth == 0) {
                    complete();
                } else {
                    if(c == '(') depth++;
                    if(c == ')') depth--;
                    buffer.append(c);
                    end++;
                }
            }
            complete();
            return children.stream();
        }

        private void complete() {
            ChildContent child = new ChildContent(content, buffer.toString(), start, end);
            children.add(child);
            buffer = new StringBuilder();
            start = end;
        }
    }
}
