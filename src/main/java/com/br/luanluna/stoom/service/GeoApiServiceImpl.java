package com.br.luanluna.stoom.service;

import com.br.luanluna.stoom.model.Address;
import com.br.luanluna.stoom.util.GeoApiContextSingleton;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

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

            GeoApiContext context = GeoApiContextSingleton.getInstance(KEY).getContext();
            GeocodingResult[] results =  GeocodingApi.geocode(context,
                    joiner.toString()).await();


            context.shutdown();
            return results.length > 0 ? results[0] : null;

        } catch (Exception e) {
            return null;
        }
    }
}
