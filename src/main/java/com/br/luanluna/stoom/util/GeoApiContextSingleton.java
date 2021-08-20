package com.br.luanluna.stoom.util;

import com.google.maps.GeoApiContext;
import lombok.Getter;

public final class GeoApiContextSingleton {
    private static GeoApiContextSingleton instance;

    @Getter
    private GeoApiContext context;

    private GeoApiContextSingleton(String key) {
        this.context = new GeoApiContext.Builder()
                .apiKey(key)
                .build();
    }

    public static GeoApiContextSingleton getInstance(String key) {
        if (instance == null) {
            instance = new GeoApiContextSingleton(key);
        }
        return instance;
    }
}
