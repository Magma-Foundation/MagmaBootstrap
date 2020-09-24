package com.meti.feature.scope.variable;

import com.meti.content.Content;
import com.meti.feature.evaluate.resolve.AbstractResolver;
import com.meti.feature.evaluate.resolve.Resolver;
import com.meti.feature.render.Type;
import com.meti.process.State;
import com.meti.feature.render.Node;
import com.meti.stack.CallStack;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;

public class VariableResolver extends AbstractResolver {
    public VariableResolver(State state, Function<State, Resolver> parentFactory) {
        super(state, parentFactory);
    }

    @Override
    public Optional<Boolean> is(Type type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Monad<Type>> resolve() {
        if (state.has(Node.Group.Variable)) {
            return state.destroy().apply(this::resolveNode).flatMap(Function.identity());
        }
        return Optional.empty();
    }

    private Optional<Optional<Monad<Type>>> resolveNode(Node node, CallStack callStack) {
        return node.applyToContent(content -> resolveContent(content, callStack));
    }

    private Optional<Monad<Type>> resolveContent(Content content, CallStack callStack) {
        return content.value().apply(callStack::resolve);
    }
}
