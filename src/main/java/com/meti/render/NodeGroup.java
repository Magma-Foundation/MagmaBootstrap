package com.meti.render;

import java.util.function.Predicate;

public enum NodeGroup {
    Function,
    Variable, Block, Return, Integer, Declare, Reference;

    public Predicate<NodeGroup> matches() {
        return (NodeGroup group) -> group == this;
    }
}
