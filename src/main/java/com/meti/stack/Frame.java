package com.meti.stack;

import com.meti.render.Field;
import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.Optional;

public interface Frame {
    Frame define(Field field);

    boolean isDefined(String name);

    Optional<Monad<Type>> resolve(String name);
}
