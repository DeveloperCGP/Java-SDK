package com.mp.javaPaymentSDK.models.quix_models.quix_service;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.models.quix_models.ConfirmationCartData;
import com.mp.javaPaymentSDK.models.quix_models.Customer;
import com.mp.javaPaymentSDK.models.quix_models.QuixBilling;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixServicePaySolExtendedData {

    private String product;
    private boolean disableFormEdition = false;
    @SerializedName("confirmation_card_data")
    private ConfirmationCartData confirmationCardData = null;
    private Customer customer = null;
    private QuixBilling billing;
    private QuixCartService cart;


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public QuixBilling getBilling() {
        return billing;
    }

    public void setBilling(QuixBilling billing) {
        this.billing = billing;
    }

    public QuixCartService getCart() {
        return cart;
    }

    public void setCart(QuixCartService cart) {
        this.cart = cart;
    }

    public boolean isDisableFormEdition() {
        return disableFormEdition;
    }

    public void setDisableFormEdition(boolean disableFormEdition) {
        this.disableFormEdition = disableFormEdition;
    }

    public ConfirmationCartData getConfirmationCardData() {
        return confirmationCardData;
    }

    public void setConfirmationCardData(ConfirmationCartData confirmationCardData) {
        this.confirmationCardData = confirmationCardData;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pair<Boolean, String> isMissingField() {
        List<String> mandatoryFields = Arrays.asList(
                "product", "billing", "cart"
        );

        Pair<Boolean, String> missingField = Utils.containsNull(
                this.getClass(), this, mandatoryFields
        );
        if (missingField.getFirst()) {
            return missingField;
        }
        missingField = billing.isMissingField();
        if (missingField.getFirst()) {
            return missingField;
        }
        return cart.isMissingField();
    }
}
