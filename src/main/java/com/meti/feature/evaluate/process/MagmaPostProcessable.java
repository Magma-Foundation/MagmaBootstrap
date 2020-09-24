package com.meti.feature.evaluate.process;

import com.meti.feature.scope.declare.DeclarationDefiner;
import com.meti.feature.block.invoke.ProcedureFixer;
import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaPostProcessable extends CollectiveProcessable {
    public MagmaPostProcessable(State previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<State, Processable>> streamPreprocessors() {
        return Stream.of(
                DeclarationDefiner::new,
                ProcedureFixer::new
        );
    }
}
