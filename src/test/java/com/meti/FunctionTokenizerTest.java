package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionTokenizerTest {
    @Test
    void valid() {
        Content content = new RootContent("def main() : Void => {}");
        Evaluator<Node> evaluator = new FunctionTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        assertTrue(optional.isPresent());
    }
}