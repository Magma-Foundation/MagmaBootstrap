package com.meti;

import java.util.Optional;

public interface Tokenizer<T> {
    Optional<T> tokenize();
}
