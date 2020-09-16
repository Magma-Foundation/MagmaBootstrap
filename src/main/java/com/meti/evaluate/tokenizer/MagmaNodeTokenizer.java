package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.evaluate.Evaluator;
import com.meti.render.Node;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaNodeTokenizer extends CollectiveNodeTokenizer {
    public MagmaNodeTokenizer(Content content) {
        super(content);
    }

    @Override
    protected Stream<Function<Content, Evaluator<Node>>> streamFactories() {
        return Stream.of(
                DereferenceNodeTokenizer::new,
                ReferenceNodeTokenizer::new,
                ReturnNodeTokenizer::new,
                InvocationNodeTokenizer::new,
                BlockNodeTokenizer::new,
                FunctionNodeTokenizer::new,
                IntegerNodeTokenizer::new,
                DeclareNodeTokenizer::new,
                VariableNodeTokenizer::new
        );
    }
}
