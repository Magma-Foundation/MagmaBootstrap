package com.meti.render;

import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.List;

public class ProcedureNode extends InvocationNode {
    public ProcedureNode(Node caller, List<Node> arguments) {
        super(caller, arguments);
    }

    @Override
    protected String createExtension() {
        return ";";
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.Procedure);
    }

    @Override
    public Prototype createPrototype() {
        return new ProcedurePrototype();
    }

    private static class ProcedurePrototype extends InvocationPrototype {
        private ProcedurePrototype() {
            this(new ArrayList<>());
        }

        private ProcedurePrototype(List<Node> children) {
            super(children);
        }

        @Override
        protected Node build(Node caller, List<Node> arguments) {
            return new ProcedureNode(caller, arguments);
        }

        @Override
        protected Prototype copy(List<Node> newChildren) {
            return new ProcedurePrototype(newChildren);
        }
    }
}
