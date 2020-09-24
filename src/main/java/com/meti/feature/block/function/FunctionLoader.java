package com.meti.feature.block.function;

import com.meti.feature.evaluate.process.Processable;
import com.meti.process.State;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.stack.CallStack;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class FunctionLoader implements Processable {
    protected final State previous;

    public FunctionLoader(State previous) {
        this.previous = previous;
    }

    @Override
    public Optional<State> evaluate() {
        return previous.foldStackByNode(this::test, this::defineFields);
    }

    protected abstract boolean test(Node node);

    private CallStack defineFields(Node node, CallStack stack) {
        List<Field> fields = node.streamFields().collect(Collectors.toList());
        CallStack withIdentity = defineIdentity(fields, stack);
        return after(fields, withIdentity);
    }

    protected abstract CallStack after(List<Field> fields, CallStack withIdentity);

    private CallStack defineIdentity(List<Field> fields, CallStack stack) {
        Field identity = fields.get(0);
        return stack.define(identity);
    }
}
