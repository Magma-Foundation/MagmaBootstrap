package com.meti.stack;

import com.meti.render.Field;
import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CallStack {
    CallStack define(String name, Set<Field> fields);

    Optional<Monad<Type>> resolve(String name);

    CallStack define(Field field);

    CallStack enter();

    CallStack defineAll(List<Field> fields);

    boolean isDefined(String name);
}
