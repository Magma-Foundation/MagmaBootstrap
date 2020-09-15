package com.meti.resolve;

import com.meti.type.Type;

public abstract class AbstractResolver implements Resolver {
    protected final Type previous;

    public AbstractResolver(Type previous) {
        this.previous = previous;
    }
}
