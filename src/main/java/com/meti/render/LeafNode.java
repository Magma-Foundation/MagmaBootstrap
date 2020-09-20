package com.meti.render;

import java.util.stream.Stream;

public abstract class LeafNode implements Node {
    @Override
    public Stream<Node> streamChildren() {
        return Stream.empty();
    }

    @Override
    public boolean isParent() {
        return false;
    }
}
