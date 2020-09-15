package com.meti;

public abstract class RecursiveProcessor implements Processor {
    protected abstract Processable createPreprocessor(State tree);

    protected abstract Processable createProcessable(State tree);

    @Override
    public Monad<Node> process(Node tree) {
        State oldState = new InlineState(tree, new ImmutableCallStack());
        State newState = process(oldState);
        return newState.node();
    }

    private State process(State oldState) {
        return oldState.node()
                .extract(node -> reduceChildren(oldState, node))
                .swap()
                .apply(State::with);
    }

    private State reduceChildren(State oldState, Node node) {
        State newState = createPreprocessor(oldState).evaluate().orElse(oldState);
        return node.streamChildren().reduce(newState, RecursiveProcessor.this::processChild, (oldChildState, newChildState) -> newChildState);
    }

    private State processChild(State state, Node node) {
        State withChild = state.with(node);
        State processed = createProcessable(withChild).evaluate().orElse(withChild);
        return process(processed);
    }
}
