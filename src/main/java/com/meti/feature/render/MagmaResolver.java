package com.meti.feature.render;

import com.meti.feature.block.invoke.MappingResolver;
import com.meti.feature.evaluate.resolve.CollectiveResolver;
import com.meti.feature.evaluate.resolve.Resolver;
import com.meti.feature.scope.variable.VariableResolver;
import com.meti.process.State;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaResolver extends CollectiveResolver {
    public MagmaResolver(State state) {
        this(state, MagmaResolver::new);
    }

    public MagmaResolver(State state, Function<State, Resolver> parentFactory) {
        super(state, parentFactory);
    }

    @Override
    protected Stream<BiFunction<State, Function<State, Resolver>, Resolver>> streamResolvers() {
        return Stream.of(MappingResolver::new, VariableResolver::new);
    }
}
