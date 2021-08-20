package com.br.luanluna.stoom.service;

import com.br.luanluna.stoom.exception.AddressNotFoundException;
import com.br.luanluna.stoom.model.Address;
import com.br.luanluna.stoom.model.Pagination;
import com.br.luanluna.stoom.repository.AddressRepository;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private GeoApiService geoApiService;

    @Override
    public Address create(Address address) {
        if (address.getLatitude() == null || address.getLongitude() == null ||
                address.getLatitude().compareTo(Double.valueOf(0)) == 0 ||
                address.getLongitude().compareTo(Double.valueOf(0)) == 0) {

            GeocodingResult localization = geoApiService.getGeocoding(address);

            if (localization != null && localization.geometry != null && localization.geometry.location != null) {

                LatLng location = localization.geometry.location;
                address.setLatitude(location.lat != 0 ? location.lat : 0);
                address.setLatitude(location.lng != 0 ? location.lng : 0);
            }
        }

        return addressRepository.save(address);
    }

    @Override
    public Address get(BigInteger id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return address.get();
        }

        throw new AddressNotFoundException();
    }

    @Override
    public Address update(Address address) {
        Optional<Address> savedAddress = addressRepository.findById(address.getId());
        if (savedAddress.isPresent()) {
            return addressRepository.save(address);
        }

        throw new AddressNotFoundException();
    }

    @Override
    public void delete(BigInteger id) {
        Optional<Address> savedAddress = addressRepository.findById(id);
        if (savedAddress.isPresent()) {
            addressRepository.deleteById(id);
        }

        throw new AddressNotFoundException();
    }

    @Override
    public List<Address> getAll(Pagination pagination) {
        Pageable paging = PageRequest.of(pagination.getPage(),
                            pagination.getSize(),
                            Sort.by((pagination.isDesc() ?
                                    Sort.Direction.DESC :
                                    Sort.Direction.ASC),
                                    pagination.getSortBy()));

        Page<Address> pagedResult = addressRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        return new ArrayList<>();
    }
}
