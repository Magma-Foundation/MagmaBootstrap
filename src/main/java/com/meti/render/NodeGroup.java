package com.meti.render;

import java.util.function.Predicate;

public enum NodeGroup {
    ConcreteFunction,
    Variable, Block, Return, Integer, Declare, Reference, Dereference, AbstractFunction, Mapping, Procedure, Structure, Construction, Field;

    public Predicate<NodeGroup> matches() {
        return (NodeGroup group) -> group == this;
    }
}
