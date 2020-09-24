package com.meti.feature.scope.declare;

import com.meti.feature.evaluate.process.AbstractProcessable;
import com.meti.process.State;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.stack.CallStack;

import java.util.Optional;

public class DeclarationDefiner extends AbstractProcessable {
    public DeclarationDefiner(State previous) {
        super(previous);
    }

    @Override
    public Optional<State> evaluate() {
        if (previous.node().test(this::isDeclaration)) {
            CallStack newStack = previous.destroy().apply(this::define);
            return Optional.ofNullable(previous.with(newStack));
        }
        return Optional.empty();
    }

    private CallStack define(Node declaration, CallStack callStack) {
        Field identity = findIdentity(declaration);
        return callStack.define(identity);
    }

    private Field findIdentity(Node node) {
        return node.streamFields()
                .findFirst()
                .orElseThrow();
    }

    private boolean isDeclaration(Node node) {
        return node.group().test(Node.Group.Declare.matches());
    }
}
