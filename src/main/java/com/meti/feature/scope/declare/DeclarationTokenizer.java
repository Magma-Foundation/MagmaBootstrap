package com.meti.feature.scope.declare;

import com.meti.feature.CompileException;
import com.meti.content.Content;
import com.meti.feature.evaluate.FieldEvaluator;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.ContentNode;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;

import java.util.*;
import java.util.function.Supplier;

public class DeclarationTokenizer extends AbstractNodeTokenizer {
    public DeclarationTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        OptionalInt optional = content.index("=");
        if (optional.isPresent()) {
            int separator = optional.getAsInt();
            Content identityContent = content.slice(0, separator);
            Field identity = new FieldEvaluator(identityContent)
                    .evaluate()
                    .orElseThrow(createIdentityFailure(identityContent));
            Content valueContent = content.sliceToEnd(separator + 1);
            Node value = new ContentNode(valueContent);
            return Optional.of(new Declaration(identity, value));
        }
        return Optional.empty();
    }

    private Supplier<CompileException> createIdentityFailure(Content identityContent) {
        return () -> {
            String message = String.format("Failed to create identity for '%s'", identityContent);
            return new CompileException(message);
        };
    }

}
