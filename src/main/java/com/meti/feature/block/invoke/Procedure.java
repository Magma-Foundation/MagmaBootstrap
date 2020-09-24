package com.meti.feature.block.invoke;

import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.List;

public class Procedure extends Invocation {

    public Procedure(Node caller, Node... arguments) {
        this(caller, List.of(arguments));
    }

    public Procedure(Node caller, List<Node> arguments) {
        super(caller, arguments);
    }



    @Override
    protected String createExtension() {
        return ";";
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Group.Procedure);
    }

    @Override
    public Prototype createPrototype() {
        return new ProcedurePrototype();
    }

    @Override
    protected Node copy(Node caller, List<Node> arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Prototype createWithChildren() {
        return streamChildren()
                .map(this::create)
                .reduce(createPrototype(), Prototype::merge);
    }

    @Override
    public Prototype create(Node child) {
        return createPrototype().withChild(child);
    }

    @Override
    public Prototype create(Field field) {
        throw new UnsupportedOperationException();
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
            return new Procedure(caller, arguments);
        }

        @Override
        protected Prototype copy(List<Node> newChildren) {
            return new ProcedurePrototype(newChildren);
        }
    }
}
