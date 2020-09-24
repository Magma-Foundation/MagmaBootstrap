package com.meti.feature.block.invoke;

import com.meti.feature.evaluate.resolve.AbstractResolver;
import com.meti.feature.evaluate.resolve.Resolver;
import com.meti.feature.render.Node;
import com.meti.feature.render.Type;
import com.meti.process.State;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;

public class MappingResolver extends AbstractResolver {
    public MappingResolver(State state, Function<State, Resolver> parentFactory) {
        super(state, parentFactory);
    }

    @Override
    public Optional<Boolean> is(Type type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Monad<Type>> resolve() {
        if (state.has(Node.Group.Mapping)) {
            State withCaller = state.transformByNode(this::findCaller);
            return parentFactory.apply(withCaller).evaluate();
        } else {
            return Optional.empty();
        }
    }

    private Node findCaller(Node node) {
        return node.streamChildren()
                .findFirst()
                .orElseThrow();
    }

}
