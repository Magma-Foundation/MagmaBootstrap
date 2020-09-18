package com.meti.type;

import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Type {
    Monad<TypeGroup> group();

    <R> Optional<R> applyToContent(Function<Content, R> function);

    Optional<String> render(String name);

    Prototype createPrototype();

    Stream<Type> streamChildren();

    Stream<Field> streamFields();

    default Optional<String> render() {
        return render("");
    }

    interface Prototype {
        Prototype withChild(Type child);

        Prototype withField(Field field);

        Type build();
    }
}
