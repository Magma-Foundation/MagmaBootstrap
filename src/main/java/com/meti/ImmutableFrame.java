package com.meti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

class ImmutableFrame implements Frame {
    private final List<Field> fields;

    ImmutableFrame() {
        this(Collections.emptyList());
    }

    ImmutableFrame(List<Field> fields) {
        this.fields = new ArrayList<>(fields);
    }

    @Override
    public Frame define(Field field) {
        fields.add(field);
        return new ImmutableFrame(fields);
    }

    @Override
    public boolean isDefined(String name){
        return fields.stream().anyMatch(field -> isNamed(field, name));
    }

    private boolean isNamed(Field field, String name) {
        return field.applyToName(name::equals);
    }
}
