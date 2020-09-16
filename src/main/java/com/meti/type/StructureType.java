package com.meti.type;

import com.meti.content.Content;
import com.meti.render.Field;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class StructureType implements Type {
    private final Content name;
    private final List<Field> fields;

    public StructureType(Content name, List<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    public StructureType(Content name) {
        this(name, Collections.emptyList());
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(name).map(function);
    }

    @Override
    public Optional<String> render(String name) {
        return this.name.value().map(inner -> "struct " + inner + " " + name).toOption();
    }

    @Override
    public Prototype createPrototype() {
        return new StructureTypePrototype(name);
    }

    @Override
    public Stream<Type> streamChildren() {
        return Stream.empty();
    }

    @Override
    public Stream<Field> streamFields() {
        return fields.stream();
    }

    @Override
    public Monad<TypeGroup> group() {
        return new Monad<>(TypeGroup.Structure);
    }

    private static class StructureTypePrototype implements Prototype {
        private final List<Field> fields;
        private final Content name;

        private StructureTypePrototype(Content name) {
            this(name, new ArrayList<>());
        }

        private StructureTypePrototype(Content name, List<Field> fields) {
            this.name = name;
            this.fields = fields;
        }

        @Override
        public Prototype withChild(Type child) {
            return this;
        }

        @Override
        public Prototype withField(Field field) {
            List<Field> newFields = new ArrayList<>(fields);
            newFields.add(field);
            return new StructureTypePrototype(name, newFields);
        }

        @Override
        public Type build() {
            return new StructureType(name, fields);
        }
    }
}
