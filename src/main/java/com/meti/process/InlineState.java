package com.meti.process;

import com.meti.render.Node;
import com.meti.stack.CallStack;
import com.meti.util.Dyad;
import com.meti.util.Monad;

public class InlineState implements State {
    private final Node node;
    private final CallStack stack;

    public InlineState(Node node, CallStack stack) {
        this.node = node;
        this.stack = stack;
    }

    @Override
    public Dyad<Node, CallStack> destroy() {
        return new Dyad<>(node, stack);
    }

    @Override
    public Monad<Node> node() {
        return new Monad<>(node);
    }

    @Override
    public State with(CallStack stack) {
        return new InlineState(node, stack);
    }

    @Override
    public State with(Node node) {
        return new InlineState(node, stack);
    }

    @Override
    public Monad<CallStack> stack() {
        return new Monad<>(stack);
    }
}
