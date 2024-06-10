package com.mp.javaPaymentSDK.models.quix_models.quix_flight;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

public class QuixItemCartItemFlight {

    private QuixArticleFlight article = null;
    private int units = -1;
    @SerializedName("total_price_with_tax")
    private double totalPriceWithTax = -1;
    @SerializedName("auto_shipping")
    private boolean autoShipping = true;

    public QuixArticleFlight getArticle() {
        return article;
    }

    public void setArticle(QuixArticleFlight article) {
        this.article = article;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getTotalPriceWithTax() {
        return totalPriceWithTax;
    }

    public void setTotalPriceWithTax(double totalPriceWithTax) {
        this.totalPriceWithTax = Double.parseDouble(Utils.roundAmount(totalPriceWithTax));
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
