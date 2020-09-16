package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.render.ContentNode;
import com.meti.render.Node;
import com.meti.render.ReferenceNode;

import java.util.Optional;

public class ReferenceNodeTokenizer extends AbstractNodeTokenizer {
    public ReferenceNodeTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if(content.startsWith("&")) {
            Content valueContent = this.content.sliceToEnd(1);
            Node value = new ContentNode(valueContent);
            return Optional.of(new ReferenceNode(value));
        }
        return Optional.empty();
    }
}
