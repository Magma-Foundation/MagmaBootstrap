package com.meti;

public interface State {
    Dyad<Node,CallStack> destroy();

    Monad<CallStack> callStack();

    Monad<Node> node();

    State with(CallStack stack);

    State with(Node node);
}
