package com.realestate.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String address;
    private String city;
    private String district;
    
    @JsonProperty("propertyType")
    private String propertyType; // APARTMENT, HOUSE, LAND, COMMERCIAL
    
    @JsonProperty("listingType")
    private String listingType; // SALE, RENT
    
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private Boolean featured;
    private Boolean approved;
    
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
    
    private User owner;
    private Category category;
    
    @JsonProperty("images")
    private List<PropertyImage> images;

    // Default constructor
    public Property() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<PropertyImage> getImages() {
        return images;
    }

    public void setImages(List<PropertyImage> images) {
        this.images = images;
    }
    
    // Utility methods
    public String getFormattedPrice() {
        if (price == null) return "N/A";
        return String.format("%,.0f VND", price);
    }
    
    public String getPropertyTypeDisplay() {
        if (propertyType == null) return "";
        switch (propertyType.toUpperCase()) {
            case "APARTMENT": return "Căn hộ";
            case "HOUSE": return "Nhà riêng";
            case "LAND": return "Đất";
            case "COMMERCIAL": return "Thương mại";
            default: return propertyType;
        }
    }
    
    public String getListingTypeDisplay() {
        if (listingType == null) return "";
        return "SALE".equals(listingType.toUpperCase()) ? "Bán" : "Cho thuê";
    }
}