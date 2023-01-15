package com.olson1998.utils;

import java.util.concurrent.ConcurrentHashMap;

public class RequestHolder {

    private static final ConcurrentHashMap<Long, String> REQUEST_THREAD_HOLDERS_MAP = new ConcurrentHashMap<>();

    public static String getCurrentRequestHolder(){
        var threadId = Thread.currentThread().getId();
        return REQUEST_THREAD_HOLDERS_MAP.get(threadId);
    }

    public static void registerCurrentRequestHolder(String holderId){
        var threadId = Thread.currentThread().getId();
        REQUEST_THREAD_HOLDERS_MAP.put(threadId, holderId);
    }

    public static void removeCurrentHolder(){
        var threadId = Thread.currentThread().getId();
        REQUEST_THREAD_HOLDERS_MAP.remove(threadId);
    }

    private RequestHolder() {
    }
}
