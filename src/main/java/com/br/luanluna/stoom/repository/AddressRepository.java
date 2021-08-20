package com.br.luanluna.stoom.repository;

import com.br.luanluna.stoom.model.Address;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;

public interface AddressRepository  extends PagingAndSortingRepository<Address, BigInteger> {
}
