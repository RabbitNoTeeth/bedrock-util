package com.github.bedrock.util.collection;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimedMap<K, V> {

    private static final TemporalUnit DEFAULT_TIME_UNIT = ChronoUnit.SECONDS;
    private final Map<K, LocalDateTime> EXPIRE_MAP = new ConcurrentHashMap<>();
    private final Map<K, V> DATA_MAP = new ConcurrentHashMap<>();

    public boolean containsKey(K key) {
        clearExpiredData();
        return DATA_MAP.containsKey(key);
    }

    public void put(K key, V value, long expire) {
        put(key, value, expire, DEFAULT_TIME_UNIT);
    }

    public void put(K key, V value, long expire, TemporalUnit timeUnit) {
        clearExpiredData();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.plus(expire, timeUnit);
        EXPIRE_MAP.put(key, expiredTime);
        DATA_MAP.put(key, value);
    }

    public V get(K key) {
        clearExpiredData();
        return DATA_MAP.get(key);
    }

    public int size() {
        clearExpiredData();
        return DATA_MAP.size();
    }

    public void remove(K key) {
        EXPIRE_MAP.remove(key);
        DATA_MAP.remove(key);
    }

    public void clear() {
        EXPIRE_MAP.clear();
        DATA_MAP.clear();
    }

    private void clearExpiredData() {
        List<K> expiredKeys = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        EXPIRE_MAP.forEach((key, expiredTime) -> {
            if (expiredTime.isBefore(now)) {
                expiredKeys.add(key);
            }
        });
        expiredKeys.forEach(key -> {
            EXPIRE_MAP.remove(key);
            DATA_MAP.remove(key);
        });
    }

}
