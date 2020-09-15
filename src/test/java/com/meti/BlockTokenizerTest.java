package com.meti;

import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.evaluate.tokenizer.BlockTokenizer;
import com.meti.evaluate.Evaluator;
import com.meti.evaluate.tokenizer.RootTokenizer;
import com.meti.render.BlockNode;
import com.meti.render.ContentNode;
import com.meti.render.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BlockTokenizerTest {
    @Test
    void validInner(){
        Content content = new RootContent("{{}{}}");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new BlockNode(new ContentNode(new RootContent("{}")), new ContentNode(new RootContent("{}"))), result);
    }

    @Test
    void validChildren(){
        Content content = new RootContent("{10;20}");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new BlockNode(new ContentNode(new RootContent("10")), new ContentNode(new RootContent("20"))), result);
    }

    @Test
    void invalid(){
        Content content = new RootContent("test");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        assertTrue(optional.isEmpty());
    }

    @Test
    void validChild(){
        Content content = new RootContent("{10}");
        Evaluator<Node> evaluator = new RootTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new BlockNode(new ContentNode(new RootContent("10"))), result);
    }

    @Test
    void valid(){
        Content content = new RootContent("{}");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new BlockNode(), result);
    }
}