package com.snyl.scentanyl.fragrance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="fragrances")
public class Fragrance {
    @Id
    @Column(name = "url", unique = true, nullable = false)
    private String url;                     // URL of the perfume (String)
    private String perfume_name;    // Perfume name
    private String brand;          // Brand name
    private String country;        // Country of origin
    private String gender;          // Gender classification (Male, Female, Unisex, etc.)
    private Double rating_value;        // Rating value (numerical)
    private Integer rating_count;        // Rating count (number of people who rated)
    private Integer year;                // Year of release
    private String top_notes;             // Top notes of the perfume (text)
    private String middle_notes;          // Middle notes of the perfume (text)
    private String base_notes;             // Base notes of the perfume (text)
    private String perfumer1;      // Perfumer 1 (name of the perfumer)
    private String perfumer2;      // Perfumer 2 (name of the perfumer)
    private String mainaccord1;   // Main accord 1
    private String mainaccord2;    // Main accord 2
    private String mainaccord3;    // Main accord 3
    private String mainaccord4;     // Main accord 4
    private String mainaccord5;     // Main accord 5

    public Fragrance() {
    }

    public Fragrance(String url, String perfume_name, String brand, String country, String gender, Double rating_value, Integer rating_count, Integer year, String top_notes, String middle_notes, String base_notes, String perfumer1, String perfumer2, String mainaccord1, String mainaccord2, String mainaccord3, String mainaccord4, String mainaccord5) {
        this.url = url;
        this.perfume_name = perfume_name;
        this.brand = brand;
        this.country = country;
        this.gender = gender;
        this.rating_value = rating_value;
        this.rating_count = rating_count;
        this.year = year;
        this.top_notes = top_notes;
        this.middle_notes = middle_notes;
        this.base_notes = base_notes;
        this.perfumer1 = perfumer1;
        this.perfumer2 = perfumer2;
        this.mainaccord1 = mainaccord1;
        this.mainaccord2 = mainaccord2;
        this.mainaccord3 = mainaccord3;
        this.mainaccord4 = mainaccord4;
        this.mainaccord5 = mainaccord5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerfume_name() {
        return perfume_name;
    }

    public void setPerfume_name(String perfume_name) {
        this.perfume_name = perfume_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getRating_value() {
        return rating_value;
    }

    public void setRating_value(Double rating_value) {
        this.rating_value = rating_value;
    }

    public Integer getRating_count() {
        return rating_count;
    }

    public void setRating_count(Integer rating_count) {
        this.rating_count = rating_count;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTop_notes() {
        return top_notes;
    }

    public void setTop_notes(String top_notes) {
        this.top_notes = top_notes;
    }

    public String getMiddle_notes() {
        return middle_notes;
    }

    public void setMiddle_notes(String middle_notes) {
        this.middle_notes = middle_notes;
    }

    public String getBase_notes() {
        return base_notes;
    }

    public void setBase_notes(String base_notes) {
        this.base_notes = base_notes;
    }

    public String getPerfumer1() {
        return perfumer1;
    }

    public void setPerfumer1(String perfumer1) {
        this.perfumer1 = perfumer1;
    }

    public String getPerfumer2() {
        return perfumer2;
    }

    public void setPerfumer2(String perfumer2) {
        this.perfumer2 = perfumer2;
    }

    public String getMainaccord1() {
        return mainaccord1;
    }

    public void setMainaccord1(String mainaccord1) {
        this.mainaccord1 = mainaccord1;
    }

    public String getMainaccord2() {
        return mainaccord2;
    }

    public void setMainaccord2(String mainaccord2) {
        this.mainaccord2 = mainaccord2;
    }

    public String getMainaccord3() {
        return mainaccord3;
    }

    public void setMainaccord3(String mainaccord3) {
        this.mainaccord3 = mainaccord3;
    }

    public String getMainaccord4() {
        return mainaccord4;
    }

    public void setMainaccord4(String mainaccord4) {
        this.mainaccord4 = mainaccord4;
    }

    public String getMainaccord5() {
        return mainaccord5;
    }

    public void setMainaccord5(String mainaccord5) {
        this.mainaccord5 = mainaccord5;
    }
}
