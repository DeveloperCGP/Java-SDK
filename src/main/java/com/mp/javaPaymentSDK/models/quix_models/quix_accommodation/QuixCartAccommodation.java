package com.mp.javaPaymentSDK.models.quix_models.quix_accommodation;

import com.mp.javaPaymentSDK.enums.Currency;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class QuixCartAccommodation {

    private Currency currency = null;
    private double total_price_with_tax = 0;
    private String reference = null;
    private List<QuixItemCartItemAccommodation> items = new ArrayList<>();

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getTotal_price_with_tax() {
        return total_price_with_tax;
    }

    public void setTotal_price_with_tax(double total_price_with_tax) {
        this.total_price_with_tax = Double.parseDouble(Utils.roundAmount(total_price_with_tax));
    }

    public List<QuixItemCartItemAccommodation> getItems() {
        return items;
    }

    public void setItems(List<QuixItemCartItemAccommodation> items) {
        this.items = items;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Pair<Boolean, String> isMissingField() {
        if (total_price_with_tax <= 0) {
            return new Pair<>(true, "total_price_with_tax");
        }
        if (currency == null) {
            return new Pair<>(true, "currency");
        }
        if (items == null || items.isEmpty()) {
            return new Pair<>(true, "items");
        }

        for (QuixItemCartItemAccommodation item : items) {
            Pair<Boolean, String> missingField = item.isMissingFields();
            if (missingField.getFirst()) {
                return missingField;
            }
        }

        return new Pair<>(false, null);
    }
}
