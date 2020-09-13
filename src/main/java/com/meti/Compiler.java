package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Compiler {

    String compile(String content) {
        if (content.startsWith("def")) {
            int paramStart = content.indexOf('(');
            int paramEnd = content.indexOf(')');
            List<Field> fields = Arrays.stream(content.substring(paramStart + 1, paramEnd).trim().split(","))
                    .filter(s -> !s.isBlank())
                    .map(String::trim)
                    .map(this::parseField)
                    .collect(Collectors.toList());
            String renderedParameters = fields.stream()
                    .map(Field::render)
                    .collect(Collectors.joining(",", "(", ")"));
            String name = content.substring(4, paramStart).trim();
            int returnStart = content.indexOf(':', paramEnd);
            int returnEnd = content.indexOf("=>");
            String returnString = content.substring(returnStart + 1, returnEnd).trim();
            Type type = resolveString(returnString);
            String renderHeader = type.render(name);
            String value = content.substring(returnEnd + 2).trim();
            return renderHeader + renderedParameters + value;
        }
        return content;
    }

    private Field parseField(String fieldString) {
        int separator = fieldString.indexOf(':');
        String name = fieldString.substring(0, separator).trim();
        String typeString = fieldString.substring(separator + 1).trim();
        Type type = resolveString(typeString);
        return new Field(name, type);
    }

    private Type resolveString(String value) {
        Type type;
        if (value.equals("Int")) {
            type = IntType.IntType;
        } else {
             type = VoidType.VoidType;
        }
        return type;
    }
}