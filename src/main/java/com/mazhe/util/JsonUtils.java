package com.mazhe.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by edison on 17/6/14.
 */
@Slf4j
public abstract class JsonUtils {
    public static <T> ByteArrayOutputStream writeToOutputStream(T object) {

        return writeToOutputStream(object, Maps.newHashMap());
    }

    public static <T> ByteArrayOutputStream writeToOutputStream(
        T object,
        Map<Object, Boolean> features) {

        return writeToOutputStream(object, createFeaturedMapper(features));
    }

    public static <T> ByteArrayOutputStream writeToOutputStream(
        T object,
        ObjectMapper mapper) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            mapper.writeValue(baos, object);
        } catch (IOException e) {
            log.warn(Strings.substitute("Cannot write object of type '{0}' to output stream", object
                .getClass().getName()));
        }

        return baos;
    }

    public static <T> T readFromOutputStream(
        ByteArrayOutputStream outputStream,
        Class<T> type) {

        return readFromOutputStream(outputStream, type, Maps.newHashMap());
    }

    public static <T> T readFromOutputStream(
        ByteArrayOutputStream outputStream,
        Class<T> type,
        Map<Object, Boolean> features) {

        return readFromOutputStream(outputStream, type, createFeaturedMapper(features));
    }

    public static <T> T readFromOutputStream(
        ByteArrayOutputStream outputStream,
        Class<T> type,
        ObjectMapper mapper) {

        T result = null;

        try {
            result = mapper.readValue(outputStream.toByteArray(), type);
        } catch (IOException e) {
            log.warn(
                Strings.substitute("Cannot convert output stream to type '{0}'", type.getName()));
            log.error("", e);
        }

        return result;
    }

    public static <T> String writeToString(T object) {

        return writeToString(object, Maps.newHashMap());
    }

    public static <T> String writeToString(
        T object,
        Map<Object, Boolean> features) {

        return writeToString(object, createFeaturedMapper(features));
    }

    public static <T> String writeToString(
        T object,
        ObjectMapper mapper) {

        String result = null;

        try {
            result = mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn(
                "Cannot write object of type '{}' to output stream",
                object.getClass().getName());
            log.warn("", e);
        }

        return result;
    }

    public static <T> T readFromString(
        String jsonString,
        Class<T> type) {

        return readFromString(jsonString, type, Maps.newHashMap());
    }

    public static <T> T readFromString(
        String jsonString,
        Class<T> type,
        Map<Object, Boolean> features) {

        return readFromString(jsonString, type, createFeaturedMapper(features));
    }

    public static <T> T readFromString(
        String jsonString,
        Class<T> type,
        ObjectMapper mapper) {

        T result = null;
        try {
            result = mapper.readValue(jsonString, type);
        } catch (IOException e) {
            log.warn(
                "Cannot convert output stream to type '{}'",
                type.getName());
            log.warn("", e);
        }

        return result;
    }

    public static <T> List<T> readListFromString(
        String jsonString,
        Class<T> type) {

        return readListFromString(jsonString, type, Maps.newHashMap());
    }

    public static <T> List<T> readListFromString(
        String jsonString,
        Class<T> type,
        Map<Object, Boolean> features) {

        return readListFromString(jsonString, type, createFeaturedMapper(features));
    }

    public static <T> List<T> readListFromString(
        String jsonString,
        Class<T> type,
        ObjectMapper mapper) {

        List<T> result = null;
        try {
            result = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            log.warn(
                "Cannot convert output stream to type '{}'",
                type.getName());
            log.warn("", e);
        }

        return result;
    }

    private static ObjectMapper createFeaturedMapper(Map<Object, Boolean> features) {

        ObjectMapper mapper = new ObjectMapper();
        initObjectMapper(mapper, features);
        return mapper;
    }

    private static void initObjectMapper(
        ObjectMapper mapper,
        Map<Object, Boolean> features) {

        features.put(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        enableFeatures(mapper, features);
        enableModules(mapper);
    }

    private static void enableFeatures(
        ObjectMapper mapper,
        Map<Object, Boolean> features) {

        if (features != null && features.size() > 0) {
            features.entrySet().forEach(entry -> {
                Object config = entry.getKey();
                Boolean value = entry.getValue();

                if (MapperFeature.class.isInstance(config)) {
                    mapper.configure((MapperFeature) config, value);
                } else if (SerializationFeature.class.isInstance(config)) {
                    mapper.configure((SerializationFeature) config, value);
                } else if (DeserializationFeature.class.isInstance(config)) {
                    mapper.configure((DeserializationFeature) config, value);
                } else if (JsonParser.Feature.class.isInstance(config)) {
                    mapper.configure((JsonParser.Feature) config, value);
                }
            });
        }
    }

    private static void enableModules(ObjectMapper mapper) {

        enableJavaTime(mapper);
    }

    private static void enableJavaTime(ObjectMapper mapper) {

        mapper.registerModule(new JavaTimeModule());
    }
}
