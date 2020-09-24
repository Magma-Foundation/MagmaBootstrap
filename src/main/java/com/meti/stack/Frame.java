package com.meti.stack;

import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.Set;

public interface Frame {
    Frame define(Field field);

    boolean isDefined(String name);

    Optional<Monad<Type>> resolve(String name);

    Frame define(String name, Set<Field> fields);

    Optional<Boolean> match(String name, Type type);

    Optional<Boolean> doesReturn(String name, Type type);
}
