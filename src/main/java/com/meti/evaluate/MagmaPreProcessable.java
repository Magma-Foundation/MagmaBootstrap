package com.meti.evaluate;

import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaPreProcessable extends CollectiveProcessable {
    public MagmaPreProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(
            FunctionPreProcessable::new
        );
    }
}
