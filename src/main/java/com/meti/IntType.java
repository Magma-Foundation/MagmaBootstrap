package com.meti;

public class IntType extends FormattedType {
    static final Type IntType = new IntType();

    public IntType() {
    }

    @Override
    protected String createFormat() {
        return "int %s";
    }
}