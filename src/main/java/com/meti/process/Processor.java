package com.meti.process;

import com.meti.util.Monad;
import com.meti.feature.render.Node;

public interface Processor {
    Monad<Node> process(Node tree);
}
