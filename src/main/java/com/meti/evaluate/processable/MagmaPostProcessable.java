package com.meti.evaluate.processable;

import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaPostProcessable extends CollectiveProcessable{
    public MagmaPostProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(
                DeclarePostProcessable::new,
                ProcedureFixer::new
        );
    }
}
