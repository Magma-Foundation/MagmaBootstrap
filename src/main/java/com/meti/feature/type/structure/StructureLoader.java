package com.meti.feature.type.structure;

import com.meti.feature.evaluate.process.AbstractProcessable;
import com.meti.feature.render.Node;
import com.meti.process.State;

import java.util.Optional;

public class StructureLoader extends AbstractProcessable {
    public StructureLoader(State previous) {
        super(previous);
    }

    @Override
    public Optional<State> evaluate() {
        if (previous.has(Node.Group.Structure)) {
            return Optional.of(previous.define());
        }
        return Optional.empty();
    }
}
