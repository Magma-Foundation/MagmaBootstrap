package com.meti.evaluate;

import com.meti.content.Content;

public abstract class AbstractTokenizer implements Tokenizer {
    protected final Content content;

    public AbstractTokenizer(Content content) {
        this.content = content;
    }
}
