package com.meti.evaluate;

import java.util.Optional;

public interface Evaluator<T> {
    Optional<T> evaluate();
}
