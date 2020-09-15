package com.meti;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaPreProcessable extends CollectiveProcessable {
    protected MagmaPreProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(
            FunctionPreProcessable::new
        );
    }
}
