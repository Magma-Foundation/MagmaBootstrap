package com.meti.stack;

import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface CallStack {
    CallStack define(String name, Set<Field> fields);

    boolean matches(String name, Type type);

    @Deprecated
    Optional<Monad<Type>> resolve(String name);

    CallStack define(Field field);

    CallStack enter();

    CallStack defineAll(List<Field> fields);

    boolean isDefined(String name);

    boolean doesReturn(String name, Type type);
}
