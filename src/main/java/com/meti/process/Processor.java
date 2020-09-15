package com.meti.process;

import com.meti.util.Monad;
import com.meti.render.Node;

public interface Processor {
    Monad<Node> process(Node tree);
}
