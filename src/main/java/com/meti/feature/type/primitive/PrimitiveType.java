package com.meti.feature.type.primitive;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public enum PrimitiveType implements Type {
    I8("char"),
    U8("unsigned char"),
    I16("int"),
    U16("unsigned int"),
    I32("long"),
    U32("unsigned long"),
    I64("long long"),
    U64("unsigned long long"),
    F32("float"),
    F64("double"),
    CHAR("char"),
    BOOL("int"),
    VOID("void"),
    ANY("void");

    private final String value;

    PrimitiveType(String value) {
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Prototype createPrototype() {
        return new PrimitivePrototype();
    }

    @Override
    public Stream<Type> streamChildren() {
        return Stream.empty();
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Primitive);
    }

    @Override
    public String render(String name) {
        return value + (name.length() == 0 || !Character.isLetter(name.charAt(0)) ? "" : " ") + name;
    }

    @Override
    public String render() {
        return value;
    }

    @Override
    public boolean is(Group group) {
        return group().test(group.matches());
    }

    @Override
    public Type transformField(Function<Field, Field> mapping) {
        return this;
    }

    @Override
    public Type transformChildren(Function<Type, Type> mapping) {
        return this;
    }

    @Override
    public boolean doesReturn(Type type) {
        return false;
    }

    private class PrimitivePrototype implements Prototype {
        @Override
        public Prototype withChild(Type child) {
            return this;
        }

        @Override
        public Prototype withField(Field field) {
            return this;
        }

        @Override
        public Type build() {
            return PrimitiveType.this;
        }
    }
}
