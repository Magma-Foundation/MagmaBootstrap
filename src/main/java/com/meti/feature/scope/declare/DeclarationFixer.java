package com.meti.feature.scope.declare;

import com.meti.feature.evaluate.process.AbstractProcessable;
import com.meti.feature.render.Type;
import com.meti.process.State;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;

import java.util.Optional;

public class DeclarationFixer extends AbstractProcessable {
    public DeclarationFixer(State previous) {
        super(previous);
    }

    @Override
    public Optional<State> evaluate() {
        if (previous.has(Node.Group.Declare)) {
            return Optional.of(previous.transformByNode(this::transformImpl));
        }
        return Optional.empty();
    }

    private Node transformImpl(Node node) {
        Node.Prototype prototype = node.transformByIdentity(this::transformIdentity);
        return node.createWithChildren()
                .merge(prototype)
                .build();
    }

    private Field transformIdentity(Field oldIdentity) {
        return oldIdentity.transformByType(this::fixDeclareType);
    }

    private Type fixDeclareType(Type type) {
        return type;
    }
}
