/*
 * Copyright (c) 2024 Recurso de ejemplo creado por David Bernal.
 */

package com.dbernal.user_manager_api.model;

import com.dbernal.user_manager_api.domain.Phone;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "PHONE")
public class PhoneEntity {

    public PhoneEntity() {
    }

    public PhoneEntity(String number, String cityCode, String countryCode) {
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_generator")
    @SequenceGenerator(name="phone_generator", sequenceName = "phone_seq", allocationSize=50)
    private Long id;

    private String number;
    private String cityCode;
    private String countryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
