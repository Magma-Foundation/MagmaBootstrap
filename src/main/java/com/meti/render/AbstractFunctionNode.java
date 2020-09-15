package com.meti.render;

import com.meti.content.Content;
import com.meti.type.Type;
import com.meti.util.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AbstractFunctionNode extends ParentNode {
    private final Type returnType;
    private final String name;
    private final List<Field> parameters;
    private final List<FieldFlag> flags;

    public AbstractFunctionNode(String name, List<Field> parameters, Type returnType, List<FieldFlag> flags) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.flags = flags;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Prototype createPrototype() {
        return new AbstractFunctionBuilder();
    }

    @Override
    public Optional<String> render() {
        if (flags.contains(FieldFlag.NATIVE)) {
            return Optional.of("");
        } else {
            String renderedParameters = renderParameters();
            return returnType.render(name + renderedParameters);
        }
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
        list.add(new InlineField(name, returnType, flags));
        list.addAll(parameters);
        return list.stream();
    }

    @Override
    public Stream<Node> streamChildren() {
        return Stream.empty();
    }

    @Override
    public Monad<NodeGroup> group() {
        return new Monad<>(NodeGroup.AbstractFunction);
    }
}
