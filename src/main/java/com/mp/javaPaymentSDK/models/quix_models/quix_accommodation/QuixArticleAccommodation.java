package com.mp.javaPaymentSDK.models.quix_models.quix_accommodation;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Category;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.models.quix_models.QuixAddress;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixArticleAccommodation {
    private String name = null;
    private final String type = "accommodation";
    private Category category = null;
    private String reference = null;
    @SerializedName("unit_price_with_tax")
    private double unitPriceWithTax = -1;
    @SerializedName("checkin_date")
    private String checkinDate = null;
    @SerializedName("checkout_date")
    private String checkoutDate = null;
    @SerializedName("establishment_name")
    private String establishmentName = null;
    private QuixAddress address = null;
    private int guests = -1;
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

    public void setUnitPriceWithTax(double unitPriceWithTax) {
        this.unitPriceWithTax = Double.parseDouble(Utils.roundAmount(unitPriceWithTax));
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public QuixAddress getAddress() {
        return address;
    }

    public void setAddress(QuixAddress address) {
        this.address = address;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
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
        if (guests <= 0) {
            return new Pair<>(true, "guests");
        }

        List<String> mandatoryFields = Arrays.asList(
                "name", "type", "category", "reference", "unitPriceWithTax",
                "checkinDate", "checkoutDate", "establishmentName", "address", "guests"
        );
        Pair<Boolean, String> missingField = Utils.containsNull(QuixArticleAccommodation.class, this, mandatoryFields);
        if (missingField.getFirst()) {
            return missingField;
        }

        return address.isMissingField();
    }
}
