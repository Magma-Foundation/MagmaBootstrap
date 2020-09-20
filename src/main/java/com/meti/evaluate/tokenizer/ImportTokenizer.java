package com.meti.evaluate.tokenizer;

import com.meti.content.Content;
import com.meti.content.RootContent;
import com.meti.content.Strategy;
import com.meti.render.ImportNode;
import com.meti.render.IncludeNode;
import com.meti.render.Node;
import com.meti.util.load.ClassPath;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ImportTokenizer extends AbstractNodeTokenizer {
    private final ClassPath classPath;

    public ImportTokenizer(Content content, ClassPath classPath) {
        super(content);
        this.classPath = classPath;
    }

    @Override
    public Optional<Node> evaluate() {
        if (content.startsWith("import native")) {
            Content value = this.content.sliceToEnd(13);
            return Optional.of(value).flatMap((Function<Content, Optional<Node>>) classPath::loadNative);
        }
        if (content.startsWith("import ")) {
            Content value = content.sliceToEnd(7);
            Stream<Content> packages = value.split(PackageStrategy::new);
            return classPath.read(packages).map(ImportNode::new);
        }
        return Optional.empty();
    }

    private static class PackageStrategy implements Strategy {
        private final Content content;

        public PackageStrategy(Content content) {
            this.content = content;
        }

        @Override
        public Stream<Content> split() {
            return content.value()
                    .map(inner -> inner.split("\\."))
                    .apply(Arrays::stream)
                    .filter(s -> !s.isBlank())
                    .map(String::trim)
                    .map(RootContent::new);
        }
    }
}
