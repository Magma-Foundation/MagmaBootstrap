package com.meti;

public interface Frame {
    Frame define(Field field);

    boolean isDefined(String name);
}
