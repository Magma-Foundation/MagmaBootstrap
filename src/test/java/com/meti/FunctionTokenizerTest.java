package com.meti;

import com.meti.content.Content;
import com.meti.content.StringContent;
import com.meti.feature.evaluate.Evaluator;
import com.meti.feature.block.function.FunctionTokenizer;
import com.meti.feature.render.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionTokenizerTest {
    @Test
    void valid() {
        Content content = new StringContent("def main() : Void => {}");
        Evaluator<Node> evaluator = new FunctionTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        assertTrue(optional.isPresent());
    }
}