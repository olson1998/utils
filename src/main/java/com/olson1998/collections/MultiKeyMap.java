package com.olson1998.collections;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface MultiKeyMap<T, S, H> extends Map<T, Map<S, H>>{

    Integer size(T key1);

    Boolean isEmpty(T key1);

    Boolean containsKey(T key1, S key2);

    Boolean containsValue(T key1, H value);

    H get(T key1, S key2);

    MultiKeyMap<T, S, H> find(Predicate<T> key1Command, Predicate<S> key2Command, Predicate<H> valueCommand);

    Map<T, S> find(Predicate<H> command);

    Collection<H> getValues(T key1);

    Map<S, H> put(T key1, S key2, H value);

    void put(MultiKeyMap<T, S, H> multiKeyMap);

    @Override
    String toString();
}
