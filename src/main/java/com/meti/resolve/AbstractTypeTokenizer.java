package com.meti.resolve;

import com.meti.feature.Type;

public abstract class AbstractTypeTokenizer implements TypeTokenizer {
    protected final Type previous;

    public AbstractTypeTokenizer(Type previous) {
        this.previous = previous;
    }
}
