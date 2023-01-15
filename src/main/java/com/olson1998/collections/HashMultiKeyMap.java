package com.olson1998.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class HashMultiKeyMap<T, S, H> implements MultiKeyMap <T, S, H> {

    private final Map<T, Map<S, H>> multiKeyMapImpl;

    @Override
    public H get(T key1, S key2) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            return linkedMap.getOrDefault(key2, null);
        }else {
            return null;
        }
    }

    @Override
    public Collection<H> get(T key1) {
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).values();
        }else {
            return null;
        }
    }

    @Override
    public MultiKeyMap<T, S, H> get(BiPredicate<T, Map<S, H>> obtainCommand) {
        var resultMap = new HashMultiKeyMap<T, S, H>();
        this.forEach((key1, linkedMap) -> {
            if(obtainCommand.test(key1, linkedMap)){
                resultMap.append(key1, linkedMap);
            }
        });
        return resultMap;
    }

    @Override
    public MultiKeyMap<T, S, H> findKeys(Set<H> values) {
        var resultMap = new HashMultiKeyMap<T, S, H>();
        this.forEach((key1, linkedMap) -> {
            linkedMap.forEach((key2, value) -> {
                if(values.contains(value)){
                    resultMap.append(key1, key2, value);
                }
            });
        });
        return resultMap;
    }

    @Override
    public void append(T key1, S key2) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            if(!linkedMap.containsKey(key2)){
                linkedMap.put(key2, null);
            }
        }
    }

    @Override
    public Set<H> getUnique(T key1) {
        return new HashSet<>(get(key1));
    }

    @Override
    public void append(T key1, S key2, H value) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            linkedMap.put(key2, value);
        }else {
            var linkedMap = new HashMap<S, H>();
            linkedMap.put(key2, value);
            multiKeyMapImpl.put(key1, linkedMap);
        }
    }

    @Override
    public void append(T key1, Map<S, H> values) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            linkedMap.putAll(values);
        }else {
            multiKeyMapImpl.put(key1, values);
        }
    }

    @Override
    public void append(MultiKeyMap<T, S, H> multiKeyMap) {
        multiKeyMap.forEach(multiKeyMapImpl::put);
    }

    @Override
    public void remove(T key1, S key2, H value) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            linkedMap.remove(key2);
        }
    }

    @Override
    public H replace(T key1, S key2, H value) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            H overWrittenValue = null;
            if(linkedMap.containsKey(key2)){
                overWrittenValue = linkedMap.get(key2);
            }
            linkedMap.replace(key2, value);
            return overWrittenValue;
        }else {
            this.append(key1, key2, value);
            return null;
        }
    }

    @Override
    public Map<S, H> replace(T key1, H value) {
        var overWrittenValues = new HashMap<S, H>();
        this.forEach((primaryKey, linkedMap) -> {
            linkedMap.forEach((key2, val) -> {
                linkedMap.replace(key2, value);
                overWrittenValues.put(key2, val);
            });
        });
        return overWrittenValues;
    }

    @Override
    public void remove(T key1, H value) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            linkedMap.forEach((key2, val) -> {
                if(val.equals(value)){
                    this.remove(key1, key2, value);
                }
            });
        }
    }

    @Override
    public void removeAll() {
        keys().forEach(multiKeyMapImpl::remove);
    }

    @Override
    public Set<S> keys(T key1) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            return linkedMap.keySet();
        }else {
            return null;
        }
    }

    @Override
    public Set<T> keys(Set<S> keys) {
        return multiKeyMapImpl.keySet().stream()
                .filter(key1 -> multiKeyMapImpl.get(key1).keySet().stream().anyMatch(keys::contains))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> keys() {
        return multiKeyMapImpl.keySet();
    }

    @Override
    public int size() {
        var sizeRef = new AtomicInteger(0);
        multiKeyMapImpl.values().forEach(linkedMap ->{
            var size = sizeRef.get();
            size = size + linkedMap.size();
            sizeRef.set(size);
        });
        return sizeRef.get();
    }

    @Override
    public Integer size(T key1) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            return linkedMap.size();
        }else {
            return null;
        }
    }

    @Override
    public boolean contains(T key1, S key2, H value) {
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            if(linkedMap.containsKey(key2)){
                return linkedMap.get(key2).equals(value);
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean contains(T key1, H value) {
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).containsValue(value);
        }else {
            return false;
        }
    }

    @Override
    public boolean contains(H value) {
        return multiKeyMapImpl.values().stream()
                .anyMatch(linkedMap -> linkedMap.containsValue(value));
    }

    @Override
    public void forEach(BiConsumer<T, Map<S, H>> command) {
        multiKeyMapImpl.forEach(command);
    }

    public HashMultiKeyMap() {
        this.multiKeyMapImpl = new HashMap<>();
    }

    public HashMultiKeyMap(MultiKeyMap<T, S, H> multiKeyMap) {
        this.multiKeyMapImpl = new HashMap<>();
        this.append(multiKeyMap);
    }
}
