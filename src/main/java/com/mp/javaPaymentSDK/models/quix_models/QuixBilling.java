package com.mp.javaPaymentSDK.models.quix_models;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixBilling {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private QuixAddress address;
    @SerializedName("corporate_id_number")
    private String corporateIdNumber = null;

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

    public QuixAddress getAddress() {
        return address;
    }

    public void setAddress(QuixAddress address) {
        this.address = address;
    }

    public String getCorporateIdNumber() {
        return corporateIdNumber;
    }

    public void setCorporateIdNumber(String corporateIdNumber) {
        this.corporateIdNumber = corporateIdNumber;
    }

    public Pair<Boolean, String> isMissingField() {
        List<String> mandatoryFields = Arrays.asList(
                "firstName", "lastName", "address"
        );
        Pair<Boolean, String> missingField = Utils.containsNull(QuixBilling.class, this, mandatoryFields);
        if (missingField.getFirst()) {
            return missingField;
        }

        return address.isMissingField();
    }
}
