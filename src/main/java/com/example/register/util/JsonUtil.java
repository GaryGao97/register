package com.example.register.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @Author: minchen
 * 创建时间: 2020/7/30
 * 功能描述: json 转化工具类
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> String toJsonString(T obj) {
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("Failed to serialize obj '%s'", obj), e);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if(StringUtils.isEmpty(json) || clazz == null){
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Failed to deserialize json '%s'", json), e);
        }
    }

    public static <T> T parse(String json, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(json) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? json : mapper.readValue(json,typeReference));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to deserialize json '%s'", json), e);
        }
    }

    public static <T> T parse(String json, Class<?> collectionClass, Class<?>... elementClasses){
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return mapper.readValue(json,javaType);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to deserialize json '%s'", json), e);
        }
    }

    public static <T> T parse(Object object, TypeReference<T> typeReference){
        if(object == null || typeReference == null){
            return null;
        }
        try {
            return mapper.convertValue(object, typeReference);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to deserialize object '%s'", object), e);
        }
    }

    public static <T> T parse(Object object, Class<T> clazz) {
        if(object == null || clazz == null){
            return null;
        }
        try {
            return mapper.convertValue(object, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to deserialize object '%s'", object), e);
        }
    }
}
