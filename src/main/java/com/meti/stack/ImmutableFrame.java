package com.meti.stack;

import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.*;

class ImmutableFrame implements Frame {
    private final List<Field> fields;
    private final Map<String, Set<Field>> structures;

    ImmutableFrame() {
        this(Collections.emptyList(), Collections.emptyMap());
    }


    ImmutableFrame(List<Field> fields, Map<String, Set<Field>> structures) {
        this.fields = new ArrayList<>(fields);
        this.structures = new HashMap<>(structures);
    }

    @Override
    public String toString() {
        List<String> names = fields.stream().reduce(new ArrayList<>(), this::merge, (oldList, newList) -> newList);
        String joinedNames = String.join(",", names);
        return String.format("[%s]", joinedNames);
    }

    private List<String> merge(List<String> strings, Field field) {
        field.applyToName(strings::add);
        return strings;
    }

    @Override
    public Frame define(Field field) {
        fields.add(field);
        return new ImmutableFrame(fields, Collections.emptyMap());
    }

    @Override
    public boolean isDefined(String name) {
        return fields.stream().anyMatch(field -> isNamed(field, name));
    }

    @Override
    public Optional<Monad<Type>> resolve(String name) {
        return fields.stream()
                .filter(field -> isNamed(field, name))
                .map(Field::type)
                .findFirst();
    }

    @Override
    public Frame define(String name, Set<Field> fields) {
        if (structures.containsKey(name)) {
            throw new IllegalArgumentException("Structure with name '" + name + "' has already been defined.");
        } else {
            structures.put(name, fields);
            return new ImmutableFrame(this.fields, structures);
        }
    }

    @Override
    public Optional<Boolean> match(String name, Type type) {
        return fields.stream()
                .filter(frame -> frame.isNamed(name))
                .map(frame -> frame.isTyped(type))
                .findFirst();
    }

    @Override
    public Optional<Boolean> doesReturn(String name, Type type) {
        return fields.stream()
                .filter(field -> field.isNamed(name))
                .map(field -> field.doesReturn(type))
                .findFirst();
    }

    private boolean isNamed(Field field, String name) {
        return field.applyToName(name::equals);
    }
}
