package com.meti;

import com.meti.content.StringContent;
import com.meti.feature.render.ContentNode;
import com.meti.feature.render.Node;
import com.meti.process.MagmaProcessor;
import com.meti.process.Processor;
import com.meti.util.load.ClassPath;

public class Compiler {
    private final Tokenizer tokenizer;

    public Compiler(ClassPath classPath) {
        this.tokenizer = new MagmaTokenizer(classPath);
    }

    String compile(String content) {
        Node root = new ContentNode(new StringContent("{" + content + "}"));
        Node tree = tokenizer.tokenize(root);
        Processor processor = new MagmaProcessor();
        String output = processor.process(tree).apply(Node::render);
        return output.substring(1, output.length() - 1);
    }
}