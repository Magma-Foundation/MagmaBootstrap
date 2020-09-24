package com.meti.feature.type.primitive;

import com.meti.feature.evaluate.tokenize.AbstractNodeTokenizer;
import com.meti.content.Content;
import com.meti.feature.render.Node;

import java.math.BigInteger;
import java.util.Optional;

public class IntTokenizer extends AbstractNodeTokenizer {
    public static final BigInteger START = BigInteger.valueOf(-32767);
    public static final BigInteger END = BigInteger.valueOf(32767);

    public IntTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        try {
            BigInteger capsule = content.value().apply(BigInteger::new);
            //checking range per https://en.wikipedia.org/wiki/C_data_types
            if (capsule.compareTo(START) > 0 && capsule.compareTo(END) < 0) {
                int value = capsule.intValueExact();
                return Optional.of(new Int_(value));
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
