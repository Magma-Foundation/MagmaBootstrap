package com.meti.process;

import com.meti.feature.evaluate.process.Processable;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.stack.ImmutableCallStack;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private State processChildren(State parentState) {
        Monad<Node> parent = parentState.node();
        Node.Prototype prototype = parent.apply(Node::createPrototype);
        State current = parentState;
        List<Field> fields = parent.apply(Node::streamFields).collect(Collectors.toList());
        Node.Prototype withFields = fields.stream().reduce(prototype, Node.Prototype::withField, (oldPrototype, newPrototype) -> oldPrototype);
        List<Node> oldChildren = parent.apply(Node::streamChildren).collect(Collectors.toList());
        List<Node> newChildren = new ArrayList<>();
        for (Node oldChild : oldChildren) {
            current = processChild(current, oldChild);
            current.node().accept(newChildren::add);
        }
        Node.Prototype withChildren = newChildren.stream().reduce(withFields, Node.Prototype::withChild, (oldPrototype, newPrototype) -> newPrototype);
        Node built = withChildren.build();
        return current.with(built);
    }

    private State processChild(State parentState, Node child) {
        State withChild = parentState.with(child);
        return process(withChild);
    }
}
