package com.meti.resolve;

import com.meti.feature.render.Type;
import com.meti.feature.type.point.PointerTypeTokenizer;
import com.meti.feature.type.primitive.PrimitiveTypeTokenizer;
import com.meti.feature.type.primitive.StringTypeTokenizer;
import com.meti.feature.type.structure.StructTypeTokenizer;

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
