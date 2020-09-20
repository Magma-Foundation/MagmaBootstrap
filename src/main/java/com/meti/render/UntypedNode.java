package com.meti.render;

import java.util.stream.Stream;

public interface UntypedNode extends Node {
    @Override
    default Stream<Field> streamFields() {
        return Stream.empty();
    }
}
