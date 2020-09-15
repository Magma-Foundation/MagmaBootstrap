package com.meti.evaluate.processable;

import com.meti.stack.CallStack;
import com.meti.render.NodeGroup;
import com.meti.process.State;
import com.meti.UndefinedException;
import com.meti.content.Content;
import com.meti.render.Node;

import java.util.Optional;
import java.util.function.Consumer;

public class VariableProcessable extends AbstractProcessable{
    public VariableProcessable(State previous) {
        super(previous);
    }

    @Override
    public Optional<State> evaluate() {
        return previous.destroy().apply(this::process);
    }

    private Optional<State> process(Node node, CallStack stack) {
        if(node.group().test(NodeGroup.Variable.matches())) {
            node.applyToContent(content -> testDefinition(content, stack))
                    .orElseThrow()
                    .ifPresent(thrown());
        }
        return Optional.empty();
    }

    private Consumer<UndefinedException> thrown() {
        return e -> {
            throw e;
        };
    }

    private Optional<UndefinedException> testDefinition(Content content, CallStack stack) {
        return content.value().test(stack::isDefined) ? Optional.empty() :
                createUndefined(content, stack);
    }

    private Optional<UndefinedException> createUndefined(Content content, CallStack stack) {
        return content.value().append(stack)
                .map(VariableProcessable.this::createUndefined)
                .toOption();
    }

    private UndefinedException createUndefined(String name, CallStack stack) {
        return new UndefinedException(name + " isn't defined in " + stack);
    }
}
