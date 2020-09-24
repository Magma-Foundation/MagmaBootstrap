package com.meti.feature.block.invoke;

import com.meti.feature.evaluate.process.AbstractProcessable;
import com.meti.feature.render.Node;
import com.meti.feature.type.primitive.PrimitiveType;
import com.meti.process.State;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcedureFixer extends AbstractProcessable {
    public ProcedureFixer(State previous) {
        super(previous);
    }

    @Override
    public Optional<State> evaluate() {
        return Optional.of(previous)
                .filter(state -> state.has(Node.Group.Mapping))
                .map(this::transformMapping);
    }

    private State transformMapping(State previous) {
        return doesReturnVoid() ? previous.transformByNode(this::mapToProcedure) : this.previous;
    }

    private boolean doesReturnVoid() {
        return previous.matches(PrimitiveType.VOID);
    }

    private Node mapToProcedure(Node node) {
        List<Node> children = node.streamChildren().collect(Collectors.toList());
        Node caller = children.get(0);
        List<Node> arguments = children.subList(1, children.size());
        return new Procedure(caller, arguments);
    }
}
