package com.meti.util.load;

import com.meti.content.Content;
import com.meti.content.StringContent;
import com.meti.feature.render.ContentNode;
import com.meti.feature.render.Empty;
import com.meti.feature.render.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MappedClassPath extends AbstractClassPath {
    private final Map<String, String> contentMap;
    private final List<String> loaded = new ArrayList<>();

    public MappedClassPath(Map<String, String> contentMap) {
        this.contentMap = contentMap;
    }

    @Override
    public Optional<Node> read(Stream<Content> packages) {
        String packageName = packages.map(Content::asString).collect(Collectors.joining("."));
        if (contentMap.containsKey(packageName)) {
            if (loaded.contains(packageName)) {
                return Optional.of(new Empty());
            } else {
                loaded.add(packageName);
                return Optional.of(new ContentNode(new StringContent(contentMap.get(packageName))));
            }
        } else {
            String message = String.format("'%s' isn't loaded as a valid import in: %s", packageName, contentMap.keySet());
            throw new IllegalArgumentException(message);
        }
    }
}
