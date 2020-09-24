package com.meti.feature.render;

import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Leaf implements Node {
    @Override
    public Stream<Node> streamChildren() {
        return Stream.empty();
    }

    @Override
    public boolean isParent() {
        return false;
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        return this;
    }
}
