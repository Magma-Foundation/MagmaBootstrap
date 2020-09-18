package com.meti.evaluate.tokenizer;

import com.meti.CompileException;
import com.meti.content.Content;
import com.meti.process.CastNode;
import com.meti.render.ContentNode;
import com.meti.render.Node;
import com.meti.type.ContentType;
import com.meti.type.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class CastTokenizer extends AbstractNodeTokenizer{
    public CastTokenizer(Content content) {
        super(content);
    }

    @Override
    public Optional<Node> evaluate() {
        if(content.startsWith("<") && content.endsWith(")")) {
            OptionalInt endOptional = content.index(">");
            if(endOptional.isEmpty()) return Optional.empty();
            int end = endOptional.getAsInt();
            Content typeContent = content.slice(1, end);
            Type type = new ContentType(typeContent);
            OptionalInt startOptional = content.index("(");
            if(startOptional.isEmpty()) return Optional.empty();
            int start = startOptional.getAsInt();
            Content valueContent = content.slice(start + 1, content.length() - 1);
            if(valueContent.isEmpty()) {
                String message = String.format("No value in cast '%s' was found.", content);
                throw new CompileException(message);
            }
            Node value = new ContentNode(valueContent);
            return Optional.of(new CastNode(type, value));
        }
        return Optional.empty();
    }
}
