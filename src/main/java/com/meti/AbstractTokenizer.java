package com.meti;

public abstract class AbstractTokenizer implements Tokenizer {
    protected final Content content;

    public AbstractTokenizer(Content content) {
        this.content = content;
    }
}
