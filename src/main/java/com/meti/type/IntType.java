package com.meti.type;

public class IntType extends FormattedType {
    public static final Type IntType = new IntType();

    public IntType() {
    }

    @Override
    protected String createFormat() {
        return "int %s";
    }
}