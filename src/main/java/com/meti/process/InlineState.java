package com.meti.process;

import com.meti.feature.render.Node;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;
import com.meti.util.Dyad;
import com.meti.util.Monad;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class InlineState implements State {
    private final Node node;
    private final CallStack stack;

    public InlineState(Node node, CallStack stack) {
        this.node = node;
        this.stack = stack;
    }

    @Override
    public Optional<State> foldStackByNode(Predicate<Node> predicate, BiFunction<Node, CallStack, CallStack> mapping) {
        return predicate.test(node) ? Optional.of(new InlineState(node, mapping.apply(node, stack))) : Optional.empty();
    }

    @Override
    public Dyad<Node, CallStack> destroy() {
        return new Dyad<>(node, stack);
    }

    @Override
    public Monad<Node> node() {
        return new Monad<>(node);
    }

    @Override
    public State with(CallStack stack) {
        return new InlineState(node, stack);
    }

    @Override
    public State with(Node node) {
        return new InlineState(node, stack);
    }

    @Override
    public Monad<CallStack> stack() {
        return new Monad<>(stack);
    }

    @Override
    public State transformByNode(Function<Node, Node> mapping){
        return with(node().apply(mapping));
    }

    @Override
    public boolean has(Node.Group group) {
        return node().test(group::matches);
    }

    @Override
    public State define(){
        return destroy().apply((node, stack) -> with(node.define(stack)));
    }

    @Override
    public boolean matches(Type type) {
        return node.matches(type, stack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InlineState that = (InlineState) o;
        return Objects.equals(node, that.node) &&
                Objects.equals(stack, that.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, stack);
    }
}
