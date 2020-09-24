package com.meti.feature.block.function;

import com.meti.content.Content;
import com.meti.feature.render.Field;
import com.meti.feature.render.Type;
import com.meti.util.Monad;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionType implements Type {
    private final Type returnType;
    private final List<Field> parameters;

    public FunctionType(Type returnType, List<Field> parameters) {
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public FunctionType(Type returnType, Field... parameters) {
        this(returnType, List.of(parameters));
    }

    @Override
    public Type transformField(Function<Field, Field> mapping) {
        List<Field> newParameters = parameters.stream().map(mapping).collect(Collectors.toList());
        return new FunctionType(returnType, newParameters);
    }

    @Override
    public Type transformChildren(Function<Type, Type> mapping) {
        Type newReturn = mapping.apply(returnType);
        return new FunctionType(newReturn, parameters);
    }

    @Override
    public Monad<Group> group() {
        return new Monad<>(Type.Group.Function);
    }

    @Override
    public <R> Optional<R> applyToContent(Function<Content, R> function) {
        return Optional.empty();
    }

    @Override
    public String render(String name) {
        String renderedParameters = parameters.stream()
                .map(Field::renderType)
                .collect(Collectors.joining(",", "(", ")"));
        return returnType.render("(*" + name + ")" + renderedParameters);
    }

    @Override
    public String render() {
        return render("");
    }

    @Override
    public Prototype createPrototype() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Type> streamChildren() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Field> streamFields() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean is(Group group) {
        return group == Group.Function;
    }

    @Override
    public boolean doesReturn(Type type) {
        return returnType.equals(type);
    }
}
