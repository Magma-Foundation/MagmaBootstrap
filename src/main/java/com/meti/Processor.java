package com.meti;

public interface Processor {
    Monad<Node> process(Node tree);
}
