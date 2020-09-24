package com.meti.resolve;

import com.meti.feature.render.Type;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class CompoundTypeTokenizer extends AbstractTypeTokenizer {
    public CompoundTypeTokenizer(Type previous) {
        super(previous);
    }

    protected abstract Stream<Function<Type, TypeTokenizer>> streamChildren();

    @Override
    public Optional<Type> tokenize() {
        return streamChildren()
                .map(function -> function.apply(previous))
                .map(TypeTokenizer::tokenize)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
