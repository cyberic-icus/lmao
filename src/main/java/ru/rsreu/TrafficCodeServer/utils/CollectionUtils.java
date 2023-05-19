package ru.rsreu.TrafficCodeServer.utils;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtils<T> {
    public Set<T> intersection(Collection<T> collection1, Collection<T> collection2) {
        return collection1.stream()
                .distinct()
                .filter(collection2::contains)
                .collect(Collectors.toSet());
    }
}
