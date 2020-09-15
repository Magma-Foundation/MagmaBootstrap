package com.meti.evaluate;

import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaProcessable extends CollectiveProcessable {
    public MagmaProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(VariableProcessable::new);
    }
}
