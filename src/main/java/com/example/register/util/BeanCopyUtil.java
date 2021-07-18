/*
 * Copyright: 2017 dingxiang-inc.com Inc. All rights reserved.
 */

package com.example.register.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: BeanCopyUtil.java
 * @Description: BeanCopyUtil.java类说明
 * @Author: XDreamc
 * @Date: 2019/1/29 20:29
 */
public class BeanCopyUtil {

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    public static <T, E> void copyProperties(T source, E target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    public static <T, E> E copyProperties(T source, Class<E> targetClass) {
        E target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        copyProperties(source, target);
        return target;
    }

    public static <T, E> List<E> copyProperties(Collection<T> sourceList, Class<E> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }

        List<E> result = new ArrayList<>(sourceList.size());
        sourceList.forEach(item -> {
            E e = copyProperties(item, targetClass);
            result.add(e);
        });

        return result;
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier beanCopier = BEAN_COPIER_CACHE.get(beanKey);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_CACHE.put(beanKey, beanCopier);
        }

        return beanCopier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

}
