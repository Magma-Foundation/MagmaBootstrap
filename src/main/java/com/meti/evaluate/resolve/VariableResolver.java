package com.meti.evaluate.resolve;

import com.meti.content.Content;
import com.meti.process.State;
import com.meti.feature.Node;
import com.meti.stack.CallStack;
import com.meti.feature.Type;
import com.meti.util.Monad;

import java.util.Optional;
import java.util.function.Function;

public class VariableResolver extends AbstractResolver {
    public VariableResolver(State state) {
        super(state);
    }

    @Override
    public Optional<Monad<Type>> resolve() {
        if (state.node().test(this::isVariable)) {
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

    private boolean isVariable(Node node) {
        return node.group().test(Node.Group.Variable.matches());
    }
}
