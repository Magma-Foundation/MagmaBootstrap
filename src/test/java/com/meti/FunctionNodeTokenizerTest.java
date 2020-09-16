package com.meti;

import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.evaluate.Evaluator;
import com.meti.evaluate.tokenizer.FunctionNodeTokenizer;
import com.meti.render.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionNodeTokenizerTest {
    @Test
    void valid() {
        Content content = new RootContent("def main() : Void => {}");
        Evaluator<Node> evaluator = new FunctionNodeTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        assertTrue(optional.isPresent());
    }
}