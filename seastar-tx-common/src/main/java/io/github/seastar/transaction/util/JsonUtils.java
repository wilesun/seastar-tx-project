package io.github.seastar.transaction.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class JsonUtils {

    private static final ThreadLocal<ObjectMapper> objectMapper = new ThreadLocal<>();


    private static ObjectMapper getObjectMapper() {

        ObjectMapper om = objectMapper.get();
        if (om == null) {
            om = new ObjectMapper();
            objectMapper.set(om);
        }

        return om;
    }


    public static String stringify(Object o) throws IOException {
        return getObjectMapper().writeValueAsString(o);
    }


    public static <T> T parse(String string, Class<T> clazz) throws IOException {
        return getObjectMapper().readValue(string, clazz);
    }
}
