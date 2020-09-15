package com.meti;

class MagmaProcessor extends RecursiveProcessor {
    @Override
    protected Processable createPreprocessor(State tree) {
        return new MagmaPreProcessable(tree);
    }

    @Override
    protected Processable createProcessable(State tree) {
        return new MagmaProcessable(tree);
    }
}
