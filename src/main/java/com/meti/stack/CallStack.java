package com.meti.stack;

import com.meti.render.Field;

import java.util.List;

public interface CallStack {
    CallStack define(Field field);

    CallStack enter();

    CallStack defineAll(List<Field> fields);

    boolean isDefined(String name);
}
