package com.meti.feature.evaluate.resolve;

import com.meti.process.State;

import java.util.function.Function;

public abstract class AbstractResolver implements Resolver {
    protected final State state;
    protected final Function<State, Resolver> parentFactory;

    public AbstractResolver(State state, Function<State, Resolver> parentFactory) {
        this.state = state;
        this.parentFactory = parentFactory;
    }
}
