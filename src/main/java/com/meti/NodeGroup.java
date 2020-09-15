package com.meti;

import java.util.function.Predicate;

public enum NodeGroup {
    Function,
    Variable, Block, Return, Integer;

    Predicate<NodeGroup> matches() {
        return (NodeGroup group) -> group == this;
    }
}
