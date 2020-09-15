package com.meti.type;

import com.meti.content.Content;
import com.meti.render.Field;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Type {
    <R> Optional<R> applyToContent(Function<Content, R> function);

    Optional<String> render(String name);

    Prototype createPrototype();

    Stream<Type> streamChildren();

    Stream<Field> streamFields();

    interface Prototype {
        Prototype withChild(Type child);

        Prototype withField(Field field);

        Type build();
    }
}
