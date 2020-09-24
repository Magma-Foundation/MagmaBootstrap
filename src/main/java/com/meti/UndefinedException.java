package com.meti;

import com.meti.feature.CompileException;

public class UndefinedException extends CompileException {
    public UndefinedException(String message) {
        super(message);
    }
}
