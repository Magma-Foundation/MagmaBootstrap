package com.meti.feature.render;

import com.meti.content.Content;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Type {
    Type transformField(Function<Field, Field> mapping);

    Type transformChildren(Function<Type, Type> mapping);

    Monad<Group> group();

    <R> Optional<R> applyToContent(Function<Content, R> function);

    String render(String name);

    String render();

    Prototype createPrototype();

    Stream<Type> streamChildren();

    Stream<Field> streamFields();

    boolean is(Group group);

    boolean doesReturn(Type type);

    enum Group {
        Content, Structure, Primitive, Function;

        public Predicate<Group> matches() {
            return typeGroup -> typeGroup == this;
        }
    }

    interface Prototype {
        Prototype withChild(Type child);

        Prototype withField(Field field);

        Type build();
    }
}
