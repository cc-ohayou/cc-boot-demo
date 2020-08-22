package com.qunhe.toilet.facade.domain.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Json util.
 */
@Slf4j
@UtilityClass
public class JsonUtil {
    /**
     * The constant OBJECT_MAPPER.
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final ObjectMapper NOT_NULL_MAPPER = new ObjectMapper();

    private static final TypeFactory TF = OBJECT_MAPPER.getTypeFactory();

    /**
     * The constant OBJECT_MAPPER_IGNORE_UNKNOW.
     */
    public static final ObjectMapper OBJECT_MAPPER_IGNORE_UNKNOW = new ObjectMapper();

    private static final TypeFactory TF_IGNORE_UNKNOW = OBJECT_MAPPER_IGNORE_UNKNOW.getTypeFactory();

    static {
        OBJECT_MAPPER_IGNORE_UNKNOW.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * obj -> obj String 忽略null字段
     * @param obj
     * @return
     */
    public static String toStringIgnoreNull(final Object obj) {
        try {
            return NOT_NULL_MAPPER.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            log.error("toString", e);
            return null;
        }
    }

    /**
     * byte[] -> obj String 忽略null字段
     * @param array
     * @return
     */
    public static String toStringIgnoreNull(final byte[] array) {
        if (Objects.isNull(array) || array.length == 0) {
            return null;
        }
        try {
            Object obj = JSON.parse(array);
            return NOT_NULL_MAPPER.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            log.error("toString", e);
            return null;
        }
    }

    /**
     * Write string.
     *
     * @param object the object
     * @return the string
     */
    public String write(final Object object) {
        if (object == null) {
            return null;
        }
        String retStr = null;
        try {
            retStr = OBJECT_MAPPER.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            log.warn("className", object.getClass().getName());
        }
        return retStr;
    }

    /**
     * Read value t.
     *
     * @param <T> the type parameter
     * @param content the content
     * @param valueTypeRef the value type ref
     * @return t t
     */
    public <T> T readValue(final String content, final TypeReference valueTypeRef) {
        try {
            if(content == null){
                return null;
            }
            return (T) OBJECT_MAPPER.readValue(content, valueTypeRef);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            log.error("readValue", e);
            return null;
        }
    }

    /**
     * readValue json to map
     *
     * @param jsonStr the json str
     * @return map map
     */
    public Map<String, Object> readJsonToMap(final String jsonStr) {
        Map<String, Object> map = new HashMap<>(16, 1);
        try {
            map = OBJECT_MAPPER.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (final IOException e) {
            log.error("readJsonToMap", e);
        }
        return map;
    }

    /**
     * readValue json to a data structure
     *
     * @param <T> the type parameter
     * @param jsonStr the json str
     * @param baseClass the base class
     * @return t t
     */
    public <T> T readValue(final String jsonStr, final Class<T> baseClass) {
        T value = null;
        if (jsonStr == null || StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            value = OBJECT_MAPPER.readValue(jsonStr, baseClass);
        } catch (final IOException e) {
            log.error("readValue", e);
        }
        return value;
    }

    /**
     * Read value t.
     *
     * @param <T> the type parameter
     * @param jsonStr the json str
     * @param baseClass the base class
     * @param ignoreUnknow the ignore unknow
     * @return the t
     */
    public <T> T readValue(String jsonStr, Class<T> baseClass, boolean ignoreUnknow) {
        if (!ignoreUnknow) {
            return readValue(jsonStr, baseClass);
        }
        T value = null;
        try {
            value = OBJECT_MAPPER_IGNORE_UNKNOW.readValue(jsonStr, baseClass);
        } catch (final IOException e) {
            log.error("readValue", e);
        }
        return value;
    }

    /**
     * readValue json string to a collection
     *
     * @param <T> the type parameter
     * @param jsonStr the json str
     * @param collectionClass the collection class
     * @param elementClass the element class
     * @return t t
     * @throws IOException the io exception
     */
    public <T> T readValue(final String jsonStr,
        final Class<? extends Collection> collectionClass, final Class<?> elementClass) throws IOException {
        T value = null;
        if(StringUtils.isBlank(jsonStr) ){
            return null;
        }
        try {
            value = OBJECT_MAPPER.readValue(jsonStr,
                TF.constructCollectionType(collectionClass, elementClass));
        } catch (final JsonParseException | JsonMappingException e) {
            log.warn("readValue", e);
        } catch (final IOException e1) {
            log.warn("readValue", e1);
        }
        return value;
    }

    public <T> T readValueNoException(final String jsonStr,
            final Class<? extends Collection> collectionClass, final Class<?> elementClass) {
        T value = null;
        if(StringUtils.isBlank(jsonStr) ){
            return null;
        }
        try {
            value = OBJECT_MAPPER.readValue(jsonStr,
                    TF.constructCollectionType(collectionClass, elementClass));
        } catch (final JsonParseException | JsonMappingException e) {
            log.warn("readValue", e);
        } catch (final IOException e1) {
            log.warn("readValue", e1);
        }
        return value;
    }

    /**
     * Read value t.
     *
     * @param <T> the type parameter
     * @param jsonStr the json str
     * @param collectionClass the collection class
     * @param elementClass the element class
     * @param ignoreUnknowProperties the ignore unknow properties
     * @return the t
     */
    public static <T> T readValue(final String jsonStr,
        final Class<? extends Collection> collectionClass, final Class<?> elementClass,
        final boolean ignoreUnknowProperties) {
        try {
            if (!ignoreUnknowProperties) {
                return readValue(jsonStr, collectionClass, elementClass);
            }
            return OBJECT_MAPPER_IGNORE_UNKNOW.readValue(jsonStr,
                TF_IGNORE_UNKNOW.constructCollectionType(collectionClass, elementClass));
        } catch (final IOException e) {
            log.warn("readValue", e);
        }
        return null;
    }

    /**
     * Read value t.
     *
     * @param <T> the type parameter
     * @param jsonStr the json str
     * @param mapClass the map class
     * @param keyClass the key class
     * @param valueClass the value class
     * @return the t
     */
    public static <T> T readValue(final String jsonStr,
        final Class<? extends Map> mapClass,
        final Class<?> keyClass, final Class<?> valueClass) {
        T value = null;
        try {
            value = OBJECT_MAPPER.readValue(jsonStr,
                TF.constructMapType(mapClass, keyClass, valueClass));
        } catch (final IOException e) {
            log.warn("readValue", e);
        }
        return value;
    }

    /**
     * Read value t.
     *
     * @param <T> the type parameter
     * @param jsonStr the json str
     * @param mapClass the map class
     * @param keyClass the key class
     * @param valueClass the value class
     * @param ignoreUnknowProperties the ignore unknow properties
     * @return the t
     */
    public static <T> T readValue(final String jsonStr,
        final Class<? extends Map> mapClass,
        final Class<?> keyClass, final Class<?> valueClass,
        final boolean ignoreUnknowProperties) {
        if (!ignoreUnknowProperties) {
            return readValue(jsonStr, mapClass, keyClass, valueClass);
        }
        T value = null;
        try {
            value = OBJECT_MAPPER_IGNORE_UNKNOW.readValue(jsonStr,
                TF_IGNORE_UNKNOW.constructMapType(mapClass, keyClass, valueClass));
        } catch (final IOException e) {
            log.warn("readValue", e);
        }
        return value;
    }

    /**
     * Read tree from json json node.
     *
     * @param jsonStr the json str
     * @return the json node
     */
    public static JsonNode readTreeFromJson(final String jsonStr) {
        JsonNode node = null;
        try {
            node = OBJECT_MAPPER.readTree(jsonStr);
        } catch (final IOException e) {
            log.warn("readTree", e);
        }
        return node;
    }
}

