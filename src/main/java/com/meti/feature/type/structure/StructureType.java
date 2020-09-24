package com.meti.feature.type.structure;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StructureType implements Type {
    private final Content structName;
    private final Set<Field> fields;

    public StructureType(Content structName, Set<Field> fields) {
        this.structName = structName;
        this.fields = fields;
    }

    public StructureType(Content structName) {
        this(structName, Collections.emptySet());
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.of(structName).map(function);
    }

    @Override
    public Prototype createPrototype() {
        return new StructureTypePrototype(structName);
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
    public Monad<Group> group() {
        return new Monad<>(Group.Structure);
    }

    @Override
    public String render(String name) {
        return "struct " + structName.asString() + " " + name;
    }

    @Override
    public String render() {
        return render("");
    }

    @Override
    public boolean is(Group group) {
        return group().test(group.matches());
    }

    @Override
    public Type transformField(Function<Field, Field> mapping) {
        Set<Field> newFields = fields.stream()
                .map(mapping)
                .collect(Collectors.toSet());
        return new StructureType(structName, newFields);
    }

    @Override
    public Type transformChildren(Function<Type, Type> mapping) {
        return this;
    }

    @Override
    public boolean doesReturn(Type type) {
        return false;
    }

    private static class StructureTypePrototype implements Prototype {
        private final Set<Field> fields;
        private final Content name;

        private StructureTypePrototype(Content name) {
            this(name, new HashSet<>());
        }

        private StructureTypePrototype(Content name, Set<Field> fields) {
            this.name = name;
            this.fields = fields;
        }

        @Override
        public Prototype withChild(Type child) {
            return this;
        }

        @Override
        public Prototype withField(Field field) {
            Set<Field> newFields = new HashSet<>(fields);
            newFields.add(field);
            return new StructureTypePrototype(name, newFields);
        }

        @Override
        public Type build() {
            return new StructureType(name, fields);
        }
    }
}
