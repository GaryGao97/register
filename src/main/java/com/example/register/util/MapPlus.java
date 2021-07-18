package com.example.register.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: minchen
 * 创建时间: 2020/9/21
 * 功能描述:
 */
public class MapPlus<K, V> implements Map<K, V>, Serializable {

    private final Map<K, V> map;

    public MapPlus(Map<K, V> map){
        if (map == null) {
            throw new IllegalArgumentException("map is null.");
        }
        this.map = map;
    }

    public MapPlus() {
        this.map = new HashMap<>();
    }

    public static <K, V> MapPlus<K, V> getMapPlus(Map<K, V> map) {
        if (map == null) {
            throw new IllegalArgumentException("map is null.");
        }
        return new MapPlus<>(map);
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }


    public <T> T getObject(Object key, Class<T> clazz) {
        Object obj = map.get(key);
        return JsonUtil.parse(obj, clazz);
    }

    public <T> T getObject(Object key, TypeReference<T> typeReference) {
        Object obj = map.get(key);
        if (typeReference == null) {
            return (T) obj;
        }
        return JsonUtil.parse(obj, typeReference);
    }


    public String getString(Object key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public Boolean getBoolean(Object key) {
        Object value = get(key);

        return castToBoolean(value);

    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    public MapPlus<K, V> fluentPut(K key, V value) {
        map.put(key, value);
        return this;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    public MapPlus<K, V> fluentPutAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
        return this;
    }

    @Override
    public void clear() {
        map.clear();
    }

    public MapPlus<K, V> fluentClear() {
        map.clear();
        return this;
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    public MapPlus<K, V> fluentRemove(Object key) {
        map.remove(key);
        return this;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Object clone() {
        return new MapPlus(map instanceof LinkedHashMap //
                ? new LinkedHashMap<K, V>(map) //
                : new HashMap<K, V>(map)
        );
    }

    @Override
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }


    public Map<K, V> getInnerMap() {
        return this.map;
    }

    private static Boolean castToBoolean(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean) value;
        }

        if(value instanceof BigDecimal){
            return intValue((BigDecimal) value) == 1;
        }

        if(value instanceof Number){
            return ((Number) value).intValue() == 1;
        }

        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            if("true".equalsIgnoreCase(strVal) //
                    || "1".equals(strVal)){
                return Boolean.TRUE;
            }
            if("false".equalsIgnoreCase(strVal) //
                    || "0".equals(strVal)){
                return Boolean.FALSE;
            }
            if("Y".equalsIgnoreCase(strVal) //
                    || "T".equals(strVal)){
                return Boolean.TRUE;
            }
            if("F".equalsIgnoreCase(strVal) //
                    || "N".equals(strVal)){
                return Boolean.FALSE;
            }
        }
        throw new IllegalArgumentException("can not cast to boolean, value : " + value);
    }

    private static int intValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.intValue();
        }

        return decimal.intValueExact();
    }



}
