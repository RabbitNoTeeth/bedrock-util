package com.github.bedrock.util;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json utils
 *
 * @author liuxindong
 */
public class JsonUtils {

    private JsonUtils() {
    }

    private static final Gson GSON = new GsonBuilder().create();

    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final JsonParser JSON_PARSER = new JsonParser();

    /**
     * encode object to json string
     *
     * @param data
     * @return
     */
    public static String encode(Object data) {
        return GSON.toJson(data);
    }

    /**
     * decode json string to object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T decode(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return GSON.fromJson(json, clazz);
    }

    /**
     * decode json string to List
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> decode2List(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        Type type = new ParameterizedTypeImpl(clazz);
        return GSON.fromJson(json, type);
    }

    /**
     * decode json string to object
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T decode(String json, Type type) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    /**
     * decode json string to object
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T decode(JsonObject json, Type type) {
        return GSON.fromJson(json.toString(), type);
    }

    /**
     * decode json element to object
     *
     * @param element
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T decode(JsonElement element, Type type) {
        return GSON.fromJson(element, type);
    }

    /**
     * decode json string to JsonElement
     *
     * @param json
     * @return
     */
    public static JsonElement decode(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON_PARSER.parse(json);
    }

    /**
     * decode json string to JsonObject
     *
     * @param json
     * @return
     */
    public static JsonObject decodeToJsonObject(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON_PARSER.parse(json).getAsJsonObject();
    }

    /**
     * decode json string to JsonArray
     *
     * @param json
     * @return
     */
    public static JsonArray decodeToJsonArray(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON_PARSER.parse(json).getAsJsonArray();
    }

    /**
     * convert type a to type b
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return decode(encode(source), targetClass);
    }

    /**
     * encode object to pretty json string
     *
     * @param obj
     * @return
     */
    public static String toPrettyJson(Object obj) {
        return PRETTY_GSON.toJson(obj);
    }

    /**
     * get value as JsonArray from JsonObject
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static JsonArray getJsonArray(JsonObject jsonObject, String key) {
        JsonElement value = jsonObject.get(key);
        return value != null && value.isJsonArray() ? value.getAsJsonArray() : null;
    }

    /**
     * get value as JsonObject from JsonObject
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static JsonObject getJsonObject(JsonObject jsonObject, String key) {
        JsonElement value = jsonObject.get(key);
        return value != null && value.isJsonObject() ? value.getAsJsonObject() : null;
    }

    /**
     * get value as String from JsonObject
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static String getString(JsonObject jsonObject, String key) {
        JsonElement value = jsonObject.get(key);
        return value != null && !value.isJsonNull() ? value.getAsString() : null;
    }

    /**
     * get value as Integer from JsonObject
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static Integer getInt(JsonObject jsonObject, String key) {
        JsonElement value = jsonObject.get(key);
        return value != null && !value.isJsonNull() ? Integer.parseInt(value.getAsString()) : null;
    }

    /**
     * get value as Boolean from JsonObject
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static Boolean getBoolean(JsonObject jsonObject, String key) {
        JsonElement value = jsonObject.get(key);
        return value != null && !value.isJsonNull() ? Boolean.valueOf(value.getAsString()) : null;
    }

    /**
     * get value as Double from JsonObject
     * @param jsonObject
     * @param key
     * @return
     */
    public static Double getDouble(JsonObject jsonObject, String key) {
        JsonElement value = jsonObject.get(key);
        return value != null && !value.isJsonNull() ? Double.valueOf(value.getAsString()) : null;
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
