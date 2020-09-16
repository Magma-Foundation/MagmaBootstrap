package com.meti.evaluate.resolve;

import com.meti.process.State;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaResolver extends CollectiveResolver {
    public MagmaResolver(State value) {
        super(value);
    }

    @Override
    protected Stream<Function<State, Resolver>> streamResolvers() {
        return Stream.of(MappingResolver::new, VariableResolver::new);
    }
}
