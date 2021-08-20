package com.br.luanluna.stoom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private BigInteger id;

    @NotNull(message = "The streetName attribute can't be null")
    @Size(max=300, message = "The max length to streetName attribute is 300")
    private String streetName;

    @NotNull(message = "The number attribute can't be null")
    @Size(max=10, message = "The max length to number attribute is 10")
    private String number;
    
    @Size(max=200, message = "The max length to complement attribute is 200")
    private String complement;

    @NotNull(message = "The neighbourhood attribute can't be null")
    @Size(max=100, message = "The max length to neighbourhood attribute is 100")
    private String neighbourhood;

    @NotNull(message = "The city attribute can't be null")
    @Size(max=100, message = "The max length to city attribute is 100")
    private String city;

    @NotNull(message = "The state attribute can't be null")
    @Size(max=100, message = "The max length to state attribute is 100")
    private String state;

    @NotNull(message = "The country attribute can't be null")
    @Size(max=100, message = "The max length to country attribute is 100")
    private String country;

    @NotNull(message = "The zipcode attribute can't be null")
    @Size(max=100, message = "The max length to zipcode attribute is 100")
    private String zipcode;

    private Double latitude;
    private Double longitude;

    @Override
    public int hashCode() {
        return Objects.hash(id, streetName, number, complement, neighbourhood, city, state, country, zipcode, latitude, longitude);
    }
}
