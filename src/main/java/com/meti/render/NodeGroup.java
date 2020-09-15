package com.meti.render;

import java.util.function.Predicate;

public enum NodeGroup {
    Function,
    Variable, Block, Return, Integer, Declare, Reference, Dereference;

    public Predicate<NodeGroup> matches() {
        return (NodeGroup group) -> group == this;
    }
}
