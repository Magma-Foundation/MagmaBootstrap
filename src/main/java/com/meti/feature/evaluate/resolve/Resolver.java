package com.meti.feature.evaluate.resolve;

import com.meti.feature.evaluate.Evaluator;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.Optional;

@Deprecated
public interface Resolver extends Evaluator<Monad<Type>> {
    Optional<Boolean> is(Type type);

    @Deprecated
    Optional<Monad<Type>> resolve();

    @Override
    default Optional<Monad<Type>> evaluate() {
        return resolve();
    }
}
