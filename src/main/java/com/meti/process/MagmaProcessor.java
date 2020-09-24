package com.meti.process;

import com.meti.feature.evaluate.process.MagmaLeafProcessable;
import com.meti.feature.evaluate.process.MagmaPostProcessable;
import com.meti.feature.evaluate.process.MagmaPreProcessable;
import com.meti.feature.evaluate.process.Processable;

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
