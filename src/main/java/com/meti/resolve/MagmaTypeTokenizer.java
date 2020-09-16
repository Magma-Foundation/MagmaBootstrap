package com.meti.resolve;

import com.meti.type.Type;

import java.util.function.Function;
import java.util.stream.Stream;

public class MagmaTypeTokenizer extends CompoundTypeTokenizer {
    public MagmaTypeTokenizer(Type previous) {
        super(previous);
    }

    @Override
    protected Stream<Function<Type, TypeTokenizer>> streamChildren() {
        return Stream.of(
                StringTypeTokenizer::new,
                PrimitiveTypeTokenizer::new,
                PointerTypeTokenizer::new,
                StructTypeTokenizer::new);
    }
}
