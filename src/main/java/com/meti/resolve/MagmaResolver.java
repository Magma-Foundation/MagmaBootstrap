package com.meti.resolve;

import com.meti.type.Type;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaResolver extends CompoundResolver {
    public MagmaResolver(Type previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<Type, Resolver>> streamChildren() {
        return Stream.of(
                StringResolver::new,
                PrimitiveResolver::new,
                PointerResolver::new);
    }
}
