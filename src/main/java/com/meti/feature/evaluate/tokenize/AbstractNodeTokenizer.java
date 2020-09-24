package com.meti.feature.evaluate.tokenize;

import com.meti.content.Content;

public abstract class AbstractNodeTokenizer implements NodeTokenizer {
    protected final Content content;

    public AbstractNodeTokenizer(Content content) {
        this.content = content;
    }
}
