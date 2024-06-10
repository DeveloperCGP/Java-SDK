package com.mp.javaPaymentSDK.models.quix_models;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.CountryCode;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixAddress {

    @SerializedName("street_address")
    private String streetAddress = null;
    @SerializedName("street_address2")
    private String streetAddress2 = null;
    @SerializedName("postal_code")
    private String postalCode = null;
    private String city = null;
    private String country = null;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(CountryCode country) {
        this.country = country.getAlpha3();
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public Pair<Boolean, String> isMissingField() {
        List<String> mandatoryFields = Arrays.asList(
                "streetAddress", "postalCode", "city", "country"
        );
        return Utils.containsNull(QuixAddress.class, this, mandatoryFields);
    }
}
