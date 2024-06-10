package com.mp.javaPaymentSDK.models.quix_models.quix_accommodation;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

public class QuixItemCartItemAccommodation {

    private QuixArticleAccommodation article = null;
    private int units = -1;
    @SerializedName("total_price_with_tax")
    private double totalPriceWithTax = -1;
    private boolean auto_shipping = true;

    public QuixArticleAccommodation getArticle() {
        return article;
    }

    public void setArticle(QuixArticleAccommodation article) {
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

    public boolean isAuto_shipping() {
        return auto_shipping;
    }

    public void setAuto_shipping(boolean auto_shipping) {
        this.auto_shipping = auto_shipping;
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

        return article.isMissingField();
    }
}
