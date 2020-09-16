package com.meti.evaluate.resolve;

import com.meti.process.State;
import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class CollectiveResolver extends AbstractResolver {
    protected CollectiveResolver(State value) {
        super(value);
    }

    protected abstract Stream<Function<State, Resolver>> streamResolvers();

    @Override
    public Optional<Monad<Type>> resolve() {
        return streamResolvers()
                .map(function -> function.apply(state))
                .map(Resolver::resolve)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
