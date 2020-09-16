package com.meti.evaluate.resolve;

import com.meti.process.State;
import com.meti.render.Node;
import com.meti.render.NodeGroup;
import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.Optional;

public class MappingResolver extends AbstractResolver {
    public MappingResolver(State state) {
        super(state);
    }

    @Override
    public Optional<Monad<Type>> resolve() {
        boolean test = state.node().test(this::isMapping);
        if (test) {
            Node caller = state.node().apply(this::findCaller);
            Resolver resolver = new MagmaResolver(state.with(caller));
            return resolver.resolve();
        } else {
            return Optional.empty();
        }
    }

    private boolean isMapping(Node node) {
        return node.group().test(this::isMapping);
    }

    private Node findCaller(Node node) {
        return node.streamChildren()
                .findFirst()
                .orElseThrow();
    }

    private boolean isMapping(NodeGroup nodeGroup) {
        return nodeGroup == NodeGroup.Mapping;
    }
}
