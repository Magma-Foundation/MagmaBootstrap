package com.meti.process;

import com.meti.render.Node;
import com.meti.stack.CallStack;
import com.meti.util.Dyad;
import com.meti.util.Monad;

public interface State {
    Dyad<Node, CallStack> destroy();

    Monad<Node> node();

    State with(CallStack stack);

    State with(Node node);
}
