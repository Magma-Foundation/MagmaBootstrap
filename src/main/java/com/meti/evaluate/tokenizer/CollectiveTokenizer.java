package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.evaluate.Evaluator;
import com.meti.render.Node;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class CollectiveTokenizer extends AbstractTokenizer {
    public CollectiveTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        return streamFactories()
                .map(factory -> factory.apply(content))
                .map(Evaluator::evaluate)
                .flatMap(Optional::stream)
                .findFirst();
    }

    protected abstract Stream<Function<Content, Evaluator<Node>>> streamFactories();
}
