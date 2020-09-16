package com.meti.evaluate.resolve;

import com.meti.process.State;

public abstract class AbstractResolver implements Resolver {
    protected final State state;

    public AbstractResolver(State state) {
        this.state = state;
    }
}
