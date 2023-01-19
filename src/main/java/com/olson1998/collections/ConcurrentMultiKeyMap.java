package com.olson1998.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ConcurrentMultiKeyMap<T, S, H> implements MultiKeyMap<T, S, H>{

    private final ConcurrentMap<T, Map<S, H>> multiKeyMapImpl;


    @Override
    public int size() {
        var sizeRef = new AtomicInteger(0);
        this.multiKeyMapImpl.values().forEach(linkedMap -> {
            var size = sizeRef.get();
            size += linkedMap.size();
            sizeRef.set(size);
        });
        return sizeRef.get();
    }

    @Override
    public Integer size(T key1){
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).size();
        }else {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return multiKeyMapImpl.isEmpty();
    }

    @Override
    public Boolean isEmpty(T key1){
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).isEmpty();
        }else {
            return null;
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return multiKeyMapImpl.containsKey(key);
    }

    @Override
    public Boolean containsKey(T key1, S key2){
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).containsKey(key2);
        }else {
            return null;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return multiKeyMapImpl.values().stream()
                .anyMatch(linkedMap -> linkedMap.containsValue(value));
    }

    @Override
    public Boolean containsValue(T key1, H value){
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).containsValue(value);
        }else {
            return null;
        }
    }

    @Override
    public H get(T key1, S key2){
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).get(key2);
        }else {
            return null;
        }
    }

    @Override
    public MultiKeyMap<T, S, H> find(Predicate<T> key1Command, Predicate<S> key2Command, Predicate<H> valueCommand) {
        var searchResult = new ConcurrentMultiKeyMap<T, S, H>();
        this.forEach((key1, linkedMap) -> {
            if(key1Command.test(key1)){
                linkedMap.forEach((key2, value) -> {
                    if(key2Command.test(key2) && valueCommand.test(value)){
                        searchResult.put(key1, key2, value);
                    }
                });
            }
        });
        return searchResult;
    }

    @Override
    public Map<T, S> find(Predicate<H> command) {
        var searchResult = new ConcurrentHashMap<T, S>();
        this.forEach((key1, linkedMap) -> {
            linkedMap.forEach((key2, value) -> {
                if(command.test(value)){
                    searchResult.put(key1, key2);
                }
            });
        });
        return searchResult;
    }

    @Override
    public Collection<H> getValues(T key1){
        if(multiKeyMapImpl.containsKey(key1)){
            return multiKeyMapImpl.get(key1).values();
        }else {
            return null;
        }
    }

    @Override
    public Map<S, H> get(Object key) {
        return multiKeyMapImpl.get(key);
    }

    @Override
    public Map<S, H> put(T key, Map<S, H> value) {
        if(multiKeyMapImpl.containsKey(key)){
            multiKeyMapImpl.get(key).putAll(value);
            return multiKeyMapImpl.get(key);
        }else {
            var linkedMap = new ConcurrentHashMap<>(value);
            multiKeyMapImpl.put(key, linkedMap);
            return linkedMap;
        }
    }

    public Map<S, H> put(T key1, S key2, H value){
        if(multiKeyMapImpl.containsKey(key1)){
            var linkedMap = multiKeyMapImpl.get(key1);
            linkedMap.put(key2, value);
            return linkedMap;
        }else {
            var linkedMap = new ConcurrentHashMap<S, H>();
            linkedMap.put(key2, value);
            return linkedMap;
        }
    }

    public void put(MultiKeyMap<T, S, H> multiKeyMap){
        this.putAll(multiKeyMap);
    }

    @Override
    public Map<S, H> remove(Object key) {
        return multiKeyMapImpl.remove(key);
    }

    @Override
    public void putAll(Map<? extends T, ? extends Map<S, H>> m) {
        multiKeyMapImpl.putAll(m);
    }

    @Override
    public void clear() {
        multiKeyMapImpl.clear();
    }

    @Override
    public Set<T> keySet() {
        return multiKeyMapImpl.keySet();
    }

    @Override
    public Collection<Map<S, H>> values() {
        return multiKeyMapImpl.values();
    }

    @Override
    public Set<Entry<T, Map<S, H>>> entrySet() {
        return multiKeyMapImpl.entrySet();
    }

    @Override
    public Map<S, H> getOrDefault(Object key, Map<S, H> defaultValue) {
        return multiKeyMapImpl.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super T, ? super Map<S, H>> action) {
        multiKeyMapImpl.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super T, ? super Map<S, H>, ? extends Map<S, H>> function) {
        multiKeyMapImpl.replaceAll(function);
    }

    @Override
    public Map<S, H> putIfAbsent(T key, Map<S, H> value) {
        return multiKeyMapImpl.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return multiKeyMapImpl.remove(key, value);
    }

    @Override
    public boolean replace(T key, Map<S, H> oldValue, Map<S, H> newValue) {
        return multiKeyMapImpl.replace(key, oldValue, newValue);
    }

    @Override
    public Map<S, H> replace(T key, Map<S, H> value) {
        return multiKeyMapImpl.replace(key, value);
    }

    @Override
    public String toString() {
        var stringify = new StringBuilder("ConcurrentMultiKeyMap={");
        this.multiKeyMapImpl.forEach((key1, linkedMap) -> {
            stringify.append(key1).append("->[");
            var keys = linkedMap.keySet().stream().toList();
            for(int i=0; i < keys.size(); i++){
                var key = keys.get(i);
                stringify.append(key).append("=").append(linkedMap.get(key));
                if(i < size()-1){
                    stringify.append(",");
                }
            }
            stringify.append("]");
        });
        stringify.append("}");
        return stringify.toString();
    }

    public ConcurrentMultiKeyMap() {
        this.multiKeyMapImpl = new ConcurrentHashMap<>();
    }

    public ConcurrentMultiKeyMap(MultiKeyMap<T, S, H> multiKeyMap){
        this.multiKeyMapImpl = new ConcurrentHashMap<>();
        multiKeyMapImpl.putAll(multiKeyMap);
    }
}
