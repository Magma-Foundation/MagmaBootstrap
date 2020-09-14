package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FunctionNode implements Node {
    private final Type returnType;
    private final String name;
    private final List<Field> parameters;
    private final Node value;

    public FunctionNode(String name, List<Field> parameters, Type returnType, Node value) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.value = value;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Prototype createPrototype() {
        return new FunctionBuilder();
    }

    @Override
    public Optional<String> render() {
        String renderedParameters = renderParameters();
        return returnType.render(name + renderedParameters)
                .map(this::appendValue);
    }

    private String appendValue(String s) {
        return s + value.render();
    }

    private String renderParameters() {
        return parameters.stream()
                .map(Field::render)
                .flatMap(Optional::stream)
                .collect(Collectors.joining(",", "(", ")"));
    }

    @Override
    public Stream<Field> streamFields() {
        List<Field> list = new ArrayList<>();
        list.add(new InlineField(name, returnType));
        list.addAll(parameters);
        return list.stream();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.of(value);
    }
}
