package com.meti.render;

import com.meti.type.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConcreteFunctionBuilder implements Node.Prototype {
    private final List<Field> fields;
    private final Node value;

    public ConcreteFunctionBuilder() {
        this(Collections.emptyList(), null);
    }

    public ConcreteFunctionBuilder withIdentity(Field identity) {
        if (fields.isEmpty()) fields.add(identity);
        else fields.set(0, identity);
        return this;
    }

    public ConcreteFunctionBuilder withParameters(List<Field> parameters){
        this.fields.addAll(parameters);
        return this;
    }

    public ConcreteFunctionBuilder(List<Field> fields, Node value) {
        this.fields = new ArrayList<>(fields);
        this.value = value;
    }

    @Override
    public Node.Prototype withField(Field field) {
        List<Field> newFields = new ArrayList<>(this.fields);
        newFields.add(field);
        return new ConcreteFunctionBuilder(newFields, value);
    }

    @Override
    public Node.Prototype withChild(Node child) {
        return new ConcreteFunctionBuilder(fields, child);
    }

    @Override
    public Node build() {
        if (fields.isEmpty()) {
            throw new IllegalStateException("No return type was provided.");
        } else {
            return fields.get(0).applyDestruction(this::createNode);
        }
    }

    private ConcreteFunctionNode createNode(String name, Type returnType) {
        List<Field> parameters = fields.subList(1, fields.size());
        if(value == null) {
            String message = String.format("Concrete function '%s' must have a value.", name);
            throw new IllegalStateException(message);
        }
        return new ConcreteFunctionNode(name, parameters, returnType, value);
    }
}
