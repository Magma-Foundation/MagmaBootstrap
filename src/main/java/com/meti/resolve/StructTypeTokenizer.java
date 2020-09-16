package com.meti.resolve;

import com.meti.type.StructureType;
import com.meti.type.Type;

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
