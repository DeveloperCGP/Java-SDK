package com.mp.javaPaymentSDK.models.quix_models.quix_service;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Category;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixArticleService {

    private String name = null;
    private final String type = "service";
    @SerializedName("start_date")
    private String startDate = null;
    @SerializedName("end_date")
    private String endDate = null;
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

    public void setUnitPriceWithTax(double unitPriceWithTax) throws InvalidFieldException {
        if (unitPriceWithTax < 0)
        {
            throw new InvalidFieldException("unitPriceWithTax: Value must be (unitPriceWithTax >= 0)");
        }
        this.unitPriceWithTax = Double.parseDouble(Utils.roundAmount(unitPriceWithTax));
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        if (url != null && !url.isBlank())
        {
            this.url = Utils.encodeUrl(url);
        }
        else
        {
            this.url = null;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.isBlank())
        {
            this.imageUrl = Utils.encodeUrl(imageUrl);
        }
        else
        {
            this.imageUrl = null;
        }
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) throws InvalidFieldException {
        if (totalDiscount < 0)
        {
            throw new InvalidFieldException("totalDiscount: Value must be (totalDiscount >= 0)");
        }
        this.totalDiscount = Double.parseDouble(Utils.roundAmount(totalDiscount));
    }

    public Pair<Boolean, String> isMissingField() {
        if (unitPriceWithTax <= 0) {
            return new Pair<>(true, "unitPriceWithTax");
        }
        List<String> mandatoryFields = Arrays.asList(
                "name", "type", "category", "reference", "endDate"
        );
        return Utils.containsNull(
                this.getClass(), this, mandatoryFields
        );
    }
}
