package com.meti.render;

import com.meti.content.Content;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class InvocationNode extends ParentNode {
    protected final Node caller;
    protected final List<Node> arguments;

    public InvocationNode(Node caller, List<Node> arguments) {
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
    public Optional<String> render() {
        return Optional.of(renderCompletely());
    }

    private String renderCompletely() {
        return renderCaller() + renderArguments() + createExtension();
    }

    private String renderArguments() {
        return arguments.stream()
                .map(Node::render)
                .map(Optional::orElseThrow)
                .collect(Collectors.joining(",", "(", ")"));
    }

    private String renderCaller() {
        return caller.render().orElseThrow();
    }
}
