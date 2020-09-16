package com.meti.render;

import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.List;

public class MappingNode extends InvocationNode {
    public MappingNode(Node caller, List<Node> arguments) {
        super(caller, arguments);
    }

    @Override
    protected String createExtension() {
        return "";
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Mapping);
    }

    @Override
    public Prototype createPrototype() {
        return new MappingPrototype();
    }

    private static class MappingPrototype extends InvocationPrototype {
        private MappingPrototype() {
            this(new ArrayList<>());
        }

        private MappingPrototype(List<Node> children) {
            super(children);
        }

        @Override
        protected Node build(Node caller, List<Node> arguments) {
            return new MappingNode(caller, arguments);
        }

        @Override
        protected Prototype copy(List<Node> newChildren) {
            return new MappingPrototype(newChildren);
        }
    }
}
