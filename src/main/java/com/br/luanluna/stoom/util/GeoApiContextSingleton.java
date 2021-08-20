package com.br.luanluna.stoom.util;

import com.google.maps.GeoApiContext;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public final class GeoApiContextSingleton {
    private static GeoApiContextSingleton instance;

    @Getter
    private GeoApiContext context;

    private GeoApiContextSingleton(String key) {
        this.context = new GeoApiContext
                .Builder()
                .disableRetries()
                .readTimeout(3, TimeUnit.SECONDS)
                .retryTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
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
