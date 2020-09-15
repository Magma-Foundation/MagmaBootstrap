package com.meti.evaluate.processable;

import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaLeafProcessable extends CollectiveProcessable {
    public MagmaLeafProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(VariableProcessable::new);
    }
}
