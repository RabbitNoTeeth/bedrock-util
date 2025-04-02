package com.github.bedrock.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static <E> List<List<E>> split(List<E> source, long size) {
        List<List<E>> res = new ArrayList<>();
        long total = source.size();
        long skip = 0;
        while (skip < total) {
            res.add(source.stream().skip(skip).limit(size).collect(Collectors.toList()));
            skip += size;
        }
        return res;
    }

    public static <E> List<E> and(List<E> a, List<E> b) {
        if (a == null || b == null) {
            return new ArrayList<>();
        }
        List<E> res = new ArrayList<>();
        a.forEach(i -> {
            if (b.contains(i)) {
                res.add(i);
            }
        });
        return res;
    }

    public static <E> List<E> or(List<E> a, List<E> b) {
        if (a == null && b == null) {
            return new ArrayList<>();
        }
        if (a == null) {
            return new ArrayList<>(b);
        }
        if (b == null) {
            return new ArrayList<>(a);
        }
        List<E> res = new ArrayList<>(a);
        b.forEach(i -> {
            if (!a.contains(i)) {
                res.add(i);
            }
        });
        return res;
    }

    public static <E> List<E> xor(List<E> a, List<E> b) {
        if (a == null || b == null) {
            return new ArrayList<>();
        }
        List<E> res = new ArrayList<>();
        b.forEach(i -> {
            if (!a.contains(i)) {
                res.add(i);
            }
        });
        return res;
    }

}
