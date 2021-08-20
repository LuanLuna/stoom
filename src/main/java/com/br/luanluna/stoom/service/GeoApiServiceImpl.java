package com.br.luanluna.stoom.service;

import com.br.luanluna.stoom.model.Address;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Service
public class GeoApiServiceImpl implements GeoApiService {

    @Value("${geocoding.secret.key}")
    private String KEY;

    @Override
    public GeocodingResult getGeocoding(Address address) {
        if (address == null) {
            return null;
        }
        try {
            StringJoiner joiner = new StringJoiner(",");
            joiner.add(address.getStreetName())
                    .add(address.getNumber())
                    .add(address.getNeighbourhood())
                    .add(address.getCity())
                    .add(address.getState())
                    .add(address.getCountry())
                    .add(address.getZipcode());

            GeoApiContext context = new GeoApiContext
                    .Builder()
                    .disableRetries()
                    .readTimeout(3, TimeUnit.SECONDS)
                    .retryTimeout(3, TimeUnit.SECONDS)
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .apiKey(KEY)
                    .build();

            GeocodingResult[] results =  GeocodingApi.geocode(context,
                    joiner.toString()).await();


            context.shutdown();
            return results.length > 0 ? results[0] : null;

        } catch (Exception e) {
            return null;
        }
    }
}
