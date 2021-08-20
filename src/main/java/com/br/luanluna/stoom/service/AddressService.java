package com.br.luanluna.stoom.service;

import com.br.luanluna.stoom.exception.GenericAddressException;
import com.br.luanluna.stoom.exception.AddressNotFoundException;
import com.br.luanluna.stoom.model.Address;
import com.br.luanluna.stoom.model.Pagination;

import java.math.BigInteger;
import java.util.List;

public interface AddressService {
    public Address create(Address address) throws GenericAddressException;
    public Address get(BigInteger id) throws GenericAddressException, AddressNotFoundException;
    public Address update(Address address) throws GenericAddressException, AddressNotFoundException;
    public void delete(BigInteger id) throws GenericAddressException, AddressNotFoundException;
    public List<Address> getAll(Pagination pagination) throws GenericAddressException, AddressNotFoundException;
}
