package com.meti.stack;

import com.meti.render.Field;

public interface Frame {
    Frame define(Field field);

    boolean isDefined(String name);
}
