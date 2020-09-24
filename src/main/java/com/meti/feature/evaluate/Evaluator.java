package com.meti.feature.evaluate;

import java.util.Optional;

public interface Evaluator<T> {
    Optional<T> evaluate();
}
