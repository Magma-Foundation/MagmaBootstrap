package com.meti.feature.evaluate.process;

import com.meti.feature.evaluate.Evaluator;
import com.meti.process.State;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class CollectiveProcessable extends AbstractProcessable {
    protected CollectiveProcessable(State previous) {
        super(previous);
    }

    protected abstract Stream<Function<State, Processable>> streamPreprocessors();

    @Override
    public Optional<State> evaluate() {
        return streamPreprocessors()
                .map(function -> function.apply(previous))
                .map(Evaluator::evaluate)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
