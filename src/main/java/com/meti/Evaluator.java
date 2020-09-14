package com.meti;

import java.util.Optional;

public interface Evaluator<T> {
    Optional<T> evaluate();
}
