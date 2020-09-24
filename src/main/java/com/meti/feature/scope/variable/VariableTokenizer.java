package com.meti.feature.scope.variable;

import com.meti.content.Content;
import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.feature.render.Node;

import java.util.Optional;

public class VariableTokenizer extends AbstractNodeTokenizer {
    public VariableTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        return Optional.of(new Variable(content));
    }

}
