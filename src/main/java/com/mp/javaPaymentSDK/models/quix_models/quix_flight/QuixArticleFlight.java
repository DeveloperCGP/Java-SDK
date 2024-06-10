package com.mp.javaPaymentSDK.models.quix_models.quix_flight;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Category;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixArticleFlight {

    private String name = null;
    private final String type = "flight";
    private Category category = null;
    private String reference = null;
    @SerializedName("unit_price_with_tax")
    private double unitPriceWithTax = -1;
    private List<QuixPassengerFlight> passengers = null;
    @SerializedName("departure_date")
    private String departureDate = null;
    private List<QuixSegmentFlight> segments = null;

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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public List<QuixPassengerFlight> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<QuixPassengerFlight> passengers) {
        this.passengers = passengers;
    }

    public List<QuixSegmentFlight> getSegments() {
        return segments;
    }

    public void setSegments(List<QuixSegmentFlight> segments) {
        this.segments = segments;
    }

    public Pair<Boolean, String> isMissingFields() {
        if (unitPriceWithTax <= 0) {
            return new Pair<>(true, "unitPriceWithTax");
        }

        List<String> mandatoryFields = Arrays.asList(
                "name", "type", "category",
                "reference", "departureDate",
                "passengers", "segments"
        );

        Pair<Boolean, String> missingField = Utils.containsNull(
                this.getClass(), this, mandatoryFields
        );
        if (missingField.getFirst()) {
            return missingField;
        }

        if (passengers.isEmpty()) {
            return new Pair<>(true, "passengers");
        }
        if (segments.isEmpty()) {
            return new Pair<>(true, "passengers");
        }

        for (QuixPassengerFlight item : passengers) {
            missingField = item.isMissingFields();
            if (missingField.getFirst()) {
                return missingField;
            }
        }

        for (QuixSegmentFlight item : segments) {
            missingField = item.isMissingFields();
            if (missingField.getFirst()) {
                return missingField;
            }
        }

        return new Pair<>(false, null);
    }
}
