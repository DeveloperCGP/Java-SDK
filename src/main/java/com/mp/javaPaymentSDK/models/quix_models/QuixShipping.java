package com.mp.javaPaymentSDK.models.quix_models;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Method;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixShipping {
    private String name = null;
    @SerializedName("first_name")
    private String firstName = null;
    @SerializedName("last_name")
    private String lastName = null;
    private String company = null;
    private String email = null;
    @SerializedName("phone_number")
    private String phoneNumber = null;
    private Method method = null;
    private QuixAddress address = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public QuixAddress getAddress() {
        return address;
    }

    public void setAddress(QuixAddress address) {
        this.address = address;
    }
}
