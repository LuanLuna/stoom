package com.br.luanluna.stoom;

import com.br.luanluna.stoom.exception.AddressNotFoundException;
import com.br.luanluna.stoom.exception.GenericAddressException;
import com.br.luanluna.stoom.model.Address;
import com.br.luanluna.stoom.model.Pagination;
import com.br.luanluna.stoom.repository.AddressRepository;
import com.br.luanluna.stoom.service.AddressServiceImpl;
import com.br.luanluna.stoom.service.GeoApiService;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[UNIT TESTS] ADDRESS SERVICE")
@SpringBootTest
@SpringJUnitConfig
class AddressUnitTests {

	@InjectMocks
	private AddressServiceImpl addressServiceMock;

	@Mock
	private AddressRepository addressRepository;
	@Mock
	private GeoApiService geoApiService;

	private Integer index = 1;
	private double lat = -7.0984016968722194;
	private double lng = -34.838459190038314;

	Address addressToSave;
	Address addressToCompare;
	GeocodingResult geocodingResult;

	@BeforeEach
	void prepareTest() {
		index++;

		addressToSave = AddressUtils.buildDefaultAddress(BigInteger.valueOf(index));
		addressToCompare = AddressUtils.buildDefaultAddress(BigInteger.valueOf(index));
		addressToCompare.setLatitude(lat + index);
		addressToCompare.setLongitude(lng + index);

		geocodingResult = new GeocodingResult();
		geocodingResult.geometry = new Geometry();
		geocodingResult.geometry.location = new LatLng(lat + index, lng + index);
	}

	@Test()
	@DisplayName("ADDRESS SUCCESS WITHOUT LATITUDE AND LONGITUDE")
	void addressWithoutLatLongSuccess() {

		addressToSave.setLongitude(null);
		addressToSave.setLongitude(null);
		Mockito.when(geoApiService.getGeocoding(Mockito.any())).thenReturn(geocodingResult);

		Address newAddress = addressServiceMock.fillLatitudeAndLongitude(addressToSave);
		assertTrue(newAddress.getLatitude() != null);
		assertTrue(newAddress.getLongitude() != null);
	}

	@Test()
	@DisplayName("CREATE ADDRESS SUCCESS")
	void createAddressSuccess() {

		Mockito.when(addressRepository.save(Mockito.any())).thenReturn(addressToSave);
		Mockito.when(geoApiService.getGeocoding(Mockito.any())).thenReturn(geocodingResult);

		Address newAddress = addressServiceMock.create(addressToSave);
		assertTrue(AddressUtils.compare(addressToCompare, newAddress));
	}

	@Test()
	@DisplayName("CREATE ADDRESS WITH NULL REQUIRED FIELD")
	void createAddressWithNullRequiredFieldNull() {

		addressToSave.setCity(null);

		Mockito.when(addressRepository.save(Mockito.any())).thenReturn(addressToSave);
		Mockito.when(geoApiService.getGeocoding(Mockito.any())).thenReturn(geocodingResult);

		GenericAddressException exception = assertThrows(
				GenericAddressException.class,
				() -> addressServiceMock.create(addressToSave)
		);

		assertEquals(exception.getReasons()[0], "The city attribute can't be null");
	}

	@Test()
	@DisplayName("UPDATE ADDRESS WITH MAX SIZE LENGTH FIELD")
	void updateAddressWithoutLatLongSuccess() {
		String text = "";
		for (int i = 0; i < 101; i++) {
			text += "T";
		}

		assertEquals(101, text.length());

		addressToSave.setNeighbourhood(text);

		Mockito.when(addressRepository.save(Mockito.any())).thenReturn(addressToSave);
		Mockito.when(geoApiService.getGeocoding(Mockito.any())).thenReturn(geocodingResult);

		GenericAddressException exception = assertThrows(
				GenericAddressException.class,
				() -> addressServiceMock.update(addressToSave)
		);

		assertEquals(exception.getReasons()[0], "The max length to neighbourhood attribute is 100");
	}

	@Test()
	@DisplayName("READ ADDRESS SUCCESS")
	void readAddressSuccess() {

		Optional<Address> address = Optional.of(addressToSave);
		Mockito.when(addressRepository.findById(Mockito.any())).thenReturn(address);

		Address requiredAddress = addressServiceMock.get(addressToSave.getId());
		assertTrue(AddressUtils.compare(addressToSave, requiredAddress));
	}

	@Test()
	@DisplayName("LIST ADDRESS SUCCESS")
	void lstAddressSuccess() {
		List<Address> addresses = new ArrayList<>();

		for (int i = 1; i < 12; i++) {
			Address tmpAddress = AddressUtils.buildDefaultAddress(BigInteger.valueOf(index));
			addressToCompare = AddressUtils.buildDefaultAddress(BigInteger.valueOf(index));
			addressToCompare.setLatitude(Double.valueOf(i));
			addressToCompare.setLongitude(Double.valueOf(i));
			addresses.add(tmpAddress);
		}

		Page<Address> page = new PageImpl<>(addresses);;
		Mockito.when(addressRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

		List<Address> requiredAddresses = addressServiceMock.getAll(new Pagination(1,1,"id",true));
		assertEquals(requiredAddresses.size(), 11);

		for (int i = 0; i < 11; i++) {
			assertTrue(AddressUtils.compare(requiredAddresses.get(0), addresses.get(0)));
		}
	}

	@Test()
	@DisplayName("DELETE ADDRESS NOT FOUND")
	void deleteAddressNotFound() {

		Optional<Address> address = Optional.empty();
		Mockito.when(addressRepository.findById(Mockito.any())).thenReturn(address);

		assertThrows(
				AddressNotFoundException.class,
				() -> addressServiceMock.delete(BigInteger.valueOf(index))
		);
	}



}
