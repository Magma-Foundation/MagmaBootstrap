package com.meti.feature.evaluate.resolve;

import com.meti.feature.render.Type;
import com.meti.process.State;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class CollectiveResolver extends AbstractResolver {
    public CollectiveResolver(State state, Function<State, Resolver> parentFactory) {
        super(state, parentFactory);
    }

    @Override
    public Optional<Boolean> is(Type type) {
        return streamConstructions()
                .map(resolver -> resolver.is(type))
                .flatMap(Optional::stream)
                .findFirst();
    }

    protected abstract Stream<BiFunction<State, Function<State, Resolver>, Resolver>> streamResolvers();

    @Override
    public Optional<Monad<Type>> resolve() {
        return streamConstructions()
                .map(Resolver::evaluate)
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Stream<Resolver> streamConstructions() {
        return streamResolvers()
                .map(function -> function.apply(state, parentFactory));
    }
}
