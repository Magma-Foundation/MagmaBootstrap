package com.meti.feature.type.structure;

import com.meti.content.Content;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.ContentNode;
import com.meti.feature.render.Node;

import java.util.Optional;
import java.util.OptionalInt;

public class FieldTokenizer extends AbstractNodeTokenizer {
    public FieldTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        OptionalInt separatorOptional = content.index(".");
        if (separatorOptional.isPresent()) {
            int separator = separatorOptional.getAsInt();
            Content parentContent = content.slice(0, separator);
            Node parent = new ContentNode(parentContent);
            Content fieldName = content.sliceToEnd(separator + 1);
            return Optional.of(new FieldNode(parent, fieldName));
        }
        return Optional.empty();
    }
}
