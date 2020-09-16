package com.meti.evaluate.resolve;

import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.Optional;

public interface Resolver {
    Optional<Monad<Type>> resolve();
}
