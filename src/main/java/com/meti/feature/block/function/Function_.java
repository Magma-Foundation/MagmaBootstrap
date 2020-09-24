package com.meti.feature.block.function;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Node;
import com.meti.feature.render.Parent;
import com.meti.feature.render.Type;
import com.meti.stack.CallStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Function_ extends Parent {
    protected final List<Field> parameters;
    protected final Field identity;

    public Function_(Field identity, List<Field> parameters) {
        this.parameters = parameters;
        this.identity = identity;
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public Optional<String> renderOptionally() {
        return Optional.of(render());
    }

    @Override
    public String render() {
        if (!identity.isFlagged(Field.Flag.NATIVE)) {
            return identity.renderWithMore(renderParameters()) + complete();
        } else {
            return "";
        }
    }

    protected abstract String complete();

    private String renderParameters() {
        return parameters.stream()
                .map(Field::render)
                .collect(Collectors.joining(",", "(", ")"));
    }

    @Override
    public Stream<Field> streamFields() {
        List<Field> list = new ArrayList<>();
        list.add(identity);
        list.addAll(parameters);
        return list.stream();
    }

    @Override
    public Node transformFields(Function<Field, Field> mapping) {
        Field newIdentity = mapping.apply(identity);
        List<Field> newParameters = transformParameters(mapping);
        return complete(newIdentity, newParameters);
    }

    protected abstract Node complete(Field newIdentity, List<Field> newParameters);

    private List<Field> transformParameters(Function<Field, Field> mapping) {
        return parameters.stream()
                .map(mapping)
                .collect(Collectors.toList());
    }

    @Override
    public boolean matches(Type value, CallStack stack) {
        throw new UnsupportedOperationException();
    }
}
