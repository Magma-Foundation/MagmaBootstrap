package com.meti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionBuilder implements Node.Prototype {
    private final List<Field> fields;
    private final Node value;

    public FunctionBuilder() {
        this(Collections.emptyList(), null);
    }

    public FunctionBuilder withIdentity(Field identity) {
        if (fields.isEmpty()) fields.add(identity);
        else fields.set(0, identity);
        return this;
    }

    public FunctionBuilder withParameters(List<Field> parameters){
        this.fields.addAll(parameters);
        return this;
    }

    public FunctionBuilder(List<Field> fields, Node value) {
        this.fields = new ArrayList<>(fields);
        this.value = value;
    }

    @Override
    public Node.Prototype withField(Field field) {
        List<Field> newFields = new ArrayList<>(this.fields);
        newFields.add(field);
        return new FunctionBuilder(newFields, value);
    }

    @Override
    public Node.Prototype withChild(Node child) {
        return new FunctionBuilder(fields, child);
    }

    @Override
    public Node build() {
        if (fields.isEmpty()) {
            throw new IllegalStateException("No return type was provided.");
        } else {
            return fields.get(0).applyDestruction(this::createNode);
        }
    }

    private FunctionNode createNode(String name, Type returnType) {
        List<Field> parameters = fields.subList(1, fields.size());
        return new FunctionNode(name, parameters, returnType, value);
    }
}
