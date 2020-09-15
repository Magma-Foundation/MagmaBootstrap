package com.meti.process;

import com.meti.evaluate.processable.MagmaLeafProcessable;
import com.meti.evaluate.processable.MagmaPostProcessable;
import com.meti.evaluate.processable.MagmaPreProcessable;
import com.meti.evaluate.processable.Processable;

public class MagmaProcessor extends RecursiveProcessor {
    @Override
    protected Processable createPreProcessable(State tree) {
        return new MagmaPreProcessable(tree);
    }

    @Override
    protected Processable createLeafProcessable(State tree) {
        return new MagmaLeafProcessable(tree);
    }

    @Override
    protected Processable createPostProcessable(State tree) {
        return new MagmaPostProcessable(tree);
    }
}
