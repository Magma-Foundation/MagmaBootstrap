package com.meti.evaluate.tokenizer;

import com.meti.content.Content;

public abstract class AbstractNodeTokenizer implements NodeTokenizer {
    protected final Content content;

    public AbstractNodeTokenizer(Content content) {
        this.content = content;
    }
}
