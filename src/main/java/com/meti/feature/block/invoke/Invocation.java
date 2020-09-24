package com.meti.feature.block.invoke;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Parent;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Invocation extends Parent {
    protected final Node caller;
    protected final List<Node> arguments;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Invocation)) return false;
        Invocation cast = (Invocation) obj;
        return caller.equals(cast.caller) &&
                arguments.containsAll(cast.arguments) &&
                cast.arguments.containsAll(arguments);
    }

    public Invocation(Node caller, List<Node> arguments) {
        this.caller = caller;
        this.arguments = arguments;
    }

    protected abstract String createExtension();

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Stream<Field> streamFields() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.concat(Stream.of(caller), arguments.stream());
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.of(renderCompletely());
    }

    private String renderCompletely() {
        return renderCaller() + renderArguments() + createExtension();
    }

    private String renderArguments() {
        return arguments.stream()
                .map(Node::renderOptionally)
                .map(Optional::orElseThrow)
                .collect(Collectors.joining(",", "(", ")"));
    }

    private String renderCaller() {
        return caller.renderOptionally().orElseThrow();
    }

    @Override
    public Node transformFields(Function<Field, Field> mapping) {
        return this;
    }

    @Override
    public Node transformChildren(Function<Node, Node> mapping) {
        Node newCaller = mapping.apply(caller);
        List<Node> newArguments = arguments.stream().map(mapping).collect(Collectors.toList());
        return copy(newCaller, newArguments);
    }

    protected abstract Node copy(Node caller, List<Node> arguments);

    @Override
    public boolean matches(Type value, CallStack stack) {
        return caller.doesReturn(value, stack);
    }

}
