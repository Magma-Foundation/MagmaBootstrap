package com.meti.feature.type.primitive;

import com.meti.content.Content;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.Node;

import java.util.Optional;

public class StringTokenizer extends AbstractNodeTokenizer {
    public StringTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if (content.startsWith("\"") && content.endsWith("\"")) {
            Content valueContent = content.slice(1, content.length() - 1);
            Node node = new String_(valueContent.asString());
            return Optional.of(node);
        }
        return Optional.empty();
    }
}
