package com.meti.evaluate.processable;

import com.meti.process.State;

public abstract class AbstractProcessable implements Processable {
    protected final State previous;

    public AbstractProcessable(State previous) {
        this.previous = previous;
    }
}
