package com.olson1998.collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public interface MultiKeyMap<T, S, H> {

    H get(T key1, S key2);

    Collection <H> get(T key1);

    MultiKeyMap<T, S, H> get(BiPredicate<T, Map<S, H>> obtainCommand);

    Set<H> getUnique(T key1);

    MultiKeyMap<T, S, H> findKeys(Set<H> values);

    void append(T key1, S key2);

    void append(T key1, S key2, H value);

    void append(T key1, Map<S, H> values);

    void append(MultiKeyMap<T, S, H> multiKeyMap);

    void remove(T key1, S key2, H value);

    H replace(T key1, S key2, H value);

    Map<S, H> replace(T key1, H value);

    void remove(T key1, H value);

    void removeAll();

    Set<S> keys(T key1);

    Set<T> keys(Set<S> key2);

    Set<T> keys();

    int size();

    Integer size(T key1);

    boolean contains(T key1, S key2, H value);

    boolean contains(T key1, H value);

    boolean contains(H value);

    void forEach(BiConsumer<T, Map<S, H>> command);
}
