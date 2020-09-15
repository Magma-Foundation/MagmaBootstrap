package com.meti.process;

import com.meti.evaluate.processable.Processable;
import com.meti.render.Node;
import com.meti.stack.ImmutableCallStack;
import com.meti.util.Monad;

public abstract class RecursiveProcessor implements Processor {
    protected abstract Processable createPreProcessable(State tree);

    protected abstract Processable createLeafProcessable(State tree);

    protected abstract Processable createPostProcessable(State tree);

    @Override
    public Monad<Node> process(Node tree) {
        State oldState = new InlineState(tree, new ImmutableCallStack());
        State newState = RecursiveProcessor.this.process(oldState);
        return newState.node();
    }

    private State process(State oldState) {
        if (oldState.node().test(Node::isParent)) {
            return processParent(oldState);
        } else {
            return createLeafProcessable(oldState).evaluate().orElse(oldState);
        }
    }

    private State processParent(State state) {
        State preprocessed = preProcess(state);
        State processed = processChildren(preprocessed);
        return postProcess(processed);
    }

    private State postProcess(State processed) {
        return createPostProcessable(processed).evaluate().orElse(processed);
    }

    private State preProcess(State state) {
        return createPreProcessable(state).evaluate().orElse(state);
    }

    private State processChildren(State preprocessed) {
        Monad<Node> parent = preprocessed.node();
        State withFinalChild = parent.apply(Node::streamChildren).reduce(preprocessed, this::processChild, (state1, state2) -> state2);
        return parent.apply(withFinalChild::with);
    }

    private State processChild(State parentState, Node child) {
        State withChild = parentState.with(child);
        return process(withChild);
    }
}
