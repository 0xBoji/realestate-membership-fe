package com.realestate.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Membership {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    
    @JsonProperty("durationDays")
    private Integer durationDays;
    
    private List<String> features;
    private Boolean active;

    // Default constructor
    public Membership() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    // Utility methods
    public String getFormattedPrice() {
        if (price == null) return "Free";
        return String.format("%,.0f VND", price);
    }
    
    public String getDurationDisplay() {
        if (durationDays == null) return "N/A";
        if (durationDays == 30) return "1 tháng";
        if (durationDays == 90) return "3 tháng";
        if (durationDays == 365) return "1 năm";
        return durationDays + " ngày";
    }
}