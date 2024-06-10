package com.mp.javaPaymentSDK.models.quix_models.quix_product;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Currency;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class QuixCartProduct {

    private Currency currency = null;
    @SerializedName("total_price_with_tax")
    private double totalPriceWithTax = 0;
    private List<QuixItemCartItemProduct> items = new ArrayList<>();
    private String reference = null;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getTotalPriceWithTax() {
        return totalPriceWithTax;
    }

    public void setTotalPriceWithTax(double totalPriceWithTax) {
        this.totalPriceWithTax = Double.parseDouble(Utils.roundAmount(totalPriceWithTax));
    }

    public List<QuixItemCartItemProduct> getItems() {
        return items;
    }

    public void setItems(List<QuixItemCartItemProduct> items) {
        this.items = items;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Pair<Boolean, String> isMissingField() {
        if (totalPriceWithTax <= 0) {
            return new Pair<>(true, "totalPriceWithTax");
        }
        if (currency == null) {
            return new Pair<>(true, "currency");
        }
        if (items == null || items.isEmpty()) {
            return new Pair<>(true, "items");
        }

        for (QuixItemCartItemProduct item : items) {
            Pair<Boolean, String> missingField = item.isMissingFields();
            if (missingField.getFirst()) {
                return missingField;
            }
        }

        return new Pair<>(false, null);
    }
}
