package com.meti;

import com.meti.content.Content;
import com.meti.content.StringContent;
import com.meti.feature.block.BlockTokenizer;
import com.meti.feature.evaluate.Evaluator;
import com.meti.feature.evaluate.tokenize.MagmaNodeTokenizer;
import com.meti.util.load.PathClassPath;
import com.meti.feature.block.Block;
import com.meti.feature.render.ContentNode;
import com.meti.feature.render.Node;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BlockTokenizerTest {
    @Test
    void validInner(){
        Content content = new StringContent("{{}{}}");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new Block(new ContentNode(new StringContent("{}")), new ContentNode(new StringContent("{}"))), result);
    }

    @Test
    void validChildren(){
        Content content = new StringContent("{10;20}");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new Block(new ContentNode(new StringContent("10")), new ContentNode(new StringContent("20"))), result);
    }

    @Test
    void invalid(){
        Content content = new StringContent("test");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        assertTrue(optional.isEmpty());
    }

    @Test
    void validChild(){
        Content content = new StringContent("{10}");
        Evaluator<Node> evaluator = new MagmaNodeTokenizer(content, new PathClassPath(Paths.get(".", "test", "source")));
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new Block(new ContentNode(new StringContent("10"))), result);
    }

    @Test
    void valid(){
        Content content = new StringContent("{}");
        Evaluator<Node> evaluator = new BlockTokenizer(content);
        Optional<Node> optional = evaluator.evaluate();
        Node result = optional.orElseThrow();
        assertEquals(new Block(), result);
    }
}