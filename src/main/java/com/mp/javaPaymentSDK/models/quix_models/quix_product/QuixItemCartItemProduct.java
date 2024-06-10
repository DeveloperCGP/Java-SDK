package com.mp.javaPaymentSDK.models.quix_models.quix_product;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

public class QuixItemCartItemProduct {

    private QuixArticleProduct article = null;
    private int units;
    @SerializedName("total_price_with_tax")
    private double totalPriceWithTax;
    @SerializedName("auto_shipping")
    private boolean autoShipping = true;

    public QuixArticleProduct getArticle() {
        return article;
    }

    public void setArticle(QuixArticleProduct article) {
        this.article = article;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getTotal_price_with_tax() {
        return totalPriceWithTax;
    }

    public void setTotal_price_with_tax(double total_price_with_tax) {
        this.totalPriceWithTax = Double.parseDouble(Utils.roundAmount(total_price_with_tax));
    }

    public boolean isAutoShipping() {
        return autoShipping;
    }

    public void setAutoShipping(boolean autoShipping) {
        this.autoShipping = autoShipping;
    }

    public Pair<Boolean, String> isMissingFields() {
        if (units <= 0) {
            return new Pair<>(true, "units");
        }
        if (totalPriceWithTax <= 0) {
            return new Pair<>(true, "totalPriceWithTax");
        }
        if (article == null) {
            return new Pair<>(true, "article");
        }

        return article.isMissingFields();
    }
}
