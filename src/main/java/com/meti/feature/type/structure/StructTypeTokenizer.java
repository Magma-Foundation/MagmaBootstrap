package com.meti.feature.type.structure;

import com.meti.feature.render.Type;
import com.meti.resolve.AbstractTypeTokenizer;

import java.util.Optional;

public class StructTypeTokenizer extends AbstractTypeTokenizer {
    public StructTypeTokenizer(Type previous) {
        super(previous);
    }

    @Override
    public Optional<Type> tokenize() {
        return previous.applyToContent(StructureType::new);
    }
}
