package com.meti.resolve;

import com.meti.type.Type;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class CompoundResolver extends AbstractResolver {
    public CompoundResolver(Type previous) {
        super(previous);
    }

    protected abstract Stream<Function<Type, Resolver>> streamChildren();

    @Override
    public Optional<Type> resolve() {
        return streamChildren()
                .map(function -> function.apply(previous))
                .map(Resolver::resolve)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
