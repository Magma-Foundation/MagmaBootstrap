package com.meti.evaluate.processable;

import com.meti.stack.CallStack;
import com.meti.render.NodeGroup;
import com.meti.process.State;
import com.meti.render.Field;
import com.meti.render.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FunctionPreProcessable implements Processable {
    private final State previous;

    public FunctionPreProcessable(State previous) {
        this.previous = previous;
    }

    @Override
    public Optional<State> evaluate() {
        return previous.destroy()
                .map(this::preprocess)
                .append(previous)
                .swap().map(State::with)
                .toOption();
    }

    private CallStack preprocess(Node node, CallStack stack) {
        return node.group().test(NodeGroup.ConcreteFunction.matches()) ?
                defineFields(node, stack) : stack;
    }

    private CallStack defineFields(Node node, CallStack stack) {
        List<Field> fields = node.streamFields().collect(Collectors.toList());
        CallStack withIdentity = defineIdentity(fields, stack);
        return defineParameters(fields, withIdentity);
    }

    private CallStack defineParameters(List<Field> fields, CallStack withIdentity) {
        List<Field> parameters = fields.subList(1, fields.size());
        return withIdentity.enter().defineAll(parameters);
    }

    private CallStack defineIdentity(List<Field> fields, CallStack stack) {
        Field identity = fields.get(0);
        return stack.define(identity);
    }
}
