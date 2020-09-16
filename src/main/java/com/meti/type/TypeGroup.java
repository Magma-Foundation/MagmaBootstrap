package com.meti.type;

import java.util.function.Predicate;

public enum TypeGroup {
    Content, Structure;

    public Predicate<TypeGroup> matches() {
        return typeGroup -> typeGroup == this;
    }
}
