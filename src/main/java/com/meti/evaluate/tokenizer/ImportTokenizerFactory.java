package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.util.load.ClassPath;

public class ImportTokenizerFactory {
    private final ClassPath classPath;

    public ImportTokenizerFactory(ClassPath classPath) {
        this.classPath = classPath;
    }

    public ImportTokenizer create(Content content1) {
        return new ImportTokenizer(content1, classPath);
    }
}