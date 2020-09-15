package com.meti;

public abstract class AbstractProcessable implements Processable {
    protected final State previous;

    public AbstractProcessable(State previous) {
        this.previous = previous;
    }
}
