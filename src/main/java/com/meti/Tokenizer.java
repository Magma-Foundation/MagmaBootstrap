package com.meti;

import com.meti.feature.render.Node;

public interface Tokenizer {
    Node tokenize(Node previous);
}
