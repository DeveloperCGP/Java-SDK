package com.mp.javaPaymentSDK.models.quix_models.quix_product;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Category;
import com.mp.javaPaymentSDK.models.quix_models.QuixAddress;
import com.mp.javaPaymentSDK.models.quix_models.QuixShipping;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixArticleProduct {

    private String name = null;
    private final String type = "product";
    private Category category = null;
    private String reference = null;
    @SerializedName("unit_price_with_tax")
    private double unitPriceWithTax = -1;
    private String description = null;
    private String url = null;
    @SerializedName("image_url")
    private String imageUrl = null;
    @SerializedName("total_discount")
    private double totalDiscount = 0;
    private String brand = null;
    private String mpn = null;
    private QuixShipping shipping = null;
    private QuixAddress address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getUnitPriceWithTax() {
        return unitPriceWithTax;
    }

    public void setUnitPriceWithTax(double unitPriceWithTax) {
        this.unitPriceWithTax = Double.parseDouble(Utils.roundAmount(unitPriceWithTax));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public QuixShipping getShipping() {
        return shipping;
    }

    public void setShipping(QuixShipping shipping) {
        this.shipping = shipping;
    }

    public QuixAddress getAddress() {
        return address;
    }

    public void setAddress(QuixAddress address) {
        this.address = address;
    }

    public Pair<Boolean, String> isMissingFields() {
        if (unitPriceWithTax <= 0) {
            return new Pair<>(true, "unitPriceWithTax");
        }

        List<String> mandatoryFields = Arrays.asList(
                "name", "type", "category", "reference"
        );

        return Utils.containsNull(
                this.getClass(), this, mandatoryFields
        );
    }
}
