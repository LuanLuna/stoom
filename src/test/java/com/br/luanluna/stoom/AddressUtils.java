package com.br.luanluna.stoom;

import com.br.luanluna.stoom.model.Address;

import java.math.BigInteger;
import java.util.Objects;

public abstract class AddressUtils {

    public static Address buildDefaultAddress(BigInteger sequenceIdentifier) {
        Address address = new Address();

        address.setId(sequenceIdentifier);
        address.setStreetName("Rua João Daminhao " + sequenceIdentifier);
        address.setZipcode("23654789");
        address.setCity("Santa Rita " + sequenceIdentifier);
        address.setComplement("Ap " + sequenceIdentifier);
        address.setCountry("Brazil " + sequenceIdentifier);
        address.setNeighbourhood("Tibiri " + sequenceIdentifier);
        address.setNumber("12" + sequenceIdentifier);
        address.setState("Paraiba " + sequenceIdentifier);

        return address;
    }

    public static boolean compare(Address address1, Address address2) {
        if (address1 == null || address2 == null) {
            return false;
        }
        return Objects.equals(address1.getId(), address2.getId()) &&
                Objects.equals(address1.getStreetName(), address2.getStreetName()) &&
                Objects.equals(address1.getNumber(), address2.getNumber()) &&
                Objects.equals(address1.getComplement(), address2.getComplement()) &&
                Objects.equals(address1.getNeighbourhood(), address2.getNeighbourhood()) &&
                Objects.equals(address1.getCity(), address2.getCity()) &&
                Objects.equals(address1.getState(), address2.getState()) &&
                Objects.equals(address1.getCountry(), address2.getCountry()) &&
                Objects.equals(address1.getZipcode(), address2.getZipcode()) &&
                Objects.equals(address1.getLatitude(), address2.getLatitude()) &&
                Objects.equals(address1.getLongitude(), address2.getLongitude());
    }
}
