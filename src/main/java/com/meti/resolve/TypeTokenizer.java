package com.meti.resolve;

import com.meti.type.Type;

import java.util.Optional;

public interface TypeTokenizer {
    Optional<Type> tokenize();
}
