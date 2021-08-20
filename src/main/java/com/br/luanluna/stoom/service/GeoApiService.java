package com.br.luanluna.stoom.service;

import com.br.luanluna.stoom.model.Address;
import com.google.maps.model.GeocodingResult;

public interface GeoApiService {
    public GeocodingResult getGeocoding(Address address);
}
