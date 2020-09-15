package com.meti.process;

import com.meti.evaluate.MagmaPreProcessable;
import com.meti.evaluate.MagmaProcessable;
import com.meti.evaluate.Processable;

public class MagmaProcessor extends RecursiveProcessor {
    @Override
    protected Processable createPreprocessor(State tree) {
        return new MagmaPreProcessable(tree);
    }

    @Override
    protected Processable createProcessable(State tree) {
        return new MagmaProcessable(tree);
    }
}
