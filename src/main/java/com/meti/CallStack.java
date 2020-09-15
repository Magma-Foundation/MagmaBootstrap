package com.meti;

import java.util.List;

public interface CallStack {
    CallStack define(Field field);

    CallStack enter();

    CallStack defineAll(List<Field> fields);

    boolean isDefined(String name);
}
